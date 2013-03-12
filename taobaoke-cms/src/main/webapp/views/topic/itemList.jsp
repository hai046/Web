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
							<th style="table-layout: inherit; width: 100px">应用Id</th>
							<th style="table-layout: inherit; width: 100px">专题Id</th>
							<th style="table-layout: inherit; width: 100px">商品Id</th>
							<th style="table-layout: inherit; width: 100px">商品名</th>
							<th style="table-layout: inherit; width: 100px">商品图片</th>
							<th style="table-layout: inherit; width: 100px">状态</th>
							<th style="table-layout: inherit; width: 100px">排列号</th>
							<th style="table-layout: inherit; width: 100px">操作</th>
						</tr>
						
						<c:forEach var="il" items="${topicItemList}">
						<tr>
							<td>${il.id }</td>
							<td>${il.app_id }</td>
							<td>${il.topicId}</td>
							<td>${il.tItemId}</td>
							<td>${il.tItemName}</td>
							<td><img src="${il.realPic}"></img></td>
							<td>${il.status}</td>
							<td>${il.orderNo}</td>
							<td>
								<form action="./itemDel" method="post">
								 	<input type="hidden" name="id" value="${il.id}" />
								 	<input type="hidden" name="topicId" value="${topicId }" style="width:100px"/>
								 	<input type="hidden"  name="currentPage" value="${currentPage }"/>
								 	<input type="hidden"  name="backUrl" value="${backUrl }"/>
								 	<input type="submit" name="del" value="删除" />
								</form>
							</td>
						</tr>
						</c:forEach>
						<tr>
							<form action="./addItem" method="post" class="form-horizontal" enctype="multipart/form-data">
								<td>新添数据项</td>
								<td><input type="text" name="app_id" style="width:100px"/></td>
								<td>
									<input type="text" name="topicId" value="${topicId }" style="width:100px"/>
									<input type="hidden"  name="currentPage" value="${currentPage }"/>
								 	<input type="hidden"  name="backUrl" value="${backUrl }"/>
								</td>
							
								<td><input type="text" name="tItemId" style="width:100px"/></td>
								<td><input type="text" name="tItemName" style="width:100px"/></td>
								<td><input type="file" name="pic" style="width:100px"/></td>
								<td><input type="text" name="status" style="width:100px"/></td>
								<td><input type="text" name="orderNo" style="width:100px"/></td>
								<td><input type="submit" name="ok" value="添加" /></td>
							</form>
						</tr>

					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><a href="${backUrl }">返回</a></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center">
					<c:set var="extraParam" value="topicId=${topicId}" />
					<%@ include file="./../pages.jsp"%>
				</td>
			</tr>
		</table>
		<!--这里是自定义页面结束-->
	</div>
</body>
</html>

<script type="text/javascript">
$(function(){
	$("input[name='del']").bind("click",function(event){
	    if(window.confirm("确定删除？")){
	    	return true;
	    }
		event.preventDefault();
		return false;
	});
});
</script>

