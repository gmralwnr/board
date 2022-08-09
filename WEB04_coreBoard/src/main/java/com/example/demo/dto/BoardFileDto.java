package com.example.demo.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardFileDto {

	/** 원본파일 명 */
	private String origin_file_nm;

	/** 파일넘버 */
	private Integer file_no;

	/** 저장파일네임 */
	private  String  save_file_nm;

	/** 저장경로 */
	private String save_path;

	/** 확장자*/
	private String ext;

	/** 사이즈*/
	private int size;

	/** 참조테이블*/
	private String ref_tbl;

	/** 참조pk*/
	private String  ref_pk;

	/** 구분키*/
	private String ref_key;

	/** 다운로드 수 */
	private int download_cnt;

	/** 순서 0 ,1,2*/
	private int ord;

	/** 등록일시*/
	private Timestamp reg_dt;

	/**파일 카운트 */
	//private int filecount;


}
