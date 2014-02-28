<%@page import="cn.clxy.studio.common.aop.LocaleInterceptor"%>
<%@page pageEncoding="UTF-8"%>
<header class="navbar navbar-fixed-top navbar-inverse">
	<div class="navbar-header">
		<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
		<a href="<c:url value='/'/>" class="navbar-brand">&nbsp;</a>
	</div>

	<nav class="collapse navbar-collapse">
		<ul class="nav navbar-nav">
			<li>
				<a href="<c:url value='/'/>">
					<span class="glyphicon glyphicon-home"></span> Home
				</a>
			</li>
			<li>
				<a href="<c:url value='/apps'/>">
					<span class="glyphicon glyphicon-leaf"></span>
					<spring:message code="common.apps" />
				</a>
			</li>
			<li>
				<a href="<c:url value='/about'/>">
					<span class="glyphicon glyphicon-bullhorn"></span> About
				</a>
			</li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<c:if test="${user.logined }">
				<li><a><spring:message code="common.hello"/>&nbsp;${user.firstName}</a></li>
			</c:if>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					<span class="glyphicon glyphicon-globe"></span>
					<c:set var="curLocale">${pageContext.response.locale.language}</c:set>
					<spring:message code='common.lang.${curLocale}' /><b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<c:forEach items="<%=LocaleInterceptor.LANG_NAMES%>" var="name">
						<c:if test="${curLocale != name}">
							<li><a href="?locale=${name}"><spring:message code='common.lang.${name}' /></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</li>
			<c:if test="${user.logined }">
				<li>
					<a href="#" class="navbar-link">
						<span class="glyphicon glyphicon-log-out"></span> ログアウト
					</a>
				</li>
			</c:if>
			<c:if test="${!user.logined }">
				<li>
					<a href="#" class="navbar-link">
						<span class="glyphicon glyphicon-log-in"></span> ログイン
					</a>
				</li>
			</c:if>
		</ul>
	</nav>
</header>
<script type="text/javascript">
/*TODO OAuth2
csm.ajax({
	url : "common/authUrl.json",
	success : function(data, status, xhr) {
		$(".glyphicon-log-in").parent().attr("href", data.loginUrl);
		$(".glyphicon-log-out").parent().attr("href", data.logoutUrl);
	}
});
*/
</script>