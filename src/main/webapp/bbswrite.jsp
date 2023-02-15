<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	MemberDto login = (MemberDto)session.getAttribute("login");
	if(login == null){ 
		// session 영역에 “login”라는 속성값이 있는지 확인합니다.
		// 값이 null이면 로그인하지 않았다는 뜻입니다.
		%>
		<script>
		alert('로그인 해 주십시오'); /* 로그인하지 않았다면 경고창을 띄운 후 */
		location.href = "login.jsp"; /* 로그인 페이지로 이동합니다. */ 
		</script>
		<%
	}	
%>      
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bbs write</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>

</head>
<body>

<h1>글쓰기</h1>

<div align="center">

<form action="bbswriteAf.jsp" id="frm" method="post">

<table border="1">
<col width="200"><col width="400">

<tr>
	<th>아이디</th>
	<td>
		<%-- <input type="text" name="id" size="50px" value="<%=login.getId() %>" readonly="readonly"> --%>
		
		<%=login.getId() %>
		<input type="hidden" name="id" value="<%=login.getId() %>">
	</td>
</tr>
<tr>
	<th>제목</th>
	<td>
		<input type="text" id="title" name="title" size="50px" placeholder="제목을 작성해주세요">
	</td>
</tr>
<tr>
	<th>내용</th>
	<td>
		<textarea rows="20" cols="50px" id="content" name="content" placeholder="내용을 작성해주세요"></textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
		<!-- <input type="submit" value="글쓰기"> -->
		<button type="button">글쓰기</button>
	</td>
</tr>

</table>
</form>
</div>

<script type="text/javascript">
$(document).ready(function() { /* 폼에 필수 항목인 ‘제목’과 ‘내용’이 입력되어 있는지 확인합니다. */
	
	$("button").click(function() {
		
		if($("#title").val().trim() == "" ){
			alert("제목을 작성해 주십시오");
			return;
		}else if($("#content").val().trim() == "" ){
			alert("내용을 작성해 주십시오");
			return;
		}else{
			$("#frm").submit();
		}		
	});	
});
</script>


</body>
</html>

