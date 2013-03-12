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


	<table>
		<tr>
			<td width="10%"></td>
			<td width="80%">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr class="TableInfoTH">
						<th style="table-layout: inherit; width: 100px">id</th>
						<th style="table-layout: inherit; width: 100px">app_id</th>
						<th style="table-layout: inherit; width: 100px">帖子标题</th>
						<th style="table-layout: inherit; width: 100px">帖子内容</th>
						<th style="table-layout: inherit; width: 100px">帖子分类Id</th>
						<th style="table-layout: inherit; width: 100px">创建时间</th>
					</tr>
					
					<tr>
						<td>${article.id }</td>
						<td>${article.app_id }</td>
						<td>${article.articleName}</td>
						<td>${fn:substring(article.articleBody, 0, 160)}</td>
						<td>${article.categoryId}</td>
						<td>${article.createTime}</td>
					</tr>
				</table>
			</td>
			<td width="10%"></td>
		</tr>
		<tr>
			<td width="10%"></td>
			<td width="80%">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
					<tr class="TableInfoTH">
						<th style="table-layout: inherit; width: 100px">id</th>
						<th style="table-layout: inherit; width: 100px">评论者</th>
						<th style="table-layout: inherit; width: 100px">被评论的人</th>
						<th style="table-layout: inherit; width: 100px">评论内容</th>
						<th style="table-layout: inherit; width: 200px">创建时间</th>
						<th style="table-layout: inherit; width: 100px">操作</th>
					</tr>
					<c:forEach var="comment" items="${commentList}">
					<tr>
						<td>${comment.id }</td>
						<td>${comment.commentorName}</td>
						<td>${comment.replieeName}</td>
						<td>${comment.msg}</td>
						<td>${comment.createTime}</td>
						<td>
							<form action="./commentDel" method="post">
								<input type="hidden" name="commentId" value="${comment.id}" />
								<input type="hidden" name="articleID" value="${comment.sourceId}" />
								<input type="hidden"  name="currentPage" value="${currentPage }"/>
								<input type="hidden"  name="callback" value="${callback }"/>
								<input type="submit" name="del" value="删除" />
							</form>
						</td>
					</tr>
					</c:forEach>
					<form action="./comment" method="post">
					<tr>
						<td>添加评论</td>
						<td>
							<input type="text" name="commentorName"  style="width: 100px"/>
							<input type="hidden" name="sourceId" value="${article.id}"/>
						</td>
						<td><input type="text" name="replieeName" style="width: 100px"/></td>
						<td><input type="text" name="msg"  /></td>
						<td><input type="text" name="createTime"  style="width: 100px"/></td>
						<td>
							<input type="hidden"  name="currentPage" value="${currentPage }"/>
							<input type="hidden"  name="callback" value="${callback }"/>
							<input type="submit" name="add" value="添加" />
						</td>
					</tr>
					</form>
				</table>
			</td>
			<td width="10%"></td>
		</tr>
		<tr>
			<td width="10%"></td>
			<td width="80%">
				<a href="${callBack }">返回</a>
			</td>
			<td width="10%"></td>
	</table>
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



