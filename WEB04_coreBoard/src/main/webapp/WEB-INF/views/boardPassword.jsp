<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/head.jsp" %>

<script type="text/javascript">
	function passwordcheck(){
	/* 	var boardnum = opener.$("#board_no").val();
		$("#board_no2").val(boardnum);
		location.href = "boardCheckpass";
		console.log("dsdsdsdsds" + $("#board_no2").val(boardnum))
		console.log(boardnum) */
		if(document.boardPw.password.value.length==0){
			alert("비밀번호를 입력하세요");
			document.frm.pass.focus();
			return false;
		}
			/* return true; */
			location.href="boardCheckpass";

	}
</script>
<body>
	<div id="pop-wrap">
		<h1 class="pop-tit">비밀번호 확인</h1>
		<div class="pop-con">
			<form name="boardPw" id="boardPw">
				<input type="hidden" value="${boardNo}" id="board_no" name="board_no">

				<table class="view">
					<colgroup>
					<col style="width:100px;"><col>
					</colgroup>
					<tbody>
					<tr>
						<th>비밀번호</th>
						<td>
							<input type="password" class="input" name="password" style="width:200px;">
							<a href="javascript:void(0);" class="btn btn-red" onclick="passwordcheck()">확인</a>
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