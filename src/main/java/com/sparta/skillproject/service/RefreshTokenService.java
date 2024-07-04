package com.sparta.skillproject.service;

import com.sparta.skillproject.entity.RefreshToken;
import com.sparta.skillproject.entity.User;

public interface RefreshTokenService {
	RefreshToken findByToken(String token);

	RefreshToken updateRefreshToken(User user);

	void deleteRefreshToken(String username);
}
