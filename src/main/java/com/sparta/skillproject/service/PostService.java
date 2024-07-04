package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.PostRequestDto;
import com.sparta.skillproject.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
	PostResponseDto createPost(PostRequestDto requestDto, Long userId);
	PostResponseDto updatePost(Long id, PostRequestDto requestDto, Long userId);
}
