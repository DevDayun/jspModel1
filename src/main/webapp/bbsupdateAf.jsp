<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
request.setCharacterEncoding("utf-8"); // 한글 깨짐 방지

// 일련번호 받기
int seq = Integer.parseInt(request.getParameter("seq"));

//값을 받아오기
String title = request.getParameter("title");
String content = request.getParameter("content");

//back-end
	BbsDao dao = BbsDao.getInstance();
	boolean isS = dao.updateBbs(seq,title,content);	
	if(isS){
		%>
		<script type="text/javascript">
			alert("성공적으로 수정 되었습니다");
			location.href = "bbslist.jsp";
			</script>
		<% 
	}else{			
		%>
			<script type="text/javascript">
			alert("수정에 실패했습니다");
			let seq = "<%=seq %>";
			location.href = "bbsupdate.jsp?seq=" + seq;
			</script>
		<%	
	}
%>
