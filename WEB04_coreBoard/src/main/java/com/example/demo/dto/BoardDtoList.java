package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardDtoList {

//MAP대신 사용
	/** 게시판 리스트*/
	private List<BoardDto> boardList;
	/** 게시판 개수*/
	private int count;

}
