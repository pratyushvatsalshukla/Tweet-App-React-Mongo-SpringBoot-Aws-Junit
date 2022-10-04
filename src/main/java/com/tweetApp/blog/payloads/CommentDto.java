package com.tweetApp.blog.payloads;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CommentDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	private String content ;
	

}
