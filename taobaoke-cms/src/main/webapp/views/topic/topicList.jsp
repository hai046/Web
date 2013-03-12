<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CMS</title>
</head>
<body class="addUser" bgcolor="#EEEEEE">
	<%@ include file="./../header.jsp"%>
	<div id="divFrame" runat="server">

		<table class="adminList" width="100%" border="0" cellspacing="0" cellpadding="0">

			<tr>
				<td>&nbsp;</td>
				<td></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>

					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr class="TableInfoTH">
							<th style="table-layout: inherit; width: 100px">id</th>
							<th style="table-layout: inherit; width: 100px">应用id</th>
							<th style="table-layout: inherit; width: 100px">专题名</th>
							<th style="table-layout: inherit; width: 100px">专题图片</th>
							<th style="table-layout: inherit; width: 100px">专题文字</th>
							<th style="table-layout: inherit; width: 100px">状态</th>
							<th style="table-layout: inherit; width: 100px">类型</th>
							<th style="table-layout: inherit; width: 100px">商品号</th>
							<th style="table-layout: inherit; width: 100px">url</th>
							<th style="table-layout: inherit; width: 100px">排列号</th>
							<th style="table-layout: inherit; width: 100px">操作</th>
						</tr>
						
						<c:forEach var="il" items="${topicList}">
						<tr>
							<td><a href="./topicAdd?id=${il.id }&backUrl=${callBack}">${il.id }</a></td>
							<td>${il.app_id}</td>
							<td><a href="./itemList?topicId=${il.id }&backUrl=${callBack}">${il.topicName}</a></td>
							<td><img src="${il.realTopicPic}"></img></td>
							<td>${il.content}</td>
							<td>${il.status == 1 ? '不显示' : '显示'}</td>
							<td>${il.type == 0 ? '专题' : il.type == 1 ? '商品' : il.type == 2 ? '网页' : '其他'}</td>
							<td>${il.numIid}</td>
							<td>${il.url}</td>
							<td>${il.orderNo}</td>
							<td>
								<form action="./changeTopicStatus" method="post">
								 	<input type="hidden" name="id" value="${il.id}" />
								 	<input type="hidden"  name="currentPage" value="${currentPage }"/>
								 	<input type="submit" name="del" value="${il.status == 1 ? '显示' : '不显示'}" />
								</form>
							</td>
						</tr>
						</c:forEach>
						

					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><a href="/topic/topicAdd">添加新专题</a></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					<%@ include file="./../pages.jsp"%>
				</td>
			</tr>
		</table>
		<!--这里是自定义页面结束-->
	</div>
</body>
</html>

<script type="text/javascript">
$("input[name='del']").bind("click",function(event){
    if(window.confirm("确定改变设置？")){
    	return true;
    }
	event.preventDefault();
	return false;
});
</script>

