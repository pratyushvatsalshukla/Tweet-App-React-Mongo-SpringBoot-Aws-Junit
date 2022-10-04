package com.tweetApp.blog.services;

import com.tweetApp.blog.payloads.CommentDto;

public interface CommentService {

	public CommentDto createComment(CommentDto commentDto, Integer postId) ;
	void deleteComment(Integer commentId) ;

}
