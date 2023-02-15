<%@page import="dto.MemberDto"%>
<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
// 일련번호 받기
int seq = Integer.parseInt(request.getParameter("seq"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	MemberDto login = (MemberDto)session.getAttribute("login");
	if(login == null){
		%>
		<script>
		alert('로그인 해 주십시오');
		location.href = "login.jsp";
		</script>
		<%
	}	
	
	BbsDao dao = BbsDao.getInstance();
	// 게시물의 일련번호(seq 컬럼)를 매개변수로 전달하고, 
	// 이를 이용해 데이터베이스에서 게시물 내용을 가져올 것입니다.
	BbsDto dto = dao.getBbs(seq);
	/* dao.updateReadCount(seq); */ 
%>

<h2>수정하기</h2>

<div align="center">

<form action="bbsupdateAf.jsp" method="post">
<input type="hidden" name="seq" value="<%=seq %>"> 

<table border="1">
<colgroup>
	<col style="width: 200px">
	<col style="width: 400px">
</colgroup>

<tr>
	<th>작성자</th>
	<td><%=dto.getId() %></td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" name="title" size="90" value="<%=dto.getTitle() %>">
	</td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea rows="15" cols="90" name="content"><%=dto.getContent() %></textarea></td>
</tr>
<tr>
	<td colspan="2">
		<input type="submit" value="글 수정하기">
	</td>
</tr>
</table>

</form>
</div>

</body>
</html>