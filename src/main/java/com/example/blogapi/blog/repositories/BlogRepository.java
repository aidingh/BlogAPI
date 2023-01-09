package com.example.blogapi.blog.repositories;

import com.example.blogapi.blog.access.layer.BlogDao;
import com.example.blogapi.blog.models.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * @author Aidin Ghassemloi
 * Repository blog entries. Repository class is responsible for CRUD-operations.
 * @see BlogDao
 */
@Repository
public class BlogRepository {

    @Autowired
    public BlogDao blogDao;
    public BlogRepository( ) {}

    /**
     * Saves a blog entry into the database.
     * @param blogEntry Blog entry object to be saved into database.
     * @return ResponseEntity<String> returns a response containing the status of the REST-action.
     */
    public ResponseEntity<String> insert(Blog blogEntry) {
        try {
            blogDao.save(blogEntry);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong inserting blog");
        }
    }

    /**
     * Removes a blog entry from the database.
     * @param id delete a blog entry by id.
     * @return ResponseEntity<String> returns a response containing the status of the REST-action.
     */
    public ResponseEntity<String> delete(String id){
        try {
            blogDao.deleteById(Long.parseLong(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong removing blog");
        }
    }

    /**
     * Updates a blog entry from the database.
     * @param id update a blog entry by id.
     * @param blogEntry Blog entry object to be updated into the database.
     * @return ResponseEntity<Blog> returns the updated blog entry.
     */
    public ResponseEntity<Blog> update(String id, Blog blogEntry){

            Optional<Blog> blog = blogDao.findById(Long.parseLong(id));

            if (blog.isPresent()) {
                Blog _blog = blog.get();
                _blog.setTitle(blogEntry.getTitle());
                _blog.setContent(blogEntry.getContent());
                _blog.setTags(blogEntry.getTags());
                return new ResponseEntity<>(blogDao.save(_blog), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get blog entry by id.
     * @param id get a blog entry by id.
     * @return ResponseEntity<Blog> returns the blog entry found by the id.
     */
    public ResponseEntity<Blog> getById(String id){
        Optional<Blog> blogOptional = blogDao.findById(Long.parseLong(id));
        return blogOptional.map(blog -> new ResponseEntity<>(blog, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Get all blog entries.
     * @return ResponseEntity<List<Blog>> returns a list of all blog entries.
     */
    public ResponseEntity<List<Blog>> getAll(){
        try {
            return new ResponseEntity<>(blogDao.findAll(Sort.by(Sort.Direction.DESC, "creationStamp")), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Before the application is started three blog entries are pre-defined and saved into the database.
     * This is not mandatory, but it is pleasant to have data before application-startup.
     */
    @PostConstruct
    private void init() {
        blogDao.save(new Blog("HOW TO NOT SET YOUR HAIR ON FIRE", "Tutorial on how to not lose your hair", Stream.of("Baldness", "A lot of hair", "Prevent hair loss").collect(Collectors.toList())));
        blogDao.save(new Blog("BEST DAD JOKES OF 2023", "Where do fruits go on vacation? Pear-is!", Stream.of("Dad-jokes", "Funny", "2023").collect(Collectors.toList())));
        blogDao.save(new Blog("MAGNUS DID NOTHING WRONG", "We all know that Magnus just wanted to help the emperor", Stream.of("Warhammer 40k", "Ultra-marines-lol", "Horus").collect(Collectors.toList())));
    }
}

