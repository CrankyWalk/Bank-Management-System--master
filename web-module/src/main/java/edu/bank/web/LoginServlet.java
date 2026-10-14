package edu.bank.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override public void init() throws ServletException {
        getServletContext().log("LoginServlet initialized");
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            resp.sendRedirect("login.jsp?error=1"); return;
        }
        try (Connection con = Db.connect();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT account_number, password_hash FROM accounts WHERE username = ?")) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && PasswordUtil.verify(password.toCharArray(), rs.getString("password_hash"))) {
                    HttpSession old = req.getSession(false);
                    if (old != null) old.invalidate();
                    HttpSession session = req.getSession(true);
                    session.setAttribute("accountNumber", rs.getString("account_number"));
                    session.setMaxInactiveInterval(15 * 60);
                    resp.sendRedirect("account");
                } else resp.sendRedirect("login.jsp?error=1");
            }
        } catch (Exception ex) {
            getServletContext().log("Login failure", ex);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to process login");
        }
    }
    @Override public void destroy() { getServletContext().log("LoginServlet destroyed"); }
}
