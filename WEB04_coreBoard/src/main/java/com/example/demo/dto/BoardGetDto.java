package com.example.demo.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;


@Data
public class BoardGetDto {
//getSelect (하나 씩 )조회 가져올 때

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
	private Integer view_cnt; //조회수
	/** 작성일 */
	private String reg_dt; //작성일  원래 Timestamp였음, Date
	/** 수정일 */
	private Timestamp mod_dt;
	private String category_cd_nm;

	/** 메세지 */
	private String msg;
	/** flag*/
	private String gflag;
	/** 시간까지 보여지도록  */
	private String date;

	private List<BoardFileDto> fileList;  //파일 리스트를 담기위해 Dto에도 담아줌
	private int file_no;
	private int filecount;

	private List<ReplyDto> replyList;
}
