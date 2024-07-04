package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.dto.UserResponseDto;
import com.sparta.skillproject.entity.User;
import com.sparta.skillproject.entity.UserPasswordRecord;
import com.sparta.skillproject.exception.PasswordMismatchException;
import com.sparta.skillproject.exception.UserNotFoundException;
import com.sparta.skillproject.jwt.UserDetailsImpl;
import com.sparta.skillproject.repository.UserPasswordRepository;
import com.sparta.skillproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

	private final UserPasswordRepository userPasswordRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserPasswordServiceImpl(UserPasswordRepository userPasswordRepository, UserRepository userRepository,
		PasswordEncoder passwordEncoder) {
		this.userPasswordRepository = userPasswordRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	//비밀번호 변경
	@Override
	@Transactional
	public UserResponseDto userPasswordChange(UserRequestDto.ChangePassword requestDto, UserDetailsImpl userDetails) {
		// 사용자 아이디 확인
		User user = userRepository.findByUsername(requestDto.getUsername())
			.orElseThrow(() -> new UserNotFoundException("The user name does not exist"));

		// 현재 비밀번호 일치 여부 확인
		if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
			throw new PasswordMismatchException("Passwords do not match");
		}

		//최근 3개가져오기
		List<UserPasswordRecord> userPasswordRecordList = userPasswordRepository.findTop3ByUserIdOrderByCreatedAtDesc(
			userDetails.getUserId());

		// 새 비밀번호 암호화
		String changePasswordEncoded = passwordEncoder.encode(requestDto.getChangePassword());

		// 최근 3개의 비밀번호와 일치하는지 확인
		for (UserPasswordRecord userPassword : userPasswordRecordList) {
			if (passwordEncoder.matches(requestDto.getChangePassword(), userPassword.getUserPassword())) {
				throw new PasswordMismatchException(
					"Your new password is the same as a recently used password. Please choose a different password.");
			}
		}

		// 비밀번호 변경
		user.ChangePassword(changePasswordEncoded);

		// 새로운 비밀번호 기록 저장
		UserPasswordRecord changePasswordRecord = new UserPasswordRecord(changePasswordEncoded);
		user.addPasswordRecord(changePasswordRecord);

		// 가장 오래된 비밀번호 기록 삭제
		if (userPasswordRecordList.size() >= 3) {
			UserPasswordRecord oldPassword = userPasswordRepository.findByUserIdAndCreatedAt(userDetails.getUserId(),
				userPasswordRecordList.get(2).getCreatedAt());
			user.removePasswordRecord(oldPassword);
		}

		userRepository.save(user);

		return new UserResponseDto(userDetails.getUsername(), userDetails.getNickname(), userDetails.getIntroduce(),
			userDetails.getEmail());
	}
}
