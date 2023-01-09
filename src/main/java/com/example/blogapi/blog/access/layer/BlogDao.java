package com.example.blogapi.blog.access.layer;

import com.example.blogapi.blog.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aidin Ghassemloi
 * Interface extends JpaRepository to be able to use its functions and overloads. Other framewords can be extended here.
 * Custom functions can also be defined in this class.
 * @see JpaRepository
 */
public interface BlogDao extends JpaRepository<Blog, Long> {
}
