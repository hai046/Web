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
							<th style="table-layout: inherit; width: 100px">专题id</th>
							<th style="table-layout: inherit; width: 100px">星期几</th>
							<th style="table-layout: inherit; width: 100px">更新日期</th>
							<th style="table-layout: inherit; width: 100px">操作</th>
						</tr>
						
						<c:forEach var="ptl" items="${planningTopicList}">
						<tr>
							<td>${ptl.id }</td>
							<td>${ptl.app_id}</td>
							<td>${ptl.topicId}</td>
							<td>${ptl.weekDay}</td>
							<td>${ptl.updateTime}</td>
							<td>
								<form action="./delPlanningTopic" method="post">
								 	<input type="hidden" name="id" value="${ptl.id}" />
								 	<input type="hidden"  name="currentPage" value="${currentPage }"/>
								 	<input type="submit" name="del" value="删除" />
								</form>
							</td>
						</tr>
						</c:forEach>
						<tr>
						<form action="./addPlanningTopic" method="post">
							<td></td>
							<td><input type="text" name="topicId" ></input></td>
							<td><input type="text" name="weekDay" ></input></td>
							<td></td>
							<td>
								<input type="submit" name="del" value="添加" />
							</td>
						</form>
						</tr>

					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td></td>
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

