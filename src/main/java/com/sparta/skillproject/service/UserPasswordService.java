package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.dto.UserResponseDto;
import com.sparta.skillproject.jwt.UserDetailsImpl;

public interface UserPasswordService {
	//비밀번호 변경
	UserResponseDto userPasswordChange(UserRequestDto.ChangePassword requestDto, UserDetailsImpl userDetails);
}
