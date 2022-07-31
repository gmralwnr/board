package com.example.demo.dto;

import lombok.Data;

@Data
public class BoardListReqDto {
	//jsp 에서 가져오는 데이터 , Request 요청
	//검색
	/**  검색조건*/
	private String type;
	/**  검색어*/
	private String keyword;
	/** 카테고리 */
	private String category;

	/** 정렬 */
	private String arrayboard;
	/** 한페이지에 보일 개수 */
	private int pointCount; //한 페이지에 보일 개수(총 10 or 30)
	/** 현재페이지 */
	private int currentPage;  //현재페이지

	/** 한페이지에 보일 게시판 글 수 */
	private int offsetData;

//	public void setOffsetData() { //offset 계산
//		offsetData = pointCount * (currentPage-1);
//	   }
}
