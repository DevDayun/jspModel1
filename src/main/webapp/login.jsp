<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login 화면입니다</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js" type="text/javascript" ></script>

<link rel="stylesheet" href="loginCSS.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

</head>
<body>
<div class="login-main">

<h2>Login page</h2>
<form action="loginAf.jsp" method="post" id="login-form">

	<input type="text" id="id" name="id" placeholder="아이디">
	<input type="password" id="pwd" name="pwd" placeholder="비밀번호">
	<!-- label 요소 : 사용자 인터페이스 항목의 설명 
	<label> 을 <input> 요소와 연관시키려면, <input> 에 id 속성을 넣어야합니다. 
	그런 다음 <label> 에 id 와 같은 값의 for 속성을 넣어야합니다.-->
	<label for="chk_save_id">
		<input type="checkbox" id="chk_save_id"> 아이디 기억하기
	</label>
	<input type="submit" value="Login">
	<a href="regi.jsp">회원가입</a>
	
</form>
</div>

<script type="text/javascript">
/* 
	cookie : id, password 저장 == String  Client
			 개발자 도구 - Application - cookies에서 저장된 쿠키 확인 가능
	session : login한 정보 == Object		 Server

*/
let user_id = $.cookie("user_id");

// 저장한 id가 있음
if(user_id != null){
	$('#id').val(user_id);
	$('#chk_save_id').prop("checked", true);
}

$('#chk_save_id').click(function() {
	
	if($('#chk_save_id').is(':checked') == true){
		
		if($('#id').val().trim() == ""){
			alert('id를 입력해 주십시오');
			$('#chk_save_id').prop("checked", false);
		}else{
			//cookie를 저장								저장기간 설정(요즘은 안함)
			$.cookie('user_id', $('#id').val().trim(), {expires:7, path:'./'});
		}
	}else{
		$.removecookie('user_id', {path:'./'} );
	}				
		
});

</script>

</body>
</html>