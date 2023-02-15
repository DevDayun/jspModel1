<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@page import="dto.MemberDto"%>
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
	dao.updateReadCount(seq); // 조회수 증가
	// 게시물의 일련번호(seq 컬럼)를 매개변수로 전달하고, 
	// 이를 이용해 데이터베이스에서 게시물 내용을 가져올 것입니다.
	BbsDto dto = dao.getBbs(seq); 
%>

<h1>상세 글보기</h1>

<div align="center">

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
	<td><%=dto.getTitle() %></td>
</tr>
<tr>
	<th>작성일</th>
	<td><%=dto.getWdate() %></td>
</tr>
<tr>
	<th>조회수</th>
	<td><%=dto.getReadcount() %></td>
</tr>
<tr>
	<th>정보</th>
	<td><%=dto.getRef() %>-<%=dto.getStep() %>-<%=dto.getDepth() %></td>
</tr>
<tr>
	<th>내용</th>
	<td><textarea rows="15" cols="90"><%=dto.getContent() %></textarea></td>
</tr>
</table>

<br>
	<button type="button" onclick="location.href='bbslist.jsp'">글목록</button>
	<button type="button" onclick="answerBbs(<%=dto.getSeq() %>)">답글</button>
<%
if(dto.getId().equals(login.getId())){
	// 1. session 영역에 속성값이 있는가? 즉, 로그인한 상태인가?
	// 2. 로그인(세션) 아이디와 DTO 객체에 저장된 아이디가 일치하는가? 즉, 작성자 본인인가?
	%>	
	<button type="button" onclick="updateBbs(<%=dto.getSeq() %>)">수정</button>
	<button type="button" onclick="deleteBbs(<%=dto.getSeq() %>)">삭제</button>
	<% 
}
%>	
</div>

<script type="text/javascript">

function answerBbs(seq) {
	location.href = "answer.jsp?seq=" + seq;
}

function updateBbs (seq) {
	location.href = "bbsupdate.jsp?seq=" + seq;
}

function deleteBbs (seq) {
	location.href = "bbsdeleteAf.jsp?seq=" + seq; // update del=1
}

// readcount 증가

</script>


</body>
</html>