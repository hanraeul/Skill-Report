package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.UserRequestDto;
import com.sparta.skillproject.entity.RefreshToken;
import com.sparta.skillproject.entity.User;
import com.sparta.skillproject.entity.UserRole;

import com.sparta.skillproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;

	private final JwtServiceImpl jwtService;

	private final PasswordEncoder passwordEncoder;

	private final RefreshTokenServiceImpl refreshTokenService;

	public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtServiceImpl jwtService,
		RefreshTokenServiceImpl refreshTokenService) {

		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.refreshTokenService = refreshTokenService;
	}

	@Override
	public String login(UserRequestDto.Login requestDto) {

		User user = userRepository.findByUsername(requestDto.getUsername())
			.orElseThrow(() -> new UsernameNotFoundException("Invalid username"));



		if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
			String accessToken = jwtService.generateAccessToken(user.getUsername(), user.getRole());
			RefreshToken refreshToken = refreshTokenService.updateRefreshToken(user);

			jwtService.setRefreshTokenAtCookie(refreshToken);
			return accessToken;
		} else {
			log.info("valid password");
		}
        return null;
    }

	@Override
	public void logout(String header) {

		String token = jwtService.getAccessTokenFromHeader(header);
		String username = jwtService.extractUsername(token);

		refreshTokenService.deleteRefreshToken(username);

		jwtService.deleteRefreshTokenAtCookie();
	}
}
