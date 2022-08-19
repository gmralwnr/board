package com.example.demo.dto;

import java.sql.Date;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BoardDto {
	/** 게시물 번호 */
	private int board_no;
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
	/** 파일 */
	private String ref_pk;
	private int file_count;
	private String new_yn;
	private String category_cd_nm;
	private int rnum;
	private int reply_count;



}
