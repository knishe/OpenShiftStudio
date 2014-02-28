<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
	<head>
		<meta charset="utf-8">
		<title>CLXY Studio</title>
		<meta name="description" content="CLXY Studio">
		<st:res type="css" files="bootstrap, main, animation, icons" />
		<link rel="shortcut icon" href="<c:url value='/resources/common/img/favicon.png'/>">
		<script src="<c:url value='/resources/common/js/serversidejs.jsp'/>"></script>
		<st:res type="js"
				files="jquery-2.1.0.min, jquery-ui-1.10.3.custom, bootstrap, main" />
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		<s:res type="js" files="html5shiv" />
	<![endif]-->
	</head>

	<body>
		<%-- ナビバー --%>
		<%@include file="navbar.jsp"%>

		<%--内容 --%>
		<div id="content">
			<%-- エラーメッセージ --%>
			<%@include file="messages.jsp"%>
			<jsp:include page="${subview}" />
		</div>

		<footer class="text-center clear">
			<hr>
			<p>
				&copy; CLXY Studio 2013. All Rights Reserved.
				<a href="mailto:yi.cui.clxy@gmail.com">
					<span class="glyphicon glyphicon-envelope"></span>
					<spring:message code="home.contactUs" />
				</a>
			</p>
		</footer>
	</body>
</html>
