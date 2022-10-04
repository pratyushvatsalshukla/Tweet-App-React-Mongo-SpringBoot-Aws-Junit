package com.tweetApp.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetApp.blog.entities.Comment;
import com.tweetApp.blog.payloads.ApiResponse;
import com.tweetApp.blog.payloads.CommentDto;
import com.tweetApp.blog.services.CommentService;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	
	@Autowired
	private CommentService commentService ;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto, 
			@PathVariable Integer postId
			){
		CommentDto createComment = this.commentService.createComment(commentDto, postId) ;
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.OK) ;
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable Integer commentId
			){
			this.commentService.deleteComment(commentId);
			return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully !!", true), HttpStatus.OK) ;
	}

}
