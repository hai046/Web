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
				<td align="center"></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>

					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr class="TableInfoTH">
							<th style="table-layout: inherit; width: 100px">类别名</th>
							<th style="table-layout: inherit; width: 100px">状态</th>
						</tr>

						<c:forEach items="${allStatus}" var="entry">
						<tr>
							<td>${entry.key.name}</td>
							<td>${entry.value ? '已爬完' : '未爬完'}</td>
						</tr>
						</c:forEach>
						<tr>
							<td>整体状态</td>
							<td>${isRun ? '运行中' : '未运行'}</td>
						</tr>
					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<form action="./run" method="post">
						<input type="text" name="app_id" value="-1"}/>
						<input type="submit" name="" value="开始运行" ${isRun ? 'disabled' : ''}/>
					</form>
					通过 app_id 来帕取      如果是-1运行爬取所有的分类
					
					
					<br><br></br><br></br>
					<form action="./runByTcId" method="post">
						<input type="text" name="tcId" value="-1"}/>
						<input type="submit" name="" value="开始运行" ${isRun ? 'disabled' : ''}/>
					</form>
					通过 categor_id 来帕取 
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<!--这里是自定义页面结束-->
	</div>
</body>
</html>

<script type="text/javascript">
	
</script>

