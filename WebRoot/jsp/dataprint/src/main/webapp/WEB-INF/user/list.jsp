<%--
  Created by IntelliJ IDEA.
  User: qingyun
  Date: 18/1/12
  Time: 下午7:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${user}
<c:forEach items="${list}" var="u">
    ${u.user}<br>
</c:forEach>
</body>
</html>
