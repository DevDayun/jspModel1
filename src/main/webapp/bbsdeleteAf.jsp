<%@page import="dao.BbsDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% 
// 일련번호 받기
int seq = Integer.parseInt(request.getParameter("seq"));

//back-end
	BbsDao dao = BbsDao.getInstance();
	boolean isS = dao.deleteBbs(seq);	
	if(isS){
		%>
		<script type="text/javascript">
			alert("성공적으로 삭제 되었습니다");
			location.href = "bbslist.jsp";
			</script>
		<% 
	}else{			
		%>
			<script type="text/javascript">
			alert("삭제에 실패했습니다");
			let seq = "<%=seq %>";
			location.href = "bbsdetail.jsp?seq=" + seq;
			</script>
		<%	
	}
%>