package com.example.blogapi.blog.controllers.rest;

import com.example.blogapi.blog.models.Blog;
import com.example.blogapi.blog.repositories.BlogRepository;
import com.example.blogapi.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Aidin Ghassemloi
 * RestController for blog entities. A CRUD for blog entries.
 * <a href="http://localhost:8080/swagger-ui.html">...</a> for Swagger-ui documentation
 * <a href="http://localhost:8080/h2-console">...</a> for H2 console
 * @see Blog for more information about the enity and its properties.
 * @see BlogRepository
 * @see BlogService
 */

@RestController
@RequestMapping("/api/v1")
public class BlogController   {

    private final BlogRepository blogRepository;
    private final BlogService blogService;

    public BlogController(BlogRepository blogRepository, BlogService blogService) {
        this.blogRepository = blogRepository;
        this.blogService = blogService;
    }

    /**
     * Endpoint will insert a blog entity.
     * Before inserting a blog entry, validation must be made.
     * @param blogEntry Blog entry object to be updated into database.
     * @return ResponseEntity<String> returns a response containing the status of the REST-action.
     */
    @Operation(summary = "Inserts a new blog entry")
    @RequestMapping(value = "/insert/blog", method = RequestMethod.POST)
    public ResponseEntity<String> insertBlogEntry(@RequestBody Blog blogEntry) {
        return blogService.validateBlog(blogEntry);
    }

    /**
     * Endpoint will update a blog entity.
     * Before updating a blog entry, validation must be made.
     * @param id path variable to check if blog entity exists.
     * @param blogEntry Blog entry object to be updated.
     * @return ResponseEntity<Blog> returns the updated blog entry.
     */
    @Operation(summary = "Updates a blog entry")
    @RequestMapping(value = "/update/blog/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog> updateBlogEntry(@RequestBody Blog blogEntry, @PathVariable String id) {
        return blogRepository.update(id, blogEntry);
    }

    /**
     * Endpoint will delete a blog entity by id.
     * @param id path variable to delete blog entry with.
     * @return ResponseEntity<String> returns a response containing the status of the REST-action.
     */
    @Operation(summary = "Removes a blog entry by id")
    @RequestMapping(value = "delete/blog/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBlogEntry(@PathVariable String id){
        return blogRepository.delete(id);
    }

    /**
     * Endpoint will get a blog entity by id.
     * @param id path variable to get a blog entry with.
     * @return ResponseEntity<Blog> returns the fetched blog entry.
     */
    @Operation(summary = "Get a blog entry by id")
    @RequestMapping(value = "/get/blog/{id}", method = RequestMethod.GET)
    public ResponseEntity<Blog> getBlogById(@PathVariable String id){
        return blogRepository.getById(id);
    }

    /**
     * Endpoint will get all blog entries if request-body is empty i.e. no filters.
     * If request-body is not empty we will filter blog entries by tag-keywords.
     * @param keyWords list of tag-keywords to filter blogs with.
     * @return ResponseEntity<List<Blog>> returns a list of blog entries.
     */
    @Operation(summary = "Get all blog entries or filter them by tags")
    @RequestMapping(value = "/list/blogs", method = RequestMethod.GET)
    public ResponseEntity<List<Blog>> listBlogEntries(@Parameter(description = "If empty all blog entries are fetched, else filter blog entries by tags") @RequestParam(required=false) List<String> keyWords){
        return blogService.filterBlogs(keyWords);
    }
}

