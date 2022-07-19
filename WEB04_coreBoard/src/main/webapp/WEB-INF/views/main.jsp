<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코아메소드-초급자교육</title>

<link href="resources/css/style.css" rel="stylesheet">
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/common.js"></script>
<script src="resources/js/custom.js"></script>

<script type="text/javaScript">

	$(document).ready(function(){
		board();
	})


		function board(num){

			let pageSize = $("#boardCount").val();  //보이는 한 페이지에 보이는 게시글 수 //select 로 개수를 지정해서 볼 수 있기 때문에 #boardCount를 넣어줌
			let totalPage =0;  //토탈 페이지 수
			let curPage = (num === undefined ? 1 : num); // 현재 페이지

			let offsetData = (pageSize * (curPage-1));

			$("input[name='currentPage']").val(curPage); // 페이지 바꿔주기(현재페에지_)
			$("input[name='pointCount']").val(pageSize); //(보이는 페이지 수)
			$("input[name='offsetData']").val(offsetData);

			/* alert("formData : " + $("#searchboardform").serialize()); */
			$('#boardtable').empty(); // 하위자식들 삭제 $('#boardtable')
			$('#areaPage').empty();

			$.ajax({
				url:"/boardList",
				type: "GET",

				dataType: "json",
				data :	$("#searchBoardForm").serialize(),
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

					let data = "";
					for(var i=0; i<boardList.length; i++){

						data +="<tr>";
						data +="	<td>" + boardList[i]["board_no"] +"</td>";
						data +="	<td>" + boardList[i]["category_cd"] +"</td>";
						data +="	<td class='l'>" + " <a href='#''>" + boardList[i]["title"] + "<img src='resources/images/new.gif' class='new' /></a>" +"</td>";
						if($(boardList[i]["file_count"]) !=null){
						data +="	<td>" +"<a href='#' class='ic-file'>" + boardList[i]["file_count"]+ "</a>" +"</td>";
						}
						data +="	<td>" + boardList[i]["writer_nm"] + "</td>";
						data +="	<td>" + boardList[i]["view_cnt"] + "</td>";
					 	data +="	<td>" + boardList[i]["reg_dt"] + "</td>";
						data+="</tr>";

					}
					$('#boardtable').append(data);

					//페이징
					let totalCount = result.count;
					$("#boardTotalCount").html(totalCount); //html 덮어쓰기

					if(totalCount !=0){
						totalPage = Math.ceil(totalCount / pageSize); //총 페이지개수 = 총 게시글 수를 한페이지에 보이는 게시글 10개 를 나누기(meth.ceil 반올림)
						var jspPage = pageLink(curPage, totalPage, "board" ); //pageLink(현재페이지, 전체페이지, 호출할 함수이름)
						$("#areaPage").append(jspPage); //담아주기
					}
			/*	 error :function(){
					alert("request error!");
				} */
				}
			});
		}


		function pageLink(curPage, totalPage, board){

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
					pageUrl +="<a href='#'><strong>" + i + "</strong></a>"
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
</script>

</head>

<body>

<div id="wrap">

	<div id="header">
		<div class="div-utill">
		</div>
		<div class="head-inner">
			<h1 class="logo"><a href="/">초급자교육</a></h1>
			<div id="gnb-wrap">
				<ul id="gnb">
					<li class="gnb1"><a href="/" class="d1">커뮤니티</a></li>
				</ul>
			</div>
		</div>
	</div><!-- /header -->

	<div id="container">

		<div id="lm">
			<h2 class="h2-tit"><strong>커뮤니티</strong></h2>
			<ul id="snb">
				<li class="snb1"><a href="/">통합게시판</a></li>
<!--
				<li class="snb2"><a href="#">파일업로드</a></li>
				<li class="snb3"><a href="#">웹에디터</a></li>
-->
			</ul><!-- /snb -->
		</div><!-- /lm -->

		<div id="contents">

			<div class="location">
				<span class="ic-home">HOME</span><span>커뮤니티</span><em>통합게시판</em>
			</div>

			<div class="tit-area">
				<h3 class="h3-tit">통합게시판</h3>
			</div>
				<!-- ONSUBMIT 전송이 안되도록 검색창에 남아있도록 하기 위해 FALSE -->
			<form name="search" id="searchBoardForm" onsubmit="return false;">
				<input type="hidden" name="currentPage" id="currentPage" value=""/>
				<input type="hidden" name="pointCount" id="pointCount" />
				<input type="hidden" name="offsetData" id="offsetData" />

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
							<select name="category" class="select" style="width:150px;" >
								<option value="">전체</option>
								<option value="CTG001">공지</option>
								<option value="CTG002">중요</option>
								<option value="CTG003">일반</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>검색어</th>
						<td>
							<select name="type" class="select" style="width:150px;">
								<option value="1">전체</option>
								<option value="2" >제목</option>
								<option value="3" >내용</option>
								<option value="4" >제목+내용</option>
								<option value="5" >작성자명</option>

							</select>
							<input type="text" class="input" style="width:300px;" id="keyword" name="keyword"  value="${keyword}" onkeyup="enterkey();">
						</td>
					</tr>

					</tbody>

					</table>
				</div>

				<div class="btn-box btm l">
					<a href="#" class="btn btn-red fr" name="search" onClick="go_serach()">검색</a>
				</div>

				<div class="tbl-hd noBrd mb0">
					<span class="total" >검색 결과 : <strong id="boardTotalCount" ></strong> 건</span>
					<div class="right">
						<span class="spanTitle">정렬 순서 :</span>
						<select class="select" name="arrayboard" id="arrayboard" onchange="board()" style="width:120px;">
							<option value="newboard">최근 작성일</option>
							<option value="viewcount">조회수</option>
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
			<tbody id="boardtable">

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
					<select class="select" id="boardCount" style="width:120px;">
						<option value="10">10개씩보기</option>
						<option value="30">30개씩보기</option>
					</select>
				</div>
			</div>

		</div><!-- /contents -->

	</div><!-- /container -->

	<div class="float-right">
		<h2>QUICK MENU</h2>
		<ul>
			<li class="item1"><a href="#">통합게시판</a></li>
<!--
			<li class="item3"><a href="#">파일업로드</a></li>
			<li class="item5"><a href="#">웹에디터</a></li>
-->
		</ul>
	</div><!-- /float-right -->

	<div id="footer">
		<div class="footer-wrap">
			<ul class="footer-link">
				<li><a href="#">개인정보처리방침</a></li>
				<li><a href="#">이메일 무단수집거부</a></li>
			</ul>
			<address>
				<b>(주)코아메소드</b> 서울시 영등포구 양평로 149 우림라이온스밸리 A동 704 ~ 706호 (선유도역 8번 출구)<br>
				<b>TEL</b> 02-3141-9784
			</address>
			<p class="copy">Copyright(C) 2022 by coremethod. All Rights Reserved.</p>
			<div class="famliy-link">
				<button>관련 사이트 안내</button>
				<ul>
					<li><a href="#">네이버</a></li>
					<li><a href="#">다음</a></li>
					<li><a href="#">구글</a></li>
					<li><a href="#">유투브</a></li>
					<li><a href="#">구글</a></li>
				</ul>
			</div>
		</div>
	</div><!-- /footer -->

</div><!-- /wrap -->

<script>
	gnb('1','1');
</script>


</body>
</html>