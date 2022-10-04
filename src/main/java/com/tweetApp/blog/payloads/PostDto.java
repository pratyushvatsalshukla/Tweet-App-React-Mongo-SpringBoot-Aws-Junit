package com.tweetApp.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tweetApp.blog.entities.Category;
import com.tweetApp.blog.entities.Comment;
import com.tweetApp.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId ;

	private String title ;
	private String content ;
	private String imageName ;
	private Date addedDate ;
	
	private CategoryDto category ;

	private UserDto user ;
	
	private Set<CommentDto> comments = new HashSet<>() ;
}
