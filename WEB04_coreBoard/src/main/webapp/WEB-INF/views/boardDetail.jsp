<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./include/head.jsp" %>


<script type="text/javaScript">

 console.log("실행 시작 :" + "dddddddddd")
	$(document).ready(function(){
		detail();
	})

	function detail(){
	/*  $("input[name='boardNo']").val(board_no); */
		console.log($("#boardView").serialize());

		$.ajax({
			url		: "/getboardDetail",
			type	: "GET",
			dataType: "json",
			data : $("#boardView").serialize(),  //form을 Ajax를 사용하여 서버로 보내기 위한 data형태

			success	: function(result){//컨트롤러에 받은 return 으로 받아온 값을 리절트로 지칭 함
				console.log("fdfjkfgfgfgfgfgfdjf" + result);
				let detail = result.bgdto;
				console.log("boardnum" + detail);
				let data = "";
				data +="<tr>"
				data +="	<td>" + del["boawriter_nm"] + "</td>";
				data +="</tr>"

			},
			error :
				console.log("에러다")

		});
	}
</script>
<body>
<div id="wrap">
<%@ include file="./include/header.jsp" %>

	<div id="container">
<%@ include file="./include/leftgnb.jsp" %>


		<form name="boardView" id="boardView" onsubmit="return false;" action="boardWrite">
			<input type="hidden" value="${boardNo}" name="board_no">

				<table class="write">
			<colgroup>
				<col style="width:150px;">
				<col>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody id="boardtable">

			</tbody>
			</table>

			<div class="btn-box r">
				<a href="#" class="btn btn-green">수정</a>
				<a href="#" class="btn btn-red">삭제</a>
				<a href="#" class="btn btn-default">목록</a>
			</div>
</form>
		</div><!-- /contents -->

	</div><!-- /container -->



<%@ include file="./include/rightgnb.jsp" %>
<%@ include file="./include/footer.jsp" %>
</div><!-- /wrap -->

<script>
	gnb('1','1');
</script>

</body>
</html>
