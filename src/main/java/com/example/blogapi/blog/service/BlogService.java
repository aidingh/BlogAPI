package com.example.blogapi.blog.service;

import com.example.blogapi.blog.models.Blog;
import com.example.blogapi.blog.repositories.BlogRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Aidin Ghassemloi
 * Service layer for blog entries. Service class is responsible for validating and filtering blog entries.
 */
@Service
public class BlogService {
    public final BlogRepository blogRepository;
    public Pattern pattern = Pattern.compile("^[a-zA-Z0-9-]+$");

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    /**
     * Functions will validate blog before inserting it into the database.
     * <p>
     * Null-check and length-check is handled in the Blog class itself. Title and content attributes use @Column(nullable = false, length = limit).
     * Functions will only validate that the title is upper-case.
     * <p>
     * Function will validate the tags to only be letters, digits or '-' with regex.
     * @param blogEntry Blog entry object to validated before insert.
     * @return ResponseEntity<String> returns a response containing the status of the REST-action.
     */
    public ResponseEntity<String> validateBlog(Blog blogEntry){

        if(!StringUtils.isAllUpperCase(blogEntry.title)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title chars must be uppercase");
        }
        else if(blogEntry.tags != null && blogEntry.tags.stream().anyMatch(tag -> tag.length() > 30)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag length is more than 30 in char length");
        }
        else if(blogEntry.tags != null && blogEntry.tags.stream().anyMatch(tag -> !pattern.matcher(tag).matches())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag can only contain letters, digits or " + "-");
        }
        return blogRepository.insert(blogEntry);
    }

    /**
     * Functions get all blog entries in the database with filter options if they are specified.
     * If no filter-options are specified all blog entries are returned, else it will return blog entries filtered by keywords.
     * @param keywords List of tag-keywords to filter blogs using their tags.
     * @return ResponseEntity<List<Blog>> returns a list of blog entries.
     */
    public ResponseEntity<List<Blog>> filterBlogs(List<String> keywords){

        List<Blog> filteredBlogEntries = new ArrayList<>();

        if(keywords == null || keywords.isEmpty()){
            return blogRepository.getAll();
        }

        List<Blog> blogList = blogRepository.getAll().getBody();

        if (blogList != null) {
            filteredBlogEntries = blogList.stream().filter(blog -> blog.tags.stream().anyMatch(keywords::contains)).collect(Collectors.toList());
        }
        return new ResponseEntity<>(filteredBlogEntries, HttpStatus.OK);
    }
}
