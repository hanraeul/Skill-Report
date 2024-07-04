package com.sparta.skillproject.repository;

import com.sparta.skillproject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

	Boolean existsByUsername(String username);

	RefreshToken findByUsername(String username);

	void deleteByUsername(String username);
}
