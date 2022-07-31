<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="./include/head.jsp" %>

	<script type="text/javascript">
		//html 다 실행되고 난 후 document.ready 가 실행

		console.log("실행시작 : " + "시자자자자ㅏㄱㅇ");

		$(document).ready(function(){//시작하기전 등록일때 조회 할 여부

/* 			*****c태그도 javascript 에 쓸 수 있다
 			<c:choose>
				<c:when test="${empty board_no }">
// 					alert("등록이지롱~~~~");
				</c:when>
				<c:when test="${not empty board_no }">
// 					alert("수정이지롱~~~~");
					// getBoard();
				</c:when>
			</c:choose> */

			if(document.boardUpdate.board_no.value!=""){//$("#board_no").val() //document 안에 있을 떈
				getBoard();
			}

			// 메뉴 하이라이팅
			gnb('1','1');
		});

		//게시판 수정 정보 가져오기
		function getBoard(){
		console.log( "16Dfdf"+ $("#boardUpdate").serialize());
			$.ajax({
				url 		:	"/updateBoard", //데이터를 주고 받을 파일 주소 입력
				type		:	"GET",  //데이터 전송방식
				dataType 	:	"json", //문자형식으로 받기
				data 		:	$("#boardUpdate").serialize(),  //보내는 데이터

				success : function(result){  //작업이 성공적으로 발생 했을 경우
					console.log("dfdfdf" + result);
					console.log("Title 확인 " + result.title);
					$("#category_cd").val(result.category_cd);
					/* $("#password").val(result.password); */  //pasword필요없음
					$("#title").val(result.title);  //text로 바꾸기
					$("#board_no").text(result.board_no);
					$("#cont").val(result.cont);
					$("#writer_nm").val(result.writer_nm);
					$("#view_cnt").val(result.view_cnt);
					$("#reg_dt").val(result.reg_dt);

				},
				error :function(){
					console.log("update() 오류");
				}
			}); //ajax 끝
		} //update() 끝, 메소드가 끝날 땐 ; 안찍음

		function update_start(){
			let board_no = $("#board_no").val();
			//let writertrim = $("#writer_nm").val();
			// $("#writer_nm").val(writertrim.trim());
			/*if(document.boardUpdate.password.value.length==0){
				alert("비밀번호를 입력하세요");
				document.boardUpdate.password.focus();
				return false;
			*/

			if(document.boardUpdate.writer_nm.value.trim()==""){
				alert("작성자를 입력하세요");
				return false;

			}

			if(board_no== "" ){
				if(document.boardUpdate.password.value.length==0) {

					alert("비밀번호를 입력하세요");
					document.boardUpdate.password.focus();
					return false;
				}
			}

			if(document.boardUpdate.title.value==""){
				alert("제목을 입력하세요");
				document.boardUpdate.title.focus();
				return false;
			}

			if(document.boardUpdate.category_cd.value==""){
				alert("카테고리 선택해주세요");
				document.category_cd_nm.title.focus();
				return false;
			}

			if(document.boardUpdate.cont.value==""){
				alert("내용을 입력해주세요");
				document.category_cd_nm.cont.focus();
				return false;
			}

			var ans =confirm("저장 하시겠습니까?");
			if(ans){
				update();
				return true;
			}/* else{
				location.href="/";
			} */

		}

		//update 실행  ajax
		function update(){
			$.ajax({
				url			:	"/update",
				type		: 	"POST",
				dataType	:	"json",
				data		: $("#boardUpdate").serialize(),
				success :function(result){
					//resultMap
					console.log("result : " , result);

					let board_no = result.board_no;
					let resultMsg = result.resultMsg;  // 컨트롤러에서 정의한 message 받아와서 뿌리기
					let flag = result.flag;  // 수정 또는 등록 플래그

					if(board_no != null){
						alert(resultMsg);
						console.log("보내기 성공 " + board_no);
					} else {
						alert(resultMsg);
					}

					if(flag=="update"){ // 업데이트 하면 디테일로 넘어감
						$("#board_no").val(board_no);  //id값에 board_no을 담아줌
					//	$("serachBpardForm").attr("method", "post");
						$("#boardUpdate").attr("action", "/boardDetail").submit();

						$("#boardUpdate").submit(); //board_no가 담아져 있는 form id값을 submit 함 post로 보내기 위한 것 !
					} else { //등록하면 검색조건 없음 1페이지이동
						location.href="/"; //
					}
				},
					error :function(){
						console.log("update() 오류");
				}
			});
		}//update() 끝


		function cancel(){
			//$("#board_no").val("${board_no}");
			$("#boardUpdate").attr("action", "/boardDetail").submit();

			$("#boardUpdate").submit();
		}

		function writercancel2(){
		//	$("#searchBoardForm").attr("onsubmit", '');
		//	$("#boardView").attr("method", 'get');
			$("#boardUpdate").attr("onsubmit", '');
			//$("#searchBoardForm").attr("method", "post");
			$('#boardUpdate').attr("action", "/").submit();
		}

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

				<!--
						<li class="snb2"><a href="#">파일업로드</a></li>
						<li class="snb3"><a href="#">웹에디터</a></li>
				-->
				<!-- 검색조건 유지  -->
				<!--
				<form id="boardDetail" name="boardDetail" method="post" action="/boardDetail">
					등록 하면 board_no 를담아야하기 떄문에 폼에  한번 더 담아서 submit
					<input type="hidden" name="board_no" id="detail_board_no"/>id값으로 담아서 post로 감
				</form>
 				-->
				<form id="boardUpdate" name="boardUpdate" method="post"  ><!-- action에 담아서 한번에  -->

					<input type="hidden" name="currentPage" id="currentPage" value="${brdto.currentPage}"/>
					<input type="hidden" name="pointCount" id="pointCount"  value="${brdto.pointCount}" />  <!-- form 밖에 있는것을 담아오기 위해 input hidden  을 사용 -->
					<input type="hidden" name="offsetData" id="offsetData" value="${brdto.offsetData}" />
					<input type="hidden" name="keyword" id="keyword" value="${brdto.keyword}">
					<input type="hidden" name="type" id="type" value="${brdto.type}">
					<input type="hidden" name="category" id="category" value="${brdto.category}">
					<input type="hidden" name="arrayboard" id="arrayboard" value="${brdto.arrayboard}">

					<input type="hidden" value="${board_no}" name="board_no" id="board_no">
					<table class="write" >
						<colgroup>
							<col style="width:150px;">
							<col>
							<col style="width:150px;">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th class="fir">작성자 <i class="req">*</i></th>
								<td>
									<%--
									<c:choose>
								 		<c:when test="${empty board_no }"><!-- 만약 board_no 가 없으면 -->
											<input type="text" class="input block" id="writer_nm" name="writer_nm" >
										</c:when>
										<c:otherwise>
											<input type="text" class="input block" id="writer_nm" name="writer_nm" readonly="readonly" >
										</c:otherwise>
									</c:choose>
									--%>
									<input type="text" class="input block" id="writer_nm" name="writer_nm" <c:if test="${not empty board_no }">readonly="readonly"</c:if>>
								</td>
								<!-- 비밀번호는 수정 할 수 없기 떄문에 주석 처리  -->
								<c:if test="${empty board_no }">
									<th class="fir">비밀번호 <i class="req">*</i></th>
									<td ><input type="password" class="input block" id="password" name="password"></td>
								</c:if>
							</tr>
							<tr>
								<th class="fir">카테고리 <i class="req">*</i></th>
								<td colspan="3" >
									<select class="select" style="width:150px;" name="category_cd" id="category_cd"><!-- category_cd_nm 은select(보여주기)이므로  -->
										<option value="">선택</option>
										<c:forEach var="item" items="${category}">
											<option value="${item.comm_cd }" >	${item.comm_cd_nm}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th class="fir">제목 <i class="req">*</i></th>
								<td colspan="3">
									<input type="text" class="input" style="width:100%;" id="title" name="title">
								</td>
							</tr>
							<tr>
								<th class="fir">내용 <i class="req">*</i></th>
								<td colspan="3">
									<textarea style="white-space:pre-wrap; width:100%; height:300px;" id="cont" name="cont"></textarea>
								</td>
							</tr>
							<tr>
								<th class="fir">첨부파일 1 <i class="req">*</i></th>
								<td colspan="3">
									<span><a href="#">상담내역1.xlsx</a> <a href="#" class="ic-del">삭제</a></span>
									<br />
									<input type="file" class="input block mt10">
								</td>
							</tr>
							<tr>
								<th class="fir">첨부파일 2</th>
								<td colspan="3">
									<span><a href="#">상담내역2.xlsx</a> <a href="#" class="ic-del">삭제</a></span>
									<br />
									<input type="file" class="input block mt10">
								</td>
							</tr>
							<tr>
								<th class="fir">첨부파일 3</th>
								<td colspan="3">
									<input type="file" class="input block mt10">
								</td>
							</tr>
						</tbody>
					</table>
				</form>

					<div class="btn-box r">
							<a href="javascript:void(0);" onclick="update_start()" class="btn btn-red">저장</a>
							<a href="javascript:void(0);" class="btn btn-default" <c:if test="${not empty board_no }"> onclick="cancel()"</c:if> onclick="writercancel2()" >
								취소
							</a>
					</div>

		</div><!-- /contents -->

	</div><!-- /container -->



<%@ include file="./include/rightgnb.jsp" %>
<%@ include file="./include/footer.jsp" %>
</div><!-- /wrap -->
</body>
</html>