<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="./include/head.jsp" %>
<script type="text/javascript">

		$(document).ready(function(){
// 			passcheck();
//			alert("window.opener.document.boardView.board_no.value >> " + window.opener.document.boardView.board_no.value);
		})

		function passcheck(){
			$.ajax({
				url			:	"/checkPass",
				type		:	"POST",
				dataType	:	"json",
				data		:	{"board_no" : opener.$("#board_no").val(),
								 "board_pass" : $("#board_pass").val()
								//$("#boardPw").serialize(),
								//$(opener.document.boardView.board_no.value).serialize(),
								},
				success		:	function(result){

					let msg = result.msg;
					let flag = result.gflag;

						alert(msg); //커ㄴ트롤러에서 이미 실행이 되어서 위에 하나만 적어줌
					if(flag =="true"){
						if(window.name=="update"){
						opener.updateboard(); //form 자체를 submit 함
						//window.opener.location.href="boardUpdateForm?board_no=${board_no}";
						self.close();
						}else if(window.name=="delete"){
							let ans =  confirm("정말로 삭제하시겠습니까?");
								if(ans){
									opener.deleteboard();
							//		window.opener.location.href="boardDeleteForm?board_no=${board_no}";

								}
							self.close();
						}

					}else{
						//alert(msg);
						document.boardPw.board_pass.focus();
						//return false;
					}

				},
					error : function(){
						alert("passcheck()확인 실패 !");
						console.log("dfsdfsdfsdf" + data);
					}

			});
		}


	/*function passwordcheck(){
		if(document.boardPw.password.value.length==0){
			alert("비밀번호를 입력하세요");
			document.boardPw.password.focus();
			return false;

		 }else if(document.boardPw.password.value != document.boardPw.pass.value){
					alert("비밀번호가 일치하지 않습니다");

					document.boardPw.password.focus();
					return false;

		}else if(document.boardPw.password.value == document.boardPw.pass.value){
					alert("비밀번호가 일치");
					console.log("!!!!!!!!! " + document.boardPw.pass.value)
					var ans = confirm("수정하시겠습니까?");
				if(ans){

				window.opener.location.href="boardUpdateForm?board_no=${board_no}";
*/
					/* $("#board_no").val(${board_no});
					$("#boardPw").submit(); */
				/*	window.opener.name="boardwrite";
					document.boardPw.traget ="boardwrite"
					document.boardPw.action="/boardUpdateForm"
					document.boardPw.submit();

					//	window.opener.document.body.appendChild(form);
				//	form.submit();


					// 	document.boardPw.action = "boardUpdateForm";
					//	opener.document.boardPw.submit();
					/*var f=document.forms.bpardPw;
					document.domain="localhost";
					opener.name="boardUpdateForm";
					f.target=opener.name;
					f.submit();
					*/
/*						self.close();
						return true;
				}

				self.close();
		}

	}
*/
	/*컨트롤러에서 사용시
		function passwordcheck(){
			if(document.boardPw.password.value.length==0){
				alert("비밀번호를 입력하세요");
				document.boardPw.password.focus();
				return false;
			}else{
				 window.location.href="boardCheckpass?board_no=${board_no}";
			// 	var form = document.boardPw;
			//	from.submit();
				return true;
			}
		}
*/
	function passwordcheck(){
		if(document.boardPw.board_pass.value.length==0){
			alert("비밀번호를 입력하세요");
			document.boardPw.board_pass.focus();
			return false;
		}else{
				passcheck();

		//	}
		//	var num = window.opener.document.boardView.board_no.value;
		//	console.log("board_no" + num);
		//	passcheck(num);

		/*  window.location.href="boardCheckpass?board_no=${board_no}";
		// 	var form = document.boardPw;
		//	from.submit(); */
			return true;
		}
	}

	</script>
</head>
<body>
	<div id="pop-wrap">
		<h1 class="pop-tit">비밀번호 확인</h1>
		<div class="pop-con">
			<form name="boardPw" id="boardPw"   method="post" onsubmit="return false;"><!--   첫번째 자바스크립 action="boardUpdateForm"   두번쨰 자바스크립  action="boardCheckpass"-->
 				<!--opener 로 board_no 을 받아와서 사용 할 수 있어서 주석 처리함   -->
 				<%-- <input type="hidden" value="${board_no}" id="board_no" name="board_no" > --%>
				<%-- <input type="hidden" value="${pass}" id="pass" name="pass"> --%>
				<table class="view">
					<colgroup>
					<col style="width:100px;"><col>
					</colgroup>
					<tbody>
					<tr>
						<th>비밀번호</th>
						<td>
							<input type="password" class="input" name="board_pass" id="board_pass" style="width:200px;">
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