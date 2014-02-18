<%@page pageEncoding="UTF-8"%>
<st:res type="css" files="index" isLocal="true"/>
<div class="jumbotron">
	<h1>
		<st:res type="img" files="logo.png"/>
	</h1>
	<div class="row">
		<p class="text-left col-md-6" id="philosophy">
			<spring:message code="home.philosophy1" />
		</p>
		<p class="col-md-6 text-right">
			<a href="mailto:yi.cui.clxy@gmail.com">
				<span class="glyphicon glyphicon-envelope"></span>
				<spring:message code="home.contactUs" />
			</a>
		</p>
	</div>
</div>
<div class="col-md-12">
	<c:set var="imgBase"><c:url value="/resources/home/img/"/></c:set>
	<c:forEach begin="1" end="6" varStatus="status">
		<div class="row intro">
			<div class="col-md-7 section col-md-offset-${status.index%2==0?'3':'2'}">
				<img src="${imgBase}intro${status.index}.gif" class="${status.index%2==0?'pull-right':''}"/>
				<spring:message code="home.intro${status.index}" />
			</div>
		</div>
	</c:forEach>
	<div class="row intro" >
		<div class="col-md-offset-3 col-md-6 section text-center">
			<img src="${imgBase}intro8.gif" class="pull-left" />
			<spring:message code="home.intro7" />
			<img src="${imgBase}intro7.gif" class="pull-right" />
		</div>
	</div>
</div>

<script>
	var phis = [];
	//<c:forEach begin="1" end="3" varStatus="status">
	phis.push("<spring:message code='home.philosophy${status.index}'/>");
	//</c:forEach>
	function setPhilosophy() {
		var random = 1 + Math.floor(10 * (Math.random() % 1));
		var index = (random == 10) ? 2 : ((random >= 7) ? 1 : 0);
		$("#philosophy").html(phis[index]);
	}

	var colors = ["rgba(0,32,128, .3)", "rgba(255,153,0, .3)", "rgba(71,16,343, .3)", "rgba(163,0,1, .3)"];
	var imgs = [ "bg3.jpg", "bg4.jpg", "bg5.jpg"];
	var target = $(".jumbotron").css("transition", "background 1.5s linear");
	function setBackground() {
		var color = colors.shift();
		var img = imgs.shift();
		target.css({
			"background-color": color,
			background: "url(/resources/home/img/" + img + ") no-repeat",
			"background-size": "cover"
		});
		colors.push(color);
		imgs.push(img);
	}

	$(document).ready(function() {
		setInterval(setPhilosophy, 10000);
		$("#philosophy").click(setPhilosophy);

		/*
		 setInterval(function() {
		 var row = $(".intro:eq(0)");
		 var parent = row.parent();
		 row.fadeOut("slow", function() {
		 row.detach();
		 parent.append(row);
		 row.fadeIn();
		 });
		 }, 12000);
		 */
		setInterval(function() {
			//setBackground();
		}, 3000);
		//setBackground();
	});
</script>