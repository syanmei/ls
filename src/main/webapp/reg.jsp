<%--
  Created by IntelliJ IDEA.
  User: 孙艳梅
  Date: 2021/11/30
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/param2/reg.do">
    用户名：<input name="username"/><br>
    密码：<input name="pwd"/><br>
    生日：<input name="birthday"/><br>
    城市：<input name="address.city"/><br>
    街道：<input name="address.street"/><br>
    电话：<input name="address.phone"/><br>
    <input type="submit" value="reg"/>
</form>
</body>
</html>
