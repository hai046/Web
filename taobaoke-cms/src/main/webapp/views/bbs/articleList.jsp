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

		<table class="adminList" width="100%" border="0" cellspacing="0"
			cellpadding="0">

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
							<th style="table-layout: inherit; width: 100px">id</th>
							<th style="table-layout: inherit; width: 100px">app_id</th>
							<th style="table-layout: inherit; width: 100px">帖子标题</th>
							<th style="table-layout: inherit; width: 100px">帖子内容</th>
							<th style="table-layout: inherit; width: 100px">帖子分类Id</th>
							<th style="table-layout: inherit; width: 100px">创建时间</th>
							<th style="table-layout: inherit; width: 100px">操作</th>
						</tr>

						<c:forEach var="article" items="${articleList}">
							<tr>
								<td>${article.id }</td>
								<td>${article.app_id }</td>
								<td>${article.articleName}</td>
								<td>${fn:substring(article.articleBody, 0, 160)}</td>
								<td>${article.categoryId}</td>
								<td>${article.createTime}</td>
								<td>
									<form action="./articleDel" method="post">
										<input type="hidden" name="articleId" value="${article.id}" />
										<input type="hidden" name="currentPage"
											value="${currentPage }" /> <input type="hidden"
											name="callback" value="${callBack }" /> <input type="submit"
											name="del" value="删除" />
									</form> <a
									href="./articleUpdate?articleId=${article.id}&callback=${callBack}">修改</a>
									<a
									href="./comment?articleID=${article.id}&callback=${callBack}">查看评论</a>
								</td>
							</tr>
						</c:forEach>
					</table>

				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><a href="./articleUpdate?callback=${callBack}">新加</a></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td align="center"><%@ include file="./../pages.jsp"%>
				</td>
			</tr>
		</table>
		<!--这里是自定义页面结束-->
	</div>
</body>
</html>

<script type="text/javascript">
	$(function() {
		$("input[name='del']").bind("click", function(event) {
			if (window.confirm("确定删除？")) {
				return true;
			}
			event.preventDefault();
			return false;
		});
	});
</script>

