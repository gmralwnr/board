<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/head.jsp" %>
<body>
<%@ include file="./include/header.jsp" %>

	<div id="container">
<%@ include file="./include/leftgnb.jsp" %>

		<!--
				<li class="snb2"><a href="#">파일업로드</a></li>
				<li class="snb3"><a href="#">웹에디터</a></li>
-->

			<table class="write">
			<colgroup>
				<col style="width:150px;">
				<col>
				<col style="width:150px;">
				<col>
			</colgroup>
			<tbody>
			<tr>
				<th class="fir">작성자 <i class="req">*</i></th>
				<td><input type="text" class="input block"></td>
				<th class="fir">비밀번호 <i class="req">*</i></th>
				<td><input type="password" class="input block"></td>
			</tr>
			<tr>
				<th class="fir">카테고리 <i class="req">*</i></th>
				<td colspan="3">
					<select class="select" style="width:150px;">
						<option>전체</option>
						<option>-</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="fir">제목 <i class="req">*</i></th>
				<td colspan="3">
					<input type="text" class="input" style="width:100%;">
				</td>
			</tr>
			<tr>
				<th class="fir">내용 <i class="req">*</i></th>
				<td colspan="3">
					<textarea style="width:100%; height:300px;">
					</textarea>
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

			<div class="btn-box r">
				<a href="#" class="btn btn-red">저장</a>
				<a href="#" class="btn btn-default">취소</a>
			</div>

		</div><!-- /contents -->

	</div><!-- /container -->



<%@ include file="./include/rightgnb.jsp" %>
<%@ include file="./include/footer.jsp" %>

<script>
	gnb('1','1');
</script>
</div><!-- /wrap -->
</body>
</html>