package com.sparta.skillproject.controller;

import com.sparta.skillproject.dto.PostRequestDto;
import com.sparta.skillproject.dto.PostResponseDto;
import com.sparta.skillproject.jwt.UserDetailsImpl;
import com.sparta.skillproject.service.PostService;
import com.sparta.skillproject.util.SuccessResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	// 게시글 등록
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUserId();
		PostResponseDto responseDto = postService.createPost(requestDto, userId);
		return SuccessResponseFactory.ok(responseDto);
	}

	// 선택 게시글 조회
	@GetMapping("/{postId}")
	public ResponseEntity<?> getPost(@PathVariable Long postId) {
		PostResponseDto responseDto = postService.getPost(postId);
		return SuccessResponseFactory.ok(responseDto);
	}


	// 선택 게시글 수정
	@PutMapping("/{postId}")
	public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		Long userId = userDetails.getUserId();
		PostResponseDto responseDto = postService.updatePost(postId, requestDto, userId);
		return SuccessResponseFactory.ok(responseDto);
	}


}
