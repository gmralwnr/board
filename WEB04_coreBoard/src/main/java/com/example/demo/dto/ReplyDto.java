package com.example.demo.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class ReplyDto {
	private List<ReplyDto> replyList;


	private int reply_no;
	private Integer board_no;
	private String content;
	private String indate;
	private String reply_nm;
	private String reply_password;
	private String flag;
	private String msg;
	private int result;
	private int rnum;
	private int reply_count;

}
