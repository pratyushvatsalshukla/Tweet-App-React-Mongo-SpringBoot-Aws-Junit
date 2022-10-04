package com.tweetApp.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PostResponse {           // 6.44ms

	private List<PostDto> content ;
	private int pageNumber ;
	private int pageSize ;
	private long totalElements ;
	private int totalPages ;
	private boolean lastpage ;

}
