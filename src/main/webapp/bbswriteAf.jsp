<%@page import="dto.BbsDto"%>
<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8"); // 한글 깨짐 방지
	
	// 값을 받아오기
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	// back-end : DAO 객체를 통해 DB에 DTO 저장
	BbsDao dao = BbsDao.getInstance();
														// 값을 DTO 객체에 저장
	boolean isS = dao.writeBbs(new BbsDto(id, title, content));	
	if(isS){
		%>
			<script type="text/javascript">
			alert("성공적으로 작성 되었습니다");
			location.href = "bbslist.jsp";
			</script>
		<% 
	}else{			
		%>
			<script type="text/javascript">
			alert("다시 작성해 주십시오");
			location.href = "bbswrite.jsp";
			</script>
		<%	
	}
%>


