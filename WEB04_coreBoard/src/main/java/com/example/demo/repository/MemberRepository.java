package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.MemberDto;

@Mapper
public interface MemberRepository {
//연결 해주는 멤버 서비스에서 연결이 됨
	List<MemberDto> getAllMember(); //<mapper namespace="com.example.demo.repository.MemberRepository">



}
