package com.example.demo.dto;

import java.sql.Date;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BoardDto {
	private int board_no;
	private String category_cd;
	private String title;
	private String cont;
	private String writer_nm;
	private String password;
	private int view_cnt; //조회수
	private Date reg_dt; //작성일
	private Date mod_dt;
	private String ref_pk;
	private int file_count;

}
