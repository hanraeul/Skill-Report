package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.dto.UserResponseDto;
import com.sparta.skillproject.jwt.UserDetailsImpl;

public interface UserService {

	void registerUser(UserRequestDto.Register requestDto);

	//회원 정보 가져오기
	UserResponseDto getUser(UserDetailsImpl userDetails);
	//회원 정보 수정
	UserResponseDto editUser(UserRequestDto.EditInfo requestDto, UserDetailsImpl userDetails);
	//회원 탈퇴
	void withdraw(UserRequestDto.Withdrawal requestDto, UserDetailsImpl userDetails);
}
