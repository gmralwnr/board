package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardListReqDto;

@Mapper
public interface BoardDao {

	//Board 전체리스트 가져오기
	List<BoardDto> boardList(BoardListReqDto bkdto);

	int getBoardCount(BoardListReqDto bkdto);



}
