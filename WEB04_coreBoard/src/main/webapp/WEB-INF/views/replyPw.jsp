<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="./include/head.jsp" %>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type ="text/javascript">
	function passwordcheck(){
		$.ajax({
			url			:	"/replypwcheck",
			type		:	"POST",
			dataType 	:	"json",
			data		:	{"reply_no" : opener.$("#reply_no").val(),
							"replyPw" : $("#replyPw").val()
							},
			success		: function(result){
				let msg = result.msg ;
				let flag = result.flag;
				let reply_no =opener.$("#reply_no").val()

				if(flag == "true"){

					if(window.name =="replyUpdate"){ //댓글 수정
						let ans = confirm(msg)
						if(ans){
						opener.replyUpdateform(1, reply_no);
						}
					}else if(window.name =="replyDelete"){ //댓글 삭제
						 ans = confirm("삭제하시겠습니까?");
						if(ans){
						opener.replyDeleteform(reply_no);
						self.close();
						}
					}
					self.close();
				}
			}
		});
	}
</script>
</head>
<body>
<div id="pop-wrap">
		<h1 class="pop-tit">reply 비밀번호 확인</h1>
		<div class="pop-con">
			<form name="replyPass" id="replyPass"   method="post" onsubmit="return false;"><!--   첫번째 자바스크립 action="boardUpdateForm"   두번쨰 자바스크립  action="boardCheckpass"-->
 				<!--opener 로 board_no 을 받아와서 사용 할 수 있어서 주석 처리함   -->
 			<!-- 	 <input type="hidden" id="reply_no" name="reply_no" > -->
				<%-- <input type="hidden" value="${pass}" id="pass" name="pass"> --%>
				<table class="view">
					<colgroup>
					<col style="width:100px;"><col>
					</colgroup>
					<tbody>
					<tr>
						<th>비밀번호</th>
						<td>
							<input type="password" class="input" name="replyPw" id="replyPw" style="width:200px;">
							<input type="submit"  value="확인" class="btn btn-red" onclick="passwordcheck()">
							<!-- <a href="javascript:void(0);" class="btn btn-red"  onclick="return  passwordcheck()">확인</a>-->
						</td>
					</tr>
					</tbody>
				</table>
					${message}
			</form>
			<div class="btn-box">
				<a href="javascript:self.close();" class="btn btn-default">닫기</a>
			</div>

		</div><!-- /pop-con -->
</div><!-- /pop-wrap -->
</body>
</html>