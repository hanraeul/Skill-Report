package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.dto.UserResponseDto;
import com.sparta.skillproject.entity.User;
import com.sparta.skillproject.entity.UserPasswordRecord;
import com.sparta.skillproject.entity.UserRole;
import com.sparta.skillproject.exception.DuplicateUsernameException;
import com.sparta.skillproject.exception.PasswordMismatchException;
import com.sparta.skillproject.exception.UsernameMismatchException;
import com.sparta.skillproject.jwt.UserDetailsImpl;
import com.sparta.skillproject.repository.UserRepository;
import com.sparta.skillproject.exception.UsernameMismatchException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void registerUser(UserRequestDto.Register requestDto) {

		if (userRepository.existsByUsername(requestDto.getUsername())) {
			throw new DuplicateUsernameException("Duplicate username.");
		}

		User user = User.builder()
			.username(requestDto.getUsername())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.introduce(requestDto.getIntroduce())
			.email(requestDto.getEmail())
			.role(UserRole.USER)
			.build();

		UserPasswordRecord userPasswordRecord = new UserPasswordRecord(
			passwordEncoder.encode(requestDto.getPassword()));
		user.addPasswordRecord(userPasswordRecord);

		userRepository.save(user);
	}

	//회원 정보
	@Override
	public UserResponseDto getUser(UserDetailsImpl userDetails) {

		return new UserResponseDto(userDetails.getUsername(), userDetails.getNickname(), userDetails.getIntroduce(),
			userDetails.getEmail());
	}

	//회원 정보 수정
	@Override
	public UserResponseDto editUser(UserRequestDto.EditInfo requestDto, UserDetailsImpl userDetails) {
		// 비밀번호 확인
		if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordMismatchException("Passwords do not match");
		}

		User user = userDetails.getUser();
		user.editInfo(requestDto.getNickname(), requestDto.getIntroduce());

		userRepository.save(user);

		return new UserResponseDto(userDetails.getUsername(), userDetails.getNickname(), userDetails.getIntroduce(),
			userDetails.getEmail());
	}

	//회원 탈퇴
	@Override
	public void withdraw(UserRequestDto.Withdrawal requestDto, UserDetailsImpl userDetails) {

		// 요청된 사용자 이름과 현재 로그인한 사용자가 일치하는지 확인
		if (!requestDto.getUsername().equals(userDetails.getUsername())) {
			throw new UsernameMismatchException("Login ID does not match");
		}

		// 비밀번호 확인
		if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordMismatchException("Passwords do not match");
		}

		User user = userDetails.getUser();
		user.updateRole(UserRole.WITHDRAWAL);

		userRepository.save(user);
	}

}
