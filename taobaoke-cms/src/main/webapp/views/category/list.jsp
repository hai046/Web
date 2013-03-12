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
							<th style="table-layout: inherit; width: 100px">app_id</th>
							<th style="table-layout: inherit; width: 100px">parentId</th>
							<th style="table-layout: inherit; width: 100px">name</th>
							<th style="table-layout: inherit; width: 100px">图标url</th>
							<th style="table-layout: inherit; width: 100px">cids</th>
							<th style="table-layout: inherit; width: 100px">order_no</th>
							<th style="table-layout: inherit; width: 100px">更新日期</th>
						</tr>
						
						<c:forEach var="c" items="${categoryList}" varStatus="status">
						<tr>
							<td>${c.id}<a href="./add?id=${c.id }&backUrl=${callBack}">修改</a><a href="/item/list?tcId=${c.id }&backUrl=${callBackForNotSameRoot}">查看item</a></td>
							<td>${c.app_id}</td>
							<td>${c.parentId}</td>
							<td>${c.name}</td>
							<td><img src="${c.realIconUrl}"></img></td>
							<td>${c.cid}</td>
							<td>${c.orderNo}</td>
							<td>${c.updateTime}</td>
						</tr>
						</c:forEach>
						

					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><a href="./add?backUrl=${callBack}">新加category</a></td>
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
	
</script>

