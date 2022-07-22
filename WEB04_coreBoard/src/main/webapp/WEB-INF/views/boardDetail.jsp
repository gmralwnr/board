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
				console.log("값 확인" + result);
				console.log("title 값 확인"+ result.title);
				$("#title").text(result.title);  //text로 바꾸기
				$("#board_no").text(result.board_no);
				$("#category_cd_nm").text(result.category_cd_nm);
				$("#cont").text(result.cont);
				$("#writer_nm").text(result.writer_nm);
				$("#password").text(result.password);
				$("#view_cnt").text(result.view_cnt);
				$("#reg_dt").text(result.reg_dt);
			},
			error :
				console.log("에러다")

		});
	}
 /* 	function boardwriter(){
 		var url ="boardPassword";
 		var opt ="toolbar=no, memubar=no, scrollbars=no, resizable=no, width=700, height=400" ;
 		window.open(url, "PASSWORD찾기", opt);

 	} */
 	function openWin(url, name){
	//	var url ="boardPassword";
		var opt ="toolbar=no, memubar=no, scrollbars=no, resizable=no, width=700, height=400" ;
		window.open(url,name, opt);

	}
</script>
<body>
<div id="wrap">
<%@ include file="./include/header.jsp" %>

	<div id="container">
<%@ include file="./include/leftgnb.jsp" %>


		<form name="boardView" id="boardView" onsubmit="return false;" action="boardupdate">
			<input type="hidden" value="${boardNo}" name="board_no" id="board_no"> <!-- boardNo을 받아옴 ajax 뿌리기전 boardDetail  -->

				<table class="write">
			<colgroup>
				<col style="width:150px;">
				<col>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody id="boardtable">
			<tr>
				<th class="fir">작성자</th>
				<td id="writer_nm"></td>
				<th class="fir">작성일시</th>
				<td id=reg_dt></td>
			</tr>
			<tr>
				<th class="fir">카테고리</th>
				<td colspan="3" id="category_cd_nm"></td>
			</tr>
			<tr>
				<th class="fir">제목</th>
				<td colspan="3" id="title"></td>
			</tr>
			<tr>
				<th class="fir" >내용</th>
				<td colspan="3" id="cont">
				</td>
			</tr>
			<tr>
				<th class="fir">첨부파일</th>
				<td colspan="3">
					<span><a href="#">상담내역1.xlsx</a></span>
					<br />
					<span><a href="#">상담내역2.xlsx</a></span>
				</td>
			</tr>
			</tbody>
			</table>

			<div class="btn-box r">
				<!-- <a href="javascript:void(0);" class="btn btn-green" onclick="boardwriter()">수정</a> -->
				<a href="javascript:void(0);" class="btn btn-green" onclick="openWin('boardPassword?board_no=${boardNo}', 'update')">수정</a>
				<a href="javascript:void(0);" class="btn btn-red">삭제</a>
				<a href="javascript:void(0);" class="btn btn-default">목록</a>
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
