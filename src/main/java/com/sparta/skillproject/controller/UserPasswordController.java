package com.sparta.skillproject.controller;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.dto.UserResponseDto;
import com.sparta.skillproject.jwt.UserDetailsImpl;
import com.sparta.skillproject.service.UserPasswordService;
import com.sparta.skillproject.util.SuccessResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserPasswordController {

	private final UserPasswordService userPasswordService;

	public UserPasswordController(UserPasswordService userPasswordService) {
		this.userPasswordService = userPasswordService;
	}

	@PutMapping("/password")
	public ResponseEntity<?> userPasswordChange(@RequestBody UserRequestDto.ChangePassword requestDto,
												@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserResponseDto userResponseDto = userPasswordService.userPasswordChange(requestDto, userDetails);
		return SuccessResponseFactory.ok(userResponseDto);
	}
}
