package com.tweetApp.blog.services;

import java.util.List;

import com.tweetApp.blog.payloads.CategoryDto;

public interface CategoryService {
	
	// Create
	
	public CategoryDto createCategory(CategoryDto categoryDto) ;
	
	// Update
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) ;
	
	// Delete
	public void deleteCategory(Integer categoryId) ;
	
	// Get
	public CategoryDto getCategory(Integer categoryId) ;
	
	//Get All
	public List<CategoryDto> getCategories() ;

}
