<%@ tag language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ tag body-content="tagdependent" trimDirectiveWhitespaces="true"%>
<%@ tag description="日付のJavascript処理をつける。"%>
<%-- 属性と変数の定義 --%>
<%@ attribute name="target" required="true"%>
<script type="text/javascript">$('#${target}').datepicker({language : 'ja'});</script>