package com.sparta.skillproject.controller;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.dto.UserResponseDto;
import com.sparta.skillproject.jwt.UserDetailsImpl;
import com.sparta.skillproject.service.UserService;
import com.sparta.skillproject.util.SuccessResponse;
import com.sparta.skillproject.util.SuccessResponseFactory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserRequestDto.Register requestDto) {
		userService.registerUser(requestDto);
		return SuccessResponseFactory.ok();
	}

	@GetMapping
	public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		UserResponseDto userInfo = userService.getUser(userDetails);
		return SuccessResponseFactory.ok(userInfo);
	}

	@PutMapping
	public ResponseEntity<?> editUser(@RequestBody @Valid UserRequestDto.EditInfo requestDto,
										  @AuthenticationPrincipal UserDetailsImpl userDetails) {

		UserResponseDto editUserInfo = userService.editUser(requestDto, userDetails);
		return SuccessResponseFactory.ok(editUserInfo);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponse<Void>> withdraw(@RequestBody @Valid UserRequestDto.Withdrawal requestDto,
														  @AuthenticationPrincipal UserDetailsImpl userDetails) {

		userService.withdraw(requestDto, userDetails);
		return SuccessResponseFactory.ok();
	}

}
