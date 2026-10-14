package edu.bank.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("accountNumber") == null) { resp.sendRedirect("login.jsp"); return; }
        String account = (String) session.getAttribute("accountNumber");
        String type = req.getParameter("type");
        try {
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));
            if (amount.signum() <= 0 || (!"DEPOSIT".equals(type) && !"WITHDRAWAL".equals(type))) throw new IllegalArgumentException();
            transact(account, type, amount);
            resp.sendRedirect("account");
        } catch (IllegalArgumentException ex) {
            resp.sendError(400, "Enter a valid positive amount and transaction type");
        } catch (SQLException ex) {
            if ("Insufficient balance".equals(ex.getMessage())) resp.sendError(400, "Insufficient balance");
            else { getServletContext().log("Transaction failed", ex); resp.sendError(500, "Transaction failed"); }
        }
    }
    private void transact(String account, String type, BigDecimal amount) throws SQLException {
        try (Connection con = Db.connect()) {
            con.setAutoCommit(false);
            try {
                BigDecimal delta = amount;
                if ("WITHDRAWAL".equals(type)) {
                    try (PreparedStatement check = con.prepareStatement("SELECT balance FROM accounts WHERE account_number=? FOR UPDATE")) {
                        check.setString(1, account);
                        try (ResultSet rs = check.executeQuery()) {
                            if (!rs.next()) throw new SQLException("Account not found");
                            if (rs.getBigDecimal(1).compareTo(amount) < 0) throw new SQLException("Insufficient balance");
                        }
                    }
                    delta = amount.negate();
                }
                try (PreparedStatement update = con.prepareStatement("UPDATE accounts SET balance=balance+? WHERE account_number=?")) {
                    update.setBigDecimal(1, delta); update.setString(2, account);
                    if (update.executeUpdate() != 1) throw new SQLException("Account not found");
                }
                try (PreparedStatement insert = con.prepareStatement("INSERT INTO transactions(account_number,type,amount) VALUES(?,?,?)")) {
                    insert.setString(1, account); insert.setString(2, type); insert.setBigDecimal(3, amount); insert.executeUpdate();
                }
                con.commit();
            } catch (SQLException | RuntimeException ex) {
                con.rollback(); throw ex;
            }
        }
    }
}
