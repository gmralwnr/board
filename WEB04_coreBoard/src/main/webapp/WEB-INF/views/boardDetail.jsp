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
				replyList();
				//ajax 파일 리스트 조회 추가
	 	gnb('1','1');
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
					//console.log("값 확인" + result);
					//console.log("title 값 확인"+ result.title);
					$("#title").text(result.title);  //text로 바꾸기
					$("#board_no").text(result.board_no);
					$("#category_cd_nm").text(result.category_cd_nm);
	/*text와  html의 차이
	let lText = "<font color='red'>안녕하셍</font>";
	$("#category_cd_nm").text(lText);
	$("#category_cd_nm").html(lText);
	*/
					$("#cont").append(result.cont);
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

					let fileList = result.fileList;
					let filecount = 1;
					let filestar = "*";
					if(fileList != null){
						//let ref_pk = fileList.ref_pk;
						//alert(ref_pk);
						let data="";
						for(var i=0; i<fileList.length; i++){
							data +="<tr >"
							data +="	<th class='fir'>"
							data +="	첨부파일 ";
							if(filecount ==1){
								data += 	filecount++;
								data +="<i class='req'>" + filestar + "</i>";
							}
							else{
							data += filecount++;
							}
							data +="</th>";
							data +="	<td colspan='3' id='file_area'>";
							data +="		<span >";
							data +="	<img src=  ../upload/" + fileList[i]["save_file_nm"] + " width='200px' height='100px' "
							data +="	onerror=" + "this.style.display='none'" + " alt=''  >";
							data +="			<a href ='javascript:void(0);' onclick='fileDownload (" + fileList[i]['file_no'] + ")'>" ;
							data += 				fileList[i]["origin_file_nm"] ;
							data +="			</a>";
							data +="</br>"
							data +="		</span>";


							data +="	</td>";
							data +="</tr >"

						}

							//alert("파일 명  : " +  fileList[i].origin_file_nm);
							//alert("파일 명  : " +  fileList[i].file_no);

							/* $("#origin_file_nm1").text(fileList[0].origin_file_nm);
							$("#origin_file_nm2").text(fileList[1].origin_file_nm);
							$("#origin_file_nm3").text(fileList[2].origin_file_nm);
							$("#file_no1").text(fileList[0].file_no);
							$("#file_no2").text(fileList[1].file_no);
							$("#file_no3").text(fileList[2].file_no);
							$("#ref_pk").text(fileList[0].ref_pk); */

						$('#filecheck').append(data);


					}

				},
				error :function(){
					console.log("detail() 오류");
					alert("상세보기  오류 관리자에게 문의하시기바랍니다");
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
		 		/* $("#searchBoardForm").attr("onsubmit", '');
				$("#searchBoardForm").attr("method", "post");
//				$("#searchBoardForm #_method").val("method", "delete");
		 		$("#searchBoardForm").attr("action", "/boardDeleteForm");
				$('#searchBoardForm').submit();
	 */
				$.ajax({
					url			: "/boardDeleteForm",
					type		: "POST",
					dataType	: "json",
					data		: $("#searchBoardForm").serialize(),

					success		: function(result){

						/**/
						if(result >0){
							alert("삭제되었습니다");
							location.href ="/" //화면 새로고침
							}else{
								alert("삭제안되었습니다");
						}
					} ,
					error : function (){
						alert("에러 발생 관리자에게 문의 하시기바랍니다");
						}

				});
		 	}

			//목록으로 넘어가는 함수
		 	function boardList(){
		 		//$("input[name='board_no']").val(board_no);  //board_no 값을 board_no에 값을 넘겨줌 -> 컨트롤러에 받아서 값을 내보냄 // 폼테그에도 INPUT값을 적어줘야함
				$("#searchBoardForm").attr("onsubmit", '');
				//$("#boardView").attr("method", 'post');
				$("#searchBoardForm").attr("method", 'post');
				$('#searchBoardForm').attr("action", "/").submit();

		 	}

			function fileDownload(file_no){
				alert("다운로드 할거지롱");
				alert(file_no);
				location.href ="filedownload?file_no=" + file_no;
			}




		//////댓글 !!
		function replyList(num, reply_no){
			board_no = $("#board_no").val();


			$.ajax({
				url		: "/replylist",
				type	: "GET",
				dataType: "json",
				data 	:{ board_no : $("#board_no").val()},
				success	: function(result){
					//댓글 추가
					let replyList = result.replyList;
					let reply_count = result.reply_count;
					let data ="";
					if(replyList !=null){
						$("#reply_count").append(reply_count);


						for(var i=0; i<replyList.length; i++){
						///$('#replyList').empty(data);
							let reg_dt_temp = replyList[i]["indate"];
							let reg_dt_real  = reg_dt_temp.substring(0,4); // yyyy
							reg_dt_real += "-";
							reg_dt_real += reg_dt_temp.substring(4,6);
							reg_dt_real += "-";
							reg_dt_real += reg_dt_temp.substring(6,8);
							reg_dt_real += " ";
							reg_dt_real += reg_dt_temp.substring(8,10);
							reg_dt_real += ":";
							reg_dt_real += reg_dt_temp.substring(10,12);
							reg_dt_real += ":";
							reg_dt_real += reg_dt_temp.substring(12,14);



							data +="<tr>"
							data +="	<td class='fir'>" + replyList[i]["rnum"];
							data +="	<td>" + replyList[i]["reply_nm"];
							data += "</td>";
							data +="	<td  colspan='3'>"
							if(reply_no == replyList[i]["reply_no"]){
								if (num == 1) {
									data +="<textarea rows='3' class='input' name='content' id='content' style='width:50%; resize: none;'>";
									data += replyList[i]["content"];
									data += "</textarea>";
									num=0;
								}
							} else {
								data += replyList[i]["content"] ;
							}
							data += "</td>";
							data +="<td>" + reg_dt_real;
							data +="</td>"
							if (num == 0) {
								if(reply_no == replyList[i]["reply_no"]){
											data +="	<td>" + "<input type='button' onclick=" + "replyUpdate" + "('" + replyList[i]["reply_no"] + "') "   + "style='cursor:pointer;'" + " value= '변경' >";
											data +="</td>"
									}
							}else {
								data +="<td>" + "<input type='button' class='btn btn-green' onclick=" + "openPopup" + "('/replyPw','" + replyList[i]["reply_no"] + "'" +  ",'replyUpdate') "   + "style='cursor:pointer;'" + " value= '수정' >";
								data += "&nbsp;"
								data +="<input type='button' class='btn btn-red' + onclick=" +  "openPopup" + "('/replyPw','" + replyList[i]["reply_no"] + "'" +  ",'replyDelete') " + " value= '삭제' >"
								data += "</td>";
								data +="</tr>"
							}
						}
					$('#replyList').html(data);
					//$('#replyList').append(data);
					}
				}
			});

		}

		function openPopup(url, reply_no, name){
			//var url ="replyPw";
			alert(  reply_no + name);
			//벨류, input 값에 hideen 으로 줘야함  ****
			$("#reply_no").attr(reply_no);
			$("#reply_no").val(reply_no);
			var opt ="toolbar=no, memubar=no,status=no,  scrollbars=no, resizable=no, width=700, height=400, top=50, left=300" ;
			window.open(url,name ,opt);
		}

		function replyUpdateform(num, reply_no){ //num은 매개변수 준거임
			//alert(num);
			replyList(num, reply_no);

		}
		function replyDeleteform(reply_no){ //num은 매개변수 준거임
			console.log( reply_no);
			$.ajax({
				url			: "/replyDelete",
				type		: "POST",
				dataType	: "json",
				data		: { reply_no : $("#reply_no").val() },
				success		: function(result){
					if(result ==1) {
						alert("삭제 되었습니다");
						location.reload();
					}else {
						alert("삭제 안되었습니다");
					}
				}
			});


		}
	//	var board_no =document.getElementById("board_no").value();
		function reply(board_no){
			alert (board_no);
 			let content = $("#replycontent").val();
 			let reply_nm = $("#reply_nm").val();
 			let reply_pw = $("#reply_pw").val();
 			//alert(content);
 			//console.log(content);
			if(replycontent==null || replycontent==""){
				alert("내용을 입력하세요");
				return false;
			}else if(reply_nm ==null || reply_nm==""){
				alert("닉네임을 입력하세요");
				return false;
			}else if(reply_pw ==null || reply_pw==""){
				alert("비밀번호를 입력하세요");
				return false;
			}else{
				replyInsert(board_no);
			}
		}

		function replyInsert(board_no){
			alert($("#replycontent").val())
			$.ajax({
				url			:	"/insertReply",
				type		:	"POST",
				dataType	:	"json",
				data		:{
					replycontent : $("#replycontent").val(),
	 						reply_nm : $("#reply_nm").val(),
	 						reply_pw : $("#reply_pw").val(),
	 						board_no

				},
				success : function(result){
					console.log("resul", result);
					if(result>0){
						alert("답글 성공");
						location.reload();
						//location.reload();
					}

				},
				error : function (){
					alert("e댓글 등록 불가 관리자에게 문의")
				}
			});
		}


		function replyUpdate(reply_no){
			alert($("#content").val());
			$.ajax({
				url			: "/replyUpdate",
				type		: "GET",
				dataType	: "json",
				data		:  $("#replyUpdate").serialize() ,
				success 	: function(result){
						if(result>0){
							alert("수정 성공" );
							location.reload();

						}else {
							alert("수정 실패");
						}
				}
			});

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
				<td colspan="3" id="cont" style="white-space:nomal; ">
				</td>
			</tr>
			<tbody id="filecheck">
		<!--	<tr >
				 <th class="fir">첨부파일</th>
				<td colspan="3" id="file_area">

					<span>fresult</span>
					<br />
					<span><a href="javascript:void(0);" ></a></span>


				</td>
			</tr> -->
			</tbody>
			</tbody>
			</table>
			<div class="btn-box r">
				<!-- <a href="javascript:void(0);" class="btn btn-green" onclick="boardwriter()">수정</a> -->
				<a href="javascript:void(0);" class="btn btn-green" onclick="openWin('/boardPassword', 'update')"  >수정</a>
				<a href="javascript:void(0);" class="btn btn-red"  onclick="openWin('/boardPassword', 'delete')">삭제</a>
				<a href="javascript:void(0);" class="btn btn-default"  onclick="boardList()">목록</a>
			</div>
<!-- </form> -->

<!-- 댓글  -->
<span class="total" >댓글 수 : <strong id="reply_count"  style="color:red;"></strong> 건</span>
	<form id="replyUpdate">
		<input type="hidden" name="reply_no" id="reply_no" value=""/>
			<br><br>
			<table class="write">
				<colgroup>
					<col style="width:70px;">
					<col style="width:100px;">
					<col style="width:500px;">
					<col style="width:100px;">
					<col style="width:10px;">
				</colgroup>
			<tr>
			<th colspan="2" class="fir" style="color:red;">답글</th>


				<td colspan="3">

					<textarea rows="5" class="input" name="replycontent" id="replycontent"style="width:150%; resize: none;"></textarea>
					<input type="text"  id="reply_nm" name="reply_nm" style="width:25%;" placeholder="닉네임">
					<input type="password"  id="reply_pw" name="reply_pw" style="width:25%;" placeholder="비밀번호">
					<a href="javascript:void(0);" class="btn btn-red" style="width:15%; " onclick="reply(board_no)">확인</a>
					</td>
			</tr>

			<tr><th class="fir" > 순번</th><th > 닉네임</th><th  colspan="3">내용</th><th>날짜</th><th>삭제/수정</th>

			<tbody id="replyList">
			</tbody>


			</table>
	</form>
		</div><!-- /contents -->

	</div><!-- /container -->



<%@ include file="./include/rightgnb.jsp" %>
<%@ include file="./include/footer.jsp" %>
</div><!-- /wrap -->

</body>
</html>
