<%@page pageEncoding="UTF-8"%>
<st:res type="css" files="about" isLocal="true"/>
<div class="col-md-12">
	<div class="page-header">
		<h1>About</h1>
	</div>

	<div class="row">
		<div class="col-md-3">
			<div class="bs-sidebar hidden-print">
				<ul class="nav bs-sidenav">
					<li class="active">
						<a href="#links"><spring:message code="home.links" /></a>
					</li>
					<li><a href="#technologies"><spring:message code="home.here"/></a></li>
				</ul>
			</div>
		</div>

		<div class="col-md-9">
			<section id="links" class="anchor row">
				<h3>
					<a><spring:message code="home.links"/></a>
				</h3>
				<div class="col-md-6 text-center">
					<div class="panel panel-default">
						<h4 title='Updated at <fmt:formatDate value="${stackoverflow.updateAt}" type="both"/>'>
							<a href="http://stackoverflow.com/users/2541318/clxy">
								<st:res type="img" files="stackoverflow-logo.gif" isLocal="true"/>
							</a>
						</h4>
						<div class="row">
							<a href="http://stackoverflow.com/users/2541318/clxy" class="col-md-6">
								<st:res type="img" files="stackoverflow.jpg" isLocal="true">
									title="clxy at stackoverflow" width="72" height="72"
								</st:res>
							</a>
							<span class="col-md-5" style="padding: 10px;">
								<span><strong><a href="http://stackoverflow.com/users/2541318/clxy">Clxy</a></strong></span><br/>
								<span>${stackoverflow.reputation}</span><br/>
								<span class="gold">${empty stackoverflow.gold?0:stackoverflow.gold}</span>
								<span class="silver">${stackoverflow.silver}</span>
								<span class="bronze">${stackoverflow.bronze}</span>
							</span>
						</div>
					</div>
				</div>
				<div class="col-md-6 text-center">
					<div class="panel panel-default">
						<h4 title='Updated at <fmt:formatDate value="${iteye.updateAt}" type="both"/>'>
							<a href="http://clxy.iteye.com/"><st:res type="img" files="iteye-logo.gif" isLocal="true"/></a>
						</h4>
						<div class="row">
							<a href="http://clxy.iteye.com/blog/answered_problems" class="col-md-6">
								<st:res type="img" files="iteye.jpg" isLocal="true">
									title="clxy at ITeye" width="72" height="72"
								</st:res>
							</a>
							<span class="col-md-5" style="padding: 10px;">
								<span><strong><a href="http://clxy.iteye.com/blog/answered_problems">clxy</a></strong></span><br/>
								<span class="score">${iteye.reputation}</span><br/>
								<span class="gold">${iteye.gold}</span>
								<span class="silver">${iteye.silver}</span>
								<span class="bronze">${iteye.bronze}</span>
							</span>
						</div>
					</div>
				</div>
			</section>
			<hr>
			<section id="technologies" class="anchor row">
				<h3><a><spring:message code="home.here"/></a></h3>
				<div class="col-md-12">
					<div class="panel panel-default" style="padding: 10px">
						<a href="http://java.oracle.com">
							<st:res type="img" files="java.jpg" isLocal="true"/>Java
						</a>
						<hr>
						<a href="http://www.springsource.org/">
							<st:res type="img" files="spring.jpg" isLocal="true">width="41" height="41"</st:res>
								Spring &MVC
							</a>
							, 
							<a href="http://www.hibernate.org/subprojects/validator.html">
							<st:res type="img" files="hibernate.gif" isLocal="true">width="41" height="41"</st:res>
								Hibernate-Validator
							</a>
							, 
							<a href="http://jsoup.org/‎">Jsoup</a>
							, 
							<a href="http://jackson.codehaus.org/‎">Jackson</a> 
							, 
							<a href="http://commons.apache.org/">
							<st:res type="img" files="apache.gif" isLocal="true">width="60" height="18"</st:res>
								Apache Commons
							</a>
							<hr>
							<a href="https://developers.google.com/appengine/">
							<st:res type="img" files="gae.gif" isLocal="true"/>GAE
						</a>
						<hr>
						<a href="http://getbootstrap.com/">Bootstrap</a>
						, 
						<a href="http://glyphicons.com/">Glyphicons</a>
						, 
						<a href="http://www.jqplot.com/">jqPlot</a>
						, 
						<a href="http://icomoon.io/">
							<st:res type="img" files="icomoon.jpg" isLocal="true">width="41" height="41"</st:res>
							IcoMoon
						</a>
						, 
						<a href="http://jquery.com/">
							<st:res type="img" files="jquery.gif" isLocal="true">width="41" height="41"</st:res>
								jQuery
							</a>
							, 
							<a href="https://github.com/blueimp/jQuery-File-Upload">jQuery File Upload Plugin</a>
							, 
							<a href="http://jqueryui.com/">
							<st:res type="img" files="jquery-ui.jpg" isLocal="true">width="41" height="41"</st:res>
								jQuery UI
							</a>
							<hr>
						<st:res type="img" files="introx2.gif" isLocal="true">title="Links"</st:res>
							and 
						<st:res type="img" files="introx1.gif" isLocal="true">title="Rocky"</st:res>
						are <a href="http://office.microsoft.com/">Microsoft Office Assistants.</a>
					</div>
				</div>
			</section>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		var $window = $(window);
		var $body = $(document.body);
		var $sideBar = $('.bs-sidebar');
		var navHeight = $('.navbar').outerHeight(true) + 10;

		$body.scrollspy({
			target: '.bs-sidebar',
			offset: navHeight
		});

		// back to top
		setTimeout(function() {
			$sideBar.affix({
				offset: {
					top: navHeight
				}
			});
		}, 100);
	});
</script>