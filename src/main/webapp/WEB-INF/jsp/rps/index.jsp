<%@page pageEncoding="UTF-8"%>
<div class="col-md-12">
	<div class="page-header">
		<h1>
			<i class="clxyicon-rps-rock"></i>
			<span class="clxyicon-rps-scissor"></span>
			<span class="clxyicon-rps-paper"></span>
		</h1>
	</div>
	<span class="help-block">
		<a href="http://zh.wikipedia.org/wiki/%E7%9F%B3%E5%A4%B4%E3%80%81%E5%89%AA%E5%AD%90%E3%80%81%E5%B8%83">
			石头剪刀布
		</a>
		Have fun!
	</span>
	<hr>
	<div>
		<header>排行榜</header>
		<ol>
			<li>Xxx</li>
			<li>Yyy</li>
			<li>Zzz</li>
			<li>...</li>
			<li>Me！</li>
			<li>...</li>
		</ol>
	</div>
	<div>
		<header>初始页面</header>
		<div>
			名字：<input type="text" placeholder="匿名">登录可保存成绩！
			赛制：
			<select class="form-control">
				<option value="free">任意</option>
				<option value="one">一局定胜负</option>
				<option value="three">三局两胜</option>
				<option value="five">五局三胜</option>
			</select>
			<hr>
			<button class="button">开始</button>
			<hr>
			在线：xxx(robot) 好友？
		</div>
	</div>
	<div>
		<header>对战页面</header>
		<div>MM vs GG</div>
		<hr>
		<div class="col-md-9">
			<button class="button"><i class="clxyicon-rps-rock"></i></button>
			<button class="button"><span class="clxyicon-rps-scissor"></span></button>
			<button class="button"><span class="clxyicon-rps-paper"></span></button>
			<button class="button">Go！<span>Count Down！5.4.3.2.1</span></button>
		</div>
		<div class="col-md-3">
			<div>Win <i class="clxyicon-rps-rock"></i> x <i class="clxyicon-rps-scissor"></i></div>
			<div>Lost <i class="clxyicon-rps-scissor"></i> x <i class="clxyicon-rps-rock"></i></div>
			<div>Win <i class="clxyicon-rps-paper"></i> x <i class="clxyicon-rps-rock"></i></div>
		</div>
		<hr>
		<div>
			<i class="clxyicon-happy"></i>
			<i class="clxyicon-smiley"></i>
			<i class="clxyicon-tongue"></i>
			<i class="clxyicon-sad"></i>
			<i class="clxyicon-wink"></i>
			<i class="clxyicon-grin"></i>
		</div>
	</div>
	<div>
		<header>结束页面</header>
		<div>MM vs GG</div>
		<hr>
		<div>Win！</div>
	</div>
</div>

<script>
</script>
<st:res type="js" files="index" isLocal="true" />
<%@include file="/WEB-INF/jsp/common/adsense.jsp"%>
