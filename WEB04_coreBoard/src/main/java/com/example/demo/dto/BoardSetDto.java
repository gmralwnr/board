package com.example.demo.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardSetDto {
//insert update set 으로 보내질 때 사용

	/** 게시물 번호 */
	private Integer board_no;
	/** 카테고리 코드*/
	private String category_cd;
	/** 제목 */
	private String title;
	/** 내용 */
	private String cont;
	/** 작성자 */
	private String writer_nm;
	/** 패스워드 */
	private String password;
	/** 조회수 */
	private int view_cnt; //조회수
	/** 작성일 */
	private Date reg_dt; //작성일
	/** 수정일 */
	private Date mod_dt;

	private String category_cd_nm;

	/**성공 유무 확인 메세지 */
	private String resultMsg;
	/** 등록수정구분 */
	private String flag;

}
