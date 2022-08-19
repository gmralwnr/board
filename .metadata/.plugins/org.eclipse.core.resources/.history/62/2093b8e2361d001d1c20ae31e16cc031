<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="./include/head.jsp" %>

	<script type="text/javascript">
		var oEditors = [];  //웹에디터 배열
		console.log("실행시작 : " + "시자자자자ㅏㄱㅇ");

		//html 다 실행되고 난 후 document.ready 가 실행
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
			gnb('1','1');

			if(document.boardUpdate.board_no.value!=""){//$("#board_no").val() //document 안에 있을 떈
				getBoard();
			}

			// 메뉴 하이라이팅

			//** 스마트 웹에디터 js ->  ready 뒤에 넣어 놓기
			nhn.husky.EZCreator.createInIFrame({
				oAppRef: oEditors,
				elPlaceHolder: "txtContent",  //textarea ID 입력
				sSkinURI: "/libs/smarteditor/SmartEditor2Skin.html",  //martEditor2Skin.html 경로 입력
				fCreator: "createSEditor2",
				htParams : {
					// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseToolbar : true,
					// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : false,
					// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : true
				}
			}); //스마트 웹에디터
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
				//	$("#cont").val(result.cont);
					$("#txtContent").val(result.cont);
					$("#writer_nm").val(result.writer_nm);
					$("#view_cnt").val(result.view_cnt);
					$("#reg_dt").val(result.reg_dt);

					//파일 List 시작
					let fileList = result.fileList;

					if(fileList != null){
						for(var i=0; i<fileList.length; i++){
							let index = i+1;
							//alert("파일 명  : " +  fileList[i].origin_file_nm);
							//alert("파일 명  : " +  fileList[i].file_no);
							$("#origin_file_nm"+ index).text(fileList[i].origin_file_nm);  //index를 i+1로 변수를 담아서 줌 그리고 파일네임 하나당 이름 부여
								//id=origin_file_nm1 or	origin_file_nm2
								//아래 소스를 반복문으로 i를 돌려서 출력 위처럼 출력
								//$("#origin_file_nm2").text(fileList[1].origin_file_nm);
								//$("#origin_file_nm3").text(fileList[2].origin_file_nm);
								//$("#file_no1").text(fileList[0].file_no);
								//$("#file_no2").text(fileList[1].file_no);
								//$("#file_no3").text(fileList[2].file_no);
							$("#ref_pk").text(fileList[0].ref_pk);
							$("#imgdata" + index ).append(
								"<img src=  ../upload/" + fileList[i]["save_file_nm"] + " width='200px' height='100px' " +  " onerror=" + "this.style.display='none'" + " alt=''  >");
							if(fileList[i].file_no !=null){ //파일 넘버가 널이 아닐때 파일 돌리기
									//ajax자체를 온클릭 줄 수 있음. 삭제힐때 따로 a태그안에 온클릭을 안줘도 됨 ***id값은 줘야함
									//fileDelete(file_no)로 넘어갈 수 있음 ex)fileDelete(54)

									$("#file_no" + index).attr("onclick", "fileDelete(" + fileList[i].file_no + ")");  //ajax자체를 온클릭 줄 수 있음
									$("#origin_file_nm" + index).attr("onclick", "fileDownload(" + fileList[i].file_no + ")");  //ajax자체를 온클릭 줄 수 있음

									//$("boardUpdate").attr("method", "post");   //attr방식

							}
						}

					}
				},
				error :function(){
					console.log("update() 오류");
				}
			}); //ajax 끝
		} //update() 끝, 메소드가 끝날 땐 ; 안찍음

		function update_start(){
			let board_no = $("#board_no").val(); //board_no를 변수에 담아서 사용하기

			if(document.boardUpdate.writer_nm.value.trim()==""){  //trim 트림 사용
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
				document.boardUpdate.title.focus();
				return false;
			}

			/*
			//if(document.boardUpdate.cont.value==""){
			if(document.boardUpdate.cont.value==""){
				alert("내용을 입력해주세요");
				document.category_cd_nm.cont.focus();
				return false;
			}
			*/
			oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);
			//스마트 에디터 값을 텍스트컨텐츠로 전달
			//alert(document.getElementById("txtContent").value); //문자열에서 어떻게 입력이 되는지 출력해봄

			//에디더 사용 하여 글씨 빈칸 찾아내기
			var Text = document.getElementById("txtContent").value;  //textContent 를 Text로 담음
			Text = Text.replace(/$nbsp;/gi,"");
			Text = Text.replace(/<br>/gi,"");
			Text = Text.replace(/ /gi,"");

			if(Text == "<p><\/p>" || Text ==""){
				alert("내용을 입력하세요");
				return false;
			}


			//등록시 파일 없을 때 Validation
			//let uploadFile = $("#uploadFile").val();  -->이 방식이 안되서 아래 방식으로

			//1. id가 uploadFile로 시작하는 객체들 모임 -> [file객체들]
			let uploadFiles = $("[id^='uploadFile']");

			//2. [file객체들]을 each문을 돌려서 하나씩 확인해보기
			let checkHaveFile = false;
			uploadFiles.each(function(index, element){

				//$(this) 은 파일하나
				console.log($(this).attr("id") , " / " , $(this).val());

				//2-1. 만약에 파일값이 있으면 file이 있다는걸로 간주
				if( $(this).val() != null && $(this).val() != ""){
					console.log($(this).attr("id") , " ~~~~~~~~ ");
					checkHaveFile = true; //2-2 . 파일값이 있다는걸 표시 (true로 값 변경)

					return false;
				}

			})
			//3. checkHaveFile이걸 확인 (false = 파일이 없음 / true = 파일이 있음)
		/**/
			if(board_no == ""){
				if(checkHaveFile === false){
					alert("첨부파일은 필수! 입니다 ~~~~~~~~~~~~~~");
					$("#uploadFile1").focus();
					return false;
				}
			}



			if(board_no ==""){
				let uploadCheck = $("#uploadFile1").val();
				if(uploadCheck==null || uploadCheck == 0 ){
					alert("첨부파일 1에 업로드 하시기바랍니다");
					return false;
				}
			}
			let uploadFile3	= 	$("uploadFile3").val();
			let uploadFile2	= 	$("uploadFile2").val();
			let uploadFile1	= 	$("uploadFile1").val();

			let origin_file_nm3	= 	$("origin_file_nm3").val();
			let origin_file_nm2 =$("#origin_file_nm2").val();
			let origin_file_nm1 =$("#origin_file_nm1").val();


			var ans =confirm("저장 하시겠습니까?");
			if(ans){
				update();
				return true;
			}
		}

		/* //업데이트 시 첨부파일 없을 떄 Validation
		if(board_no >= 1){
			//let file_check = $("#file_check").val();  //file count로 0개라면
			let uploadCheck = $("#uploadFile1").val();
			if(uploadCheck == 0 ){
				alert("첨부파일1은 필수입니다 다시 올리시기 바랍니다 !");
			//alert(file_check);
			$("#uploadFile").focus();
				return false;
			}
		}*/


		//파일 한개씩 삭제
		function before_Delete(num){
			let origin_file_nm1 =$("#origin_file_nm1").val();
			let origin_file_nm2 =$("#origin_file_nm2").val();
			let origin_file_nm3 =$("#origin_file_nm3").val();
			if(num =1){
				//if(origin_file_nm1 !=null){
					alert("첨부파일 1 : 기존 파일 삭제 후 파일 업로드 하시기바랍니다");
					//$("#origin_file_nm1").focus();
					return false;
				//}
			}
		}

		//update 실행  ajax
		function update(){

			var form = $('#boardUpdate')[0];  //serize
			var data = new FormData(form); //data에 form 을 담아서 객체 자체를 넘겨줌

			//alert(data);

			$.ajax({
				url			:	"/update",
				enctype		: "multipart/form-data",	//파일 업로드 시 필수
				type		: 	"POST",
				dataType	:	"json",
				processData	:	 false,		//파일 업로드 시 필수
				contentType	:	false,		//파일 업로드 시 필수
				data		:	data,		//form 변수를 만들어서 boardUpade form 을 담고
								//$("#boardUpdate").serialize(),
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
					//	$("boardUpdate").attr("method", "post");
						$("#boardUpdate").attr("action", "/boardDetail").submit();

						$("#boardUpdate").submit(); //board_no가 담아져 있는 form id값을 submit 함 post로 보내기 위한 것 !
					} else { //등록하면 검색조건 없음 1페이지이동
						location.href="/"; //
					}
				},
					error :function(){
						console.log("update() 오류");
						alert(" 수정/등록 불가 관리자에게 문의하세요");
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
		//	$("#searchBoardForm").attr("method", "post");

			$("#boardUpdate").attr("onsubmit", '');
			$("#boardUpdate").attr("method", "post");
			$('#boardUpdate').attr("action", "/").submit();
		}


		//파일 삭제
		function fileDelete(file_no){
			let ans = confirm("삭제하시겠습니까?")
				if(ans){
					BoardFileDelete(file_no);

				}
		}

		//파일 삭제 Ajax
		function BoardFileDelete(file_no){ //매개변수 받은걸  담아와서 또 실행
				//alert($("#file_no1").val());
			$.ajax({
				url			:	"/boardFileDelete",
				//enctype	: "multipart/form-data",	//파일 업로드 시 만 삭제는 안써도됨
				type		: 	"POST",
				dataType	:	"json",
				data		:	{ file_no : file_no,
								/* $("#file_no2").val();  데이터 뿌려줄 때 for문으로 file_no를 받아서 뿌려주기떄문에 굳이 나눌 필요가 없음
									$("#file_no3").val(); */
								board_no :	$("#board_no").val()
				},

				success		:function(result){
					if(result ==1){
					alert("삭제되었습니다");
						location.reload(); //화면 새로고침
					}else{
						alert("삭제안되었습니다");
					}
				},

				error: function (xhr, ajaxOptions, thrownError) {
					alert(xhr.responseJSON.message);
					alert(" 수정/등록 불가 관리자에게 문의하세요");
				}

			});

		}

		function fileDownload(file_no) {
			alert("다운로드 할거지롱");
			alert(file_no);
			location.href ="filedownload?file_no=" + file_no;
		}



		//네이버 에디터 !!!!!!!!!!!!!!!!!


			function save(){
				oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);
			    		//스마트 에디터 값을 텍스트컨텐츠로 전달
				var content = document.getElementById("smartEditor").value;
				alert(document.getElementById("txtContent").value);
			    		// 값을 불러올 땐 document.get으로 받아오기
			    update_start();
				return;
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
				<!--
				<form id="boardDetail" name="boardDetail" method="post" action="/boardDetail">
					등록 하면 board_no 를담아야하기 떄문에 폼에  한번 더 담아서 submit
					<input type="hidden" name="board_no" id="detail_board_no"/>id값으로 담아서 post로 감
				</form>
 				-->

				<form id="boardUpdate" name="boardUpdate" method="post"  enctype="multipart/form-data"><!-- action에 담아서 한번에  -->

					<input type="hidden" name="currentPage" id="currentPage" value="${brdto.currentPage}"/>
					<input type="hidden" name="pointCount" id="pointCount"  value="${brdto.pointCount}" />  <!-- form 밖에 있는것을 담아오기 위해 input hidden  을 사용 -->
					<input type="hidden" name="offsetData" id="offsetData" value="${brdto.offsetData}" />
					<input type="hidden" name="keyword" id="keyword" value="${brdto.keyword}">
					<input type="hidden" name="type" id="type" value="${brdto.type}">
					<input type="hidden" name="category" id="category" value="${brdto.category}">
					<input type="hidden" name="arrayboard" id="arrayboard" value="${brdto.arrayboard}">
					<input type="hidden" value="${board_no}" name="board_no" id="board_no">
					<%-- <input type="hidden"  value="${count}" id="file_check" name="file_check"> --%>
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
								<!-- 	<textarea style="white-space:pre-wrap; width:100%; height:300px;" id="cont" name="cont"></textarea> -->
								<textarea id="txtContent" rows="10" cols="100" style="width: 100%;" name="cont"></textarea>
<!--							javascript는 무조건 head에 올리기 ready 시작 후
								<script id="smartEditor" type="text/javascript">
										var oEditors = [];
										nhn.husky.EZCreator.createInIFrame({
											oAppRef: oEditors,
											elPlaceHolder: "txtContent",  //textarea ID 입력
											sSkinURI: "/libs/smarteditor/SmartEditor2Skin.html",  //martEditor2Skin.html 경로 입력
											fCreator: "createSEditor2",
											htParams : {
											// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
											bUseToolbar : true,
											// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
											bUseVerticalResizer : false,
											// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
											bUseModeChanger : false
										}
									});
								</script> -->
								</td>
							</tr>
							<!----> <tr>
								<th class="fir">첨부파일 1 <i class="req">*</i></th>
								<td colspan="3" >
								<div id="imgdata1"></div>
									<span><a href="javascript:void(0);"   id="origin_file_nm1" ></a> <c:if test="${count>=1}"><a href="javascript:void(0);" id="file_no1" class="ic-del">삭제</a></c:if></span>
									<br />
									<%-- <c:choose>
										<c:when test="${count>=1}">
											<input type="file" name="uploadFile" id="uploadFile1" class="input block mt10" disabled ="disabled"> **기존파일 삭제 후 업로드하시기바랍니다
										</c:when>
										<c:otherwise>

										</c:otherwise>
									</c:choose> --%>
								<input type="file" name="uploadFile" id="uploadFile1" class="input block mt10">
								</td>
								<!--	<input type="file" name="uploadFile" id="uploadFile" <c:if test="${count>=1}">  /*disabled ="disabled"*/ readonly="readonly" onclick = "before_Delete(1)"</c:if>class="input block mt10">
-->
							</tr>
							<tr>
								<th class="fir">첨부파일 2</th>
								<td colspan="3" >
								<div id="imgdata2"></div>
									<span><a href="javascript:void(0);" id="origin_file_nm2"></a> <c:if test="${count>=2}"><a href="javascript:void(0);"   id="file_no2" class="ic-del">삭제</a></c:if></span>
									<br />
									<!-- <input type="file" name="uploadFile" id="uploadFile"class="input block mt10"> -->
									<%-- <c:choose>
										<c:when test="${count>=2}">
											<input type="file" name="uploadFile" id="uploadFile2" class="input block mt10" disabled ="disabled"> **기존파일 삭제 후 업로드하시기바랍니다
										</c:when>
										<c:otherwise>

										</c:otherwise>
									</c:choose>--%>
									<input type="file" name="uploadFile" id="uploadFile2" class="input block mt10">
								</td>
							</tr>
							<tr>
								<th class="fir">첨부파일 3</th>
								<td colspan="3">
								<div id="imgdata3"></div>
									<span><a href="javascript:void(0);" id="origin_file_nm3"></a> <c:if test="${3 eq count}"><a href="javascript:void(0);" id="file_no3"class="ic-del">삭제</a></c:if></span>
									<!-- <input type="file" name="uploadFile" id= "uploadFile" class="input block mt10"> -->
									<%--<c:choose>
										 <c:when test="${count==3}">
											<input type="file" name="uploadFile" id="uploadFile3" class="input block mt10" disabled ="disabled"> **기존파일 삭제 후 업로드하시기바랍니다
										</c:when>
										<c:otherwise> </c:otherwise> </c:choose> --%>
											<input type="file" name="uploadFile" id="uploadFile3" class="input block mt10">


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
					<!--  스마트 에디터 사용
					<div id="se2_sample" style="margin:10px 0;">
						<input type="button" onclick="save();" value="본문 내용 가져오기">
					</div> -->

		</div><!-- /contents -->

	</div><!-- /container -->



<%@ include file="./include/rightgnb.jsp" %>
<%@ include file="./include/footer.jsp" %>
</div><!-- /wrap -->
</body>
</html>