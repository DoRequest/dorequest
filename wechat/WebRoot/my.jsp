<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="dorequest" uri="/WEB-INF/tld/time.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>欢迎你 ， <c:if test="${sub.sex=='1' }">
    		靓仔！
    	</c:if> <c:if test="${sub.sex=='2' }">
    		靓女！
    	</c:if>
</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	background-color: #E7D9F4;
	margin: 0 auto;
}

#content {
	text-align: left;
	width: 95%;
	height: auto;
	background-color: #99A1DB;
	box-shadow: 12px 12px 80px rgb(90, 85, 94);
	border-radius: 25px;
	font-size: 20px;
	color:#fff;
}
</style>
</head>
<center>
	<body>
		<div style="margin-top:50px;">
			<Strong style="font-size: 50px;font-family:隶书;"><c:if
					test="${sub.sex=='1' }">
    		靓仔简介
    	</c:if> <c:if test="${sub.sex=='2' }">
    		靓女简介
    	</c:if> </Strong>
		</div>
		<div id="content">
			<div style="padding-left: 20px">
				 <img alt="微信头像" style="margin-left: 80px;margin-top:20px;border:1px solid black;box-shadow: 0px 0px 20px 15px rgb(238, 255, 161);" src="${sub.headimgurl }" width="160px" /> 
				<p style="">微信昵称：${sub.nickname }</p>
				<p style="padding-top: 10px">
					&nbsp;&nbsp;性别：<c:if test="${sub.sex=='2' }">妹子</c:if><c:if test="${sub.sex=='1' }">爷们</c:if>
				</p>
				<p style="padding-top: 10px">&nbsp;&nbsp;国籍：${sub.country }</p>
				<p style="padding-top: 10px">&nbsp;&nbsp;省份：${sub.province }</p>
				<p style="padding-top: 10px">&nbsp;&nbsp;城市：${sub.city }</p>
				<p style="padding-top: 10px">关注时间：
						${dorequest:time(sub.subscribeTime)}
				 </p>
			</div>
		</div>
	</body>
</center>
</html>
