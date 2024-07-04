package com.sparta.skillproject.controller;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.service.AuthService;
import com.sparta.skillproject.util.SuccessResponse;
import com.sparta.skillproject.util.SuccessResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<String>> login(@RequestBody @Valid UserRequestDto.Login requestDto) {

		String token = authService.login(requestDto);
		return SuccessResponseFactory.ok(token);
	}

	@DeleteMapping("/logout")
	public ResponseEntity<SuccessResponse<Void>> logout(@RequestHeader("Authorization") String authorizationHeader) {

		authService.logout(authorizationHeader);
		return SuccessResponseFactory.ok();
	}

}
