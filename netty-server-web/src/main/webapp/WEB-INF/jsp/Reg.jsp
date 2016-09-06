<%--
  Created by IntelliJ IDEA.
  User: piecebook
  Date: 2016/9/6
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
</head>
<body>
<form action="account/register" method="post">
    用户名:<input type="text" name="uid">
    密码:<input type="password" name="pwd">
    <input type="radio" name="sex" value="0" checked>男
    <input type="radio" name="sex" value="1">女
    邮件：<input type="text" name="email">
    <input type="submit">
</form>
</body>
</html>
