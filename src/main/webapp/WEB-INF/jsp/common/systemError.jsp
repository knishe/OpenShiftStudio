<%@page pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="cn.clxy.studio.common.web.WebUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>System Exception</title>
<st:res type="css" files="bootstrap, main" />
<link rel="shortcut icon" href="<c:url value='/resources/common/img/favicon.png'/>">
<style type="text/css" media="screen">
.container {
	margin: 10px auto 40px auto;
	width: 600px;
	text-align: center;
}

#suggestions {
	margin-top: 15px;
	color: #ccc;
}

#suggestions a {
	font-weight: 200;
	font-size: 14px;
	margin: 0 10px;
}

img {
	vertical-align: bottom;
}
</style>
</head>
<body>
	<div class="container">
		<h1>
			<st:res type="img" files="cat.gif" />
			&mdash; 500 &mdash;
			<st:res type="img" files="dog.gif" />
		</h1>
		<br>
		<br>
		<h4>
			<spring:message code="exception.system.default" />
		</h4>
		<br>
		<p>
			<spring:message code="exception.system.detail" />
		</p>
		<br>
		<div id="suggestions">
			<a href="#" class="hidden">Contact Support1</a> &mdash;
			<a href="mailto:yi.cui.clxy@gmail.com">
				<i class="glyphicon glyphicon-envelope"></i> Contact Support
			</a> &mdash;
			<a href="#" class="hidden">Contact Support3</a>
		</div>
	</div>
</body>
</html>
<%
	WebUtil.invalidate();
%>
