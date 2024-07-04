package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;


public interface UserService {

	void registerUser(UserRequestDto.Register requestDto);
}
