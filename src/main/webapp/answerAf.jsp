<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8"); // 한글 깨짐 방지
	
	int seq = Integer.parseInt(request.getParameter("seq"));
	
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	// back-end : Dao에서 어떤 함수를 연결해야 하는지 항상 주의!
	BbsDao dao = BbsDao.getInstance();
	boolean isS = dao.answer(seq, new BbsDto(id, title, content));	
	if(isS){
		%>
			<script type="text/javascript">
			alert("답글이 성공적으로 작성 되었습니다");
			location.href = "bbslist.jsp";
			</script>
		<% 
	}else{			
		%>
			<script type="text/javascript">
			alert("답글을 다시 작성해 주십시오");
			let seq = "<%=seq %>";
			location.href = "answer.jsp?seq=" + seq;
			</script>
		<%	
	}
%>
