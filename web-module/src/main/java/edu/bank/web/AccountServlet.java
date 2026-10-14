package edu.bank.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountNumber") == null) { resp.sendRedirect("login.jsp"); return; }
        String account = (String) session.getAttribute("accountNumber");
        try (Connection con = Db.connect();
             PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE account_number = ?")) {
            ps.setString(1, account);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) { resp.sendError(404); return; }
                req.setAttribute("balance", rs.getBigDecimal(1));
            }
            try (PreparedStatement ps2 = con.prepareStatement(
                    "SELECT type, amount, created_at FROM transactions WHERE account_number = ? ORDER BY created_at DESC LIMIT 10")) {
                ps2.setString(1, account);
                try (ResultSet rs = ps2.executeQuery()) {
                    StringBuilder html = new StringBuilder();
                    while (rs.next()) html.append("<tr><td>").append(escape(rs.getString("type")))
                        .append("</td><td>").append(escape(rs.getBigDecimal("amount").toPlainString()))
                        .append("</td><td>").append(escape(rs.getTimestamp("created_at").toString())).append("</td></tr>");
                    req.setAttribute("historyRows", html.toString());
                }
            }
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<!doctype html><html><head><title>Bank account</title><style>body{font-family:Arial;max-width:850px;margin:40px auto;background:#f4f7fb;color:#172033}section{background:white;padding:24px;border-radius:12px;margin:16px 0}input,button{padding:10px;margin:5px}table{width:100%;border-collapse:collapse}td,th{padding:8px;border-bottom:1px solid #ddd}</style></head><body>");
            resp.getWriter().println("<h1>Bank Management</h1><section><h2>Available balance: ₹" + req.getAttribute("balance") + "</h2>");
            resp.getWriter().println("<form method='post' action='transaction'><input type='number' name='amount' min='0.01' step='0.01' required><button name='type' value='DEPOSIT'>Deposit</button><button name='type' value='WITHDRAWAL'>Withdraw</button></form>");
            resp.getWriter().println("<form method='post' action='logout'><button>Logout</button></form></section><section><h2>Recent transactions</h2><table><tr><th>Type</th><th>Amount</th><th>Date</th></tr>" + req.getAttribute("historyRows") + "</table></section></body></html>");
        } catch (SQLException ex) {
            getServletContext().log("Account page failure", ex);
            resp.sendError(500, "Unable to load account");
        }
    }
    private static String escape(String s) {
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");
    }
}
