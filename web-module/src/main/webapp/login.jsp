<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html><html><head><title>Bank Login</title>
<style>body{font-family:Arial;background:#eef2f7;display:grid;place-items:center;height:90vh}.card{background:white;padding:32px;border-radius:14px;box-shadow:0 8px 28px #0001;width:320px}input,button{box-sizing:border-box;width:100%;padding:12px;margin:8px 0}button{background:#1264a3;color:white;border:0;border-radius:6px}</style></head>
<body><form class="card" method="post" action="login"><h1>Bank Login</h1>
<% if ("1".equals(request.getParameter("error"))) { %><p style="color:#b00">Invalid username or password.</p><% } %>
<label>Username</label><input name="username" required autocomplete="username">
<label>Password</label><input type="password" name="password" required autocomplete="current-password">
<button type="submit">Sign in</button><p>Assessment demo web module</p></form></body></html>
