package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;

public interface AuthService {

	String login(UserRequestDto.Login requestDto);

	void logout(String header);
}
