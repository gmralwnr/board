<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="./include/head.jsp" %>

	<script type="text/javaScript">

		$(document).ready(function(){
			if(${brdto.currentPage} !=0){
				board(${brdto.currentPage}) //페이징은 ajax에 담겨져 있기떄문에 페이지 번호를(currentPage 현재페이지) 담아서 넘겨줘야함
			}else{
				board();
			}

		})


		function board(num){

			let pageSize = $("#boardCount").val();  //보이는 한 페이지에 보이는 게시글 수 //select 로 개수를 지정해서 볼 수 있기 때문에 #boardCount를 넣어줌
			let totalPage =0;  //토탈 페이지 수
			let curPage = (num === undefined ? 1 : num); // 현재 페이지 NUM이 undifined 일때 1이면 true /false num

			let offsetData = (pageSize * (curPage-1));

			$("input[name='currentPage']").val(curPage); // 페이지 바꿔주기(현재페에지_)  //폼안에 받아온걸 담음
			$("input[name='pointCount']").val(pageSize); //(보이는 페이지 수)
			$("input[name='offsetData']").val(offsetData);

			/* alert("formData : " + $("#searchboardform").serialize()); */
			$('#boardtable').empty(); // 하위자식들 삭제 $('#boardtable')
			$('#areaPage').empty();

			$.ajax({
				url:"/boardList",
				type: "POST",/*컨트롤 매핑과 동일 */

				dataType: "json",
				data :	$("#searchBoardForm").serialize(),  //보내는값
				/*
						{
							page : page,
							serachType : searchType,
							keyword :search,
							listSize : listSize
						}
					*/
				success : function(result){

					let boardList = result.boardList;
					let boardListCount = result.count;
					let data = "";
					console.log("리스트 " + boardList);
					if(boardListCount !=0){
						for(var i=0; i<boardList.length; i++){
							data +="<tr>";
							data +="	<td>" + boardList[i]["rnum"] +"</td>";
							data +="	<td>" + boardList[i]["category_cd_nm"] +"</td>";

							//new icon 3일 이후는 안보이게
							data +="	<td class='l'>";

							data += " <a href='javascript:void(0);' onclick='detailForm(" + boardList[i]['board_no'] + ")'>" + boardList[i]["title"];

							if((boardList[i]['reply_count']) != 0){

								data +="<span style='color:red; font-weight:bold;'>" + "[" +  boardList[i]['reply_count'] + "]" +"</span>"
							}

							if((boardList[i]["new_yn"]) ==='Y'){//type 까지 비교 문자와 문자
								data +=	"<img src='resources/images/new.gif' class='new' />";
							}
							data +=	"</a>" +"</td>";

							//file 클립 icon 유뮤
							data +="	<td>";
							if((boardList[i]["file_count"]) != 0){
								data +="		<a href='javascript:void(0);' class='ic-file'>" + boardList[i]["file_count"]+ "</a>" ;
							}
							data +="	</td>";
							data +="	<td>" + boardList[i]["writer_nm"] + "</td>";
							data +="	<td>" + boardList[i]["view_cnt"] + "</td>";
						 	data +="	<td>" + boardList[i]["reg_dt"] + "</td>";

							data+="</tr>";
							}
					}else{
						console.log("게시물 없음 ");
						data +="<tr>";
						data +="	<td colspan='7'>"+ "게시글이 없습니다" + "</td>";
						data +="</tr>";
					}

					$('#boardtable').append(data); //추가

					//페이징
					let totalCount = result.count;
					$("#boardTotalCount").html(totalCount); //html 덮어쓰기

					if(totalCount !=0){
						totalPage = Math.ceil(totalCount / pageSize); //총 페이지개수 = 총 게시글 수를 한페이지에 보이는 게시글 10개 를 나누기(meth.ceil 반올림)
						var jspPage = pageLink(curPage, totalPage, "board" ); //pageLink(현재페이지, 전체페이지, 호출할 함수이름)
						$("#areaPage").append(jspPage); //담아주기

						// ********만약에 검색버튼을 누르지 않고, 상세화면으로 갔다가 취소한 경우 토탈 페이지 보다 크면 토탈페이지랑 값을 같게 한다.
						var nowPage = $("#currentPage").val(); //nowPage : 현재 페이지에서 들어간 버노 //

						if( nowPage > totalPage ) {//total 페이지는 조회된 페이지값을 가지고 있음 , 검색 하고 서브밋을 누르지 않고 들어갔을 경우 토탈 페이지가 계산되서
							board(totalPage); //멘끝페이지로 감
						}

					}
					/*error :function(){
					alert("request error!");
					} */
				}
			});
		}


		function pageLink(curPage, totalPage, board){  //현재페이지 총 페이지 함수이름

			let pageUrl ="";

			let pageLimit = 10;		//한블럭에 나올 개수
			let startPage = parseInt((curPage -1) / pageLimit) * pageLimit + 1; //한블럭에 첫번째 페이지
			let endPage = startPage + pageLimit -1; //한블럭에 마지막 페이지

			if(totalPage < endPage){ //[end페이지]가 [총페이지] 보다 적으면 [end페이지]를 총페이지로 바꿔주기
				endPage = totalPage;
			}

			console.log("~~~startPage : " , startPage , " / endPage : " + endPage);

			var nextPage = endPage+1;//

			//맨 첫 페이지
			console.log(" curPage : " , curPage , " / pageLimit : " , pageLimit)
			if(curPage > 1 && pageLimit <curPage){
				pageUrl +="<a class='direction fir' href='javascript:" + board+ "(1);'>처음</a>"
			}
			//이전페이지
			if(curPage > pageLimit){
				pageUrl +="<a class='direction prev' href=' javascript:" + board + "(" + (startPage == 1 ? 1 :startPage - 1) + ");'>이전</a>";
			}

			//~pageLimit 맞게 페이지수 보여줌
			for(var i=startPage; i<=endPage; i++){

				if(i == curPage){
					pageUrl +="<strong>" + i + "</strong>"
				}else {
					pageUrl +="<a href ='javascript:" + board +"(" + i + ");'> " + i + "</a>";
				}

			}

			//다음 페이지
			if (nextPage <= totalPage) {
				pageUrl += "<a class='direction next' href='javascript:" + board + "(" + (nextPage < totalPage ? nextPage : totalPage) + ");'>다음</a>";
			}
			//맨 마지막 페이지
			if (curPage < totalPage && nextPage < totalPage) {
				pageUrl += "<a class='direction last' href='javascript:" + board + "(" + totalPage + ");'>끝</a>";
			}

			return pageUrl;

		}

		/*검색 type select */
		function go_serach(){
				board();

		}
		/* 엔터키 누르면 이동 */
		function enterkey(){
			if(window.event.keyCode == 13){
				board();
			}
		}

	/* 	$("#arrayboard").on('change', function() {
			alert("dddd")
			board();
		});
 */
	//디테일 가는 방법
	 	function detailForm(board_no){


			/* alert(board_no) */
			$("input[name='board_no']").val(board_no);  //board_no 값을 board_no에 값을 넘겨줌 -> 컨트롤러에 받아서 값을 내보냄 // 폼테그에도 INPUT값을 적어줘야함

			$("#searchBoardForm").attr("onsubmit", '');
			$('#searchBoardForm').attr("action", "/boardDetail").submit();

	 	}

		 /*	function detailForm(board_no){
			 $("input[name='board_no']").val(board_no);  //board_no 값을 board_no에 값을 넘겨줌 -> 컨트롤러에 받아서 값을 내보냄 // 폼테그에도 INPUT값을 적어줘야함

			 $("#searchBoardForm").attr("onsubmit", '');
			 $('#searchBoardForm').attr("action", "/boardDetail").submit();
		 	}
		  */

	 	function boardWrite(){
			$("#searchBoardForm").attr("onsubmit", '');
			$("#searchBoardForm").attr("method", "post");
			$('#searchBoardForm').attr("action", "/boardWriteForm").submit();

	 		//location.href="boardWriteForm"
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
				<!-- ONSUBMIT 전송이 안되도록 검색창에 남아있도록 하기 위해 FALSE -->
			<form name="search" id="searchBoardForm" method="post" onsubmit="return false;" action= "boardDetail"><!--  -->
				<input type="hidden" name="currentPage" id="currentPage" value="${brdto.currentPage}"/>
				<input type="hidden" name="pointCount" id="pointCount" value="${brdto.pointCount}"/>  <!-- form 밖에 있는것을 담아오기 위해 input hidden  을 사용 -->
				<input type="hidden" name="offsetData" id="offsetData"	value="${brdto.offsetData}"  />
				<input type="hidden" name="board_no" id="board_no"/>
				<!-- 추가건  -->
			 	<%--<input type="hidden" name="keyword" id="keyword" value="${brdto.keyword}">
				<input type="hidden" name="type" id="type" value="${brdto.type}">
				<input type="hidden" name="category" id="category" value="${brdto.category}"> --%>
				<div class="hide-dv mt10" id="hideDv">
					<table class="view">
						<colgroup>
							<col style="width:150px;">
							<col>
						</colgroup>

						<tbody>

							<tr>
								<th>카테고리</th>
								<td>
									<!-- <select name="category" class="select" style="width:150px;" >
										<option value="">전체</option>
										<option value="CTG001">공지</option>
										<option value="CTG002">중요</option>
										<option value="CTG003">일반</option>
									</select> -->
									<select name="category" class="select" style="width:150px;" >
										<option value="">전체</option>
										<c:forEach var="item" items="${category}">
											<option value="${item.comm_cd}" <c:if test ="${brdto.category == item.comm_cd}">selected="selected"</c:if>>
												${item.comm_cd_nm}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>

							<tr>
								<th>검색어</th>
								<td>
									<select name="type" class="select" style="width:150px;">
										<option value="">전체</option>
										<option value="2" <c:if test="${brdto.type == 2}">selected="selected"</c:if>>제목</option>
										<option value="3" <c:if test="${brdto.type == 3}">selected="selected"</c:if>>내용</option>
										<option value="4" <c:if test="${brdto.type == 4}">selected="selected"</c:if>>제목+내용</option>
										<option value="5" <c:if test="${brdto.type == 5}">selected="selected"</c:if>>작성자명</option>

									</select>
									<input type="text" class="input" style="width:300px;" id="keyword" name="keyword"  value="${brdto.keyword}" onkeyup="enterkey();">
								</td>
							</tr>

						</tbody>

					</table>
				</div>

				<div class="btn-box btm l">
					<a href="javascript:void(0)" class="btn btn-red fr" name="search" onClick="go_serach()">검색</a>
				</div>

				<div class="tbl-hd noBrd mb0">
					<span class="total" >검색 결과 : <strong id="boardTotalCount" ></strong> 건</span>
					<div class="right">
						<span class="spanTitle">정렬 순서 :</span>
						<select class="select" name="arrayboard" id="arrayboard" onchange="board()" style="width:120px;">
							<option value="newboard"<c:if test="${brdto.arrayboard eq 'newboard'}">selected="selected"</c:if>>최근 작성일</option>
							<option value="viewcount"<c:if test="${brdto.arrayboard eq 'viewcount'}"> selected="selected"</c:if>>조회수</option>
						</select>
					</div>
				</div>
			</form>

			<table class="list default">
			<colgroup>
				<col style="width:60px;">
				<col style="width:80px;">
				<col>
				<col style="width:80px;">
				<col style="width:80px;">
				<col style="width:80px;">
				<col style="width:120px;">
			</colgroup>
			<thead>
			<tr>
				<th>No</th>
				<th>카테고리</th>
				<th>제목</th>
				<th>첨부파일</th>
				<th>작성자</th>
				<th>조회수</th>
				<th>작성일</th>
			</tr>
			</thead>
			<tbody id="boardtable"> <!-- 리스트 받기 -->

			</tbody>
			</table>

			<div class="paginate_complex">
				<div id="areaPage">
					<!-- <a href="#" class="direction fir">처음</a>
					<a href="#" class="direction prev">이전</a>
					<a href="javascript:board('1');">1</a>
					<a href="javascript:board('2');">2</a>
					<a href="javascript:board('3');">3</a>
					<a href="#">4</a>
					<strong>5</strong>
					<a href="#">6</a>
					<a href="#">7</a>
					<a href="#">8</a>
					<a href="#">9</a>
					<a href="#">10</a>
					<a href="#" class="direction next">다음</a>
					<a href="#" class="direction last">끝</a> -->
				</div>
				<div class="right">
					<select class="select" id="boardCount" style="width:120px;"  onchange="board()"> <!-- 실시간 변경 onchange -->
						<option value="10" <c:if test="${brdto.pointCount == 10}">selected="selected"</c:if>>10개씩보기</option>
						<option value="30" <c:if test="${brdto.pointCount == 30}">selected="selected"</c:if>>30개씩보기</option>
						<option value="50" <c:if test="${brdto.pointCount == 50}">selected="selected"</c:if>>50개씩보기</option>
					</select>
				</div>
			</div>

			<div class="btn-box l mt30" style="clear:both;">
				<a href="javascript:void(0);" onClick="boardWrite()" class="btn btn-green fr">등록</a>
			</div>

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