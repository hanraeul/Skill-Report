package com.sparta.skillproject.service;

import com.sparta.skillproject.entity.RefreshToken;
import com.sparta.skillproject.entity.User;
import com.sparta.skillproject.entity.User;
import com.sparta.skillproject.exception.TokenNotFoundException;
import com.sparta.skillproject.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	private final JwtServiceImpl jwtService;

	public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtServiceImpl jwtService) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
	}

	@Override
	public RefreshToken findByToken(String token) {
		return refreshTokenRepository.findByToken(token)
			.orElseThrow(() -> new TokenNotFoundException("Not found Refresh Token."));
	}

	@Override
	public RefreshToken updateRefreshToken(User user) {
		if (refreshTokenRepository.existsByUsername(user.getUsername())) {
			RefreshToken refreshToken = refreshTokenRepository.findByUsername(user.getUsername());
			jwtService.updateRefreshToken(refreshToken, user);

			return refreshTokenRepository.save(refreshToken);
		} else {

			return refreshTokenRepository.save(jwtService.createRefreshToken(user));
		}
	}

	@Override
	@Transactional
	public void deleteRefreshToken(String username) {

		refreshTokenRepository.deleteByUsername(username);
	}
}
