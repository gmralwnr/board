<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="./include/head.jsp" %>

	<script type="text/javaScript">

		console.log("실행 시작 :" + "dddddddddd")
		//Detail 정보 가져와서 뿌릴 때
		$(document).ready(function(){
				detail();
				//ajax 파일 리스트 조회 추가
		})

		function detail(){
			/*$("input[name='board_no']").val(board_no); */
			// console.log($("#boardView").serialize());

			$.ajax({
				url		: "/getboardDetail",
				type	: "GET",
				dataType: "json",
				data 	: $("#searchBoardForm").serialize(),  //form을 Ajax를 사용하여 서버로 보내기 위한 data형태

				success	: function(result){//컨트롤러에 받은 return 으로 받아온 값을 리절트로 지칭 함
					console.log("값 확인" + result);
					console.log("title 값 확인"+ result.title);
					$("#title").text(result.title);  //text로 바꾸기
					$("#board_no").text(result.board_no);
					$("#category_cd_nm").text(result.category_cd_nm);
	/*text와  html의 차이
	let lText = "<font color='red'>안녕하셍</font>";
	$("#category_cd_nm").text(lText);
	$("#category_cd_nm").html(lText);
	*/
					$("#cont").text(result.cont);
					$("#writer_nm").text(result.writer_nm);
					$("#password").text(result.password);  //수정할 때 필요?
					$("#view_cnt").text(result.view_cnt);

					//시간 쪼개기 실행된 결과 값으로 자바로 돌릴 수 없음 동적 jstl 사용이 불가 html 이 만들어지기 전에 사용 가능
					console.log(result.reg_dt);

					let reg_dt_temp = result.reg_dt;
					let reg_dt_real  = reg_dt_temp.substring(0,4); // yyyy
						reg_dt_real += "-";
						reg_dt_real += reg_dt_temp.substring(4,6); // mm
						reg_dt_real += "-";
						reg_dt_real += reg_dt_temp.substring(6,8); // dd
						reg_dt_real += " ";
						reg_dt_real += reg_dt_temp.substring(8,10);
						reg_dt_real += ":";
						reg_dt_real += reg_dt_temp.substring(10,12);
						reg_dt_real += ":";
						reg_dt_real += reg_dt_temp.substring(12,14);

					console.log(reg_dt_real);

					$("#reg_dt").text(reg_dt_real);

				/* 	$("#date").text(result.date); //시간 초 나오게 */
				},
				error :function(){
					console.log("detail() 오류");
				}
			}); //ajax 끝나는 (ajax는 안에 함수 이기 떄문에 세미콜론을 붙여줌)
		}		//메소드이기 때문에  } 만 사용

		  /*function boardwriter(){
	 		var url ="boardPassword";
	 		var opt ="toolbar=no, memubar=no, scrollbars=no, resizable=no, width=700, height=400" ;
	 		window.open(url, "PASSWORD찾기", opt);
			} */

		//수정 & 삭제 팝업창 .. 컨트롤러에서 비교하기 때문에 창만열어줌
		function openWin(url, name){
			//var url ="boardPassword";
			var opt ="toolbar=no, memubar=no,status=no,  scrollbars=no, resizable=no, width=700, height=400, top=50, left=300" ;
			window.open(url,name, opt);
		}

		//팝업창에서 수정창으로 넘어가는 함수
		function updateboard(){
			$("#searchBoardForm").attr("onsubmit", '');
			$("#searchBoardForm").attr("method", 'post');
			$('#searchBoardForm').attr("action", "/boardUpdateForm");
			$('#searchBoardForm').submit();
	 	}

		//팝업창에서 삭제로 넘어가는 함수
	 	function deleteboard(){
	 		$("#searchBoardForm").attr("onsubmit", '');
			$("#searchBoardForm").attr("method", "post");
//			$("#searchBoardForm #_method").val("method", "delete");
	 		$("#searchBoardForm").attr("action", "/boardDeleteForm");
			$('#searchBoardForm').submit();
	 	}

		//목록으로 넘어가는 함수
	 	function boardList(){
	 		//$("input[name='board_no']").val(board_no);  //board_no 값을 board_no에 값을 넘겨줌 -> 컨트롤러에 받아서 값을 내보냄 // 폼테그에도 INPUT값을 적어줘야함
			$("#searchBoardForm").attr("onsubmit", '');
			//$("#boardView").attr("method", 'post');
			$("#searchBoardForm").attr("method", 'post');
			$('#searchBoardForm').attr("action", "/").submit();

	 	}

	 	gnb('1','1');
	</script>
</head>
<body>
<div id="wrap">
<%@ include file="./include/header.jsp" %>

	<div id="container">
<%@ include file="./include/leftgnb.jsp" %>
		<div id="contents"><!-- 바디 -->

			<div class="location">
				<span class="ic-home">HOME</span><span>커뮤니티</span><em>통합게시판</em>
			</div>

			<div class="tit-area">
				<h3 class="h3-tit">통합게시판</h3>
			</div>
		<!-- 검색조건 유지  -->
		<form name="search" id="searchBoardForm" method="post">
				<input type="hidden" name="currentPage" id="currentPage" value="${brdto.currentPage}"/>
				<input type="hidden" name="pointCount" id="pointCount"  value="${brdto.pointCount}" />  <!-- form 밖에 있는것을 담아오기 위해 input hidden  을 사용 -->
				<input type="hidden" name="offsetData" id="offsetData" value="${brdto.offsetData}" />
				<input type="hidden" name="keyword" id="keyword" value="${brdto.keyword}">
				<input type="hidden" name="type" id="type" value="${brdto.type}">
				<input type="hidden" name="category" id="category" value="${brdto.category}">
				<input type="hidden" name="arrayboard" id="arrayboard" value="${brdto.arrayboard}">
				<input type="hidden" value="${board_no}" name="board_no" id="board_no">
				<input type="hidden" name="_method" id="_method">
			<!-- //	<input type="hidden" name="board_no" id="board_no"/> -->
		</form>
		<!-- 검색조건 유지때문에form을 하나로 만듦 -->
		<!-- 디테일 form  -->
		<!-- <form name="boardView" id="boardView" onsubmit="return false;"  action="/boardUpdateForm"> -->
		<%-- 	<input type="hidden" value="${board_no}" name="board_no" id="board_no">  --%><!-- board_no을 받아옴 ajax 뿌리기전 boardDetail  -->
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
				<td id="reg_dt" ></td>
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
				<td colspan="3" id="cont" style="white-space:pre-wrap; ">
				</td>
			</tr>
			<tr>
				<th class="fir">첨부파일</th>
				<td colspan="3" id="file_area">
				<!-- 
					<span>fresult</span>
					<br />
					<span><a href="#">상담내역2.xlsx</a></span>
				 -->
				 
				</td>
			</tr>
			</tbody>
			</table>

			<div class="btn-box r">
				<!-- <a href="javascript:void(0);" class="btn btn-green" onclick="boardwriter()">수정</a> -->
				<a href="javascript:void(0);" class="btn btn-green" onclick="openWin('/boardPassword', 'update')"  >수정</a>
				<a href="javascript:void(0);" class="btn btn-red"  onclick="openWin('/boardPassword', 'delete')">삭제</a>
				<a href="javascript:void(0);" class="btn btn-default"  onclick="boardList()">목록</a>
			</div>
<!-- </form> -->
		</div><!-- /contents -->

	</div><!-- /container -->



<%@ include file="./include/rightgnb.jsp" %>
<%@ include file="./include/footer.jsp" %>
</div><!-- /wrap -->

</body>
</html>
