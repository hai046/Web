<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:if test="${pageCount > 0 }">
 <form action="${baseUrl}" method="get">
        <input type="hidden" name="pageCount" value="${pageCount}" />
        <c:if test="${pageCount <= 5 || currentPage<=3 }">
			<c:set var="start" value="1"/>
			<c:if test="${pageCount<=5}" >
				<c:set var="end" value="${pageCount}"/>
			</c:if>
			<c:if test="${pageCount>5}" >
				<c:set var="end" value="5"/>
			</c:if>
		</c:if>
        <c:if test="${pageCount > 5 && currentPage > 3}">
			<c:if test="${pageCount-currentPage<2}" >
				<c:set var="start" value="${pageCount-4}"/>
				<c:set var="end" value="${pageCount}"/>
			</c:if>
			<c:if test="${pageCount-currentPage>=2}" >
				<c:set var="start" value="${currentPage-2}"/>
				<c:set var="end" value="${currentPage+2}"/>
			</c:if>
		</c:if>
        <c:if test="${start > 1}">
			<a href="${baseUrl}?currentPage=1&${extraParam}"><font size="2">首页</font></a>
		</c:if>
        <c:forEach var="index" begin="${start}" end="${end}">
			<c:if test="${index==currentPage}">
				<font size="2">第${index}页</font>
			</c:if>
			<c:if test="${index!=currentPage}">
				<a href="${baseUrl}?currentPage=${index}&${extraParam}"><font size="2">第${index}页</font></a>
			</c:if>
		</c:forEach>
        <c:if test="${end != pageCount}">
			<a href="${baseUrl}?currentPage=${pageCount}&${extraParam}"><font size="2">末页</font></a>
		</c:if>
        <input type="text" name="currentPage" value="" size="2" />
        <input type="submit" name="" value="跳转到" />
        一共${count}个资料
        </form>
</c:if>
<c:if test="${pageCount == null }">
	<c:if test="${currentPage > 0}">
		<a href="${baseUrl}?currentPage=${currentPage-1}&${extraParam}">上一页</a>
	</c:if>
	第${currentPage}页
	<c:if test="${count > 0}">
	<a href="${baseUrl}?currentPage=${currentPage+1}&${extraParam}">下一页</a>
	</c:if>
</c:if>