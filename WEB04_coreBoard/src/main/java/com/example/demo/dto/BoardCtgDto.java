package com.example.demo.dto;

import lombok.Data;

@Data
public class BoardCtgDto {

	//Response //개발자
	private String grp_cd;

	//Request //사용자

	private String comm_cd;
	private String comm_cd_nm;
	private String del_yn;



}
