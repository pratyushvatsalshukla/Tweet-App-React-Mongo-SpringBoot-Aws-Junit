package com.tweetApp.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetApp.blog.entities.Comment;
import com.tweetApp.blog.entities.Post;
import com.tweetApp.blog.exceptions.ResourceNotFoundException;
import com.tweetApp.blog.payloads.CommentDto;
import com.tweetApp.blog.repositories.CommentRepository;
import com.tweetApp.blog.repositories.PostRepository;
import com.tweetApp.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepo ;
	
	@Autowired
	private CommentRepository commentRepo ;
	
	@Autowired
	private ModelMapper modelMapper ;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", " Post Id ", postId)) ;
		Comment comment = this.modelMapper.map(commentDto, Comment.class) ;
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment) ;
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", " comment Id ", commentId)) ;
		this.commentRepo.delete(comment);
	}

}
