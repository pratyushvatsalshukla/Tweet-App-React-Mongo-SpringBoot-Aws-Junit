package com.tweetApp.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tweetApp.blog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
