package com.example.blogapi.blog.models;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Aidin Ghassemloi
 * Blog entity that is persisted as an H2 database.
 * Title and content properties cant be null and cant be over a certian length. The checks are made with the @Column decorator.
 */
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;
    public Blog(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }
    public Blog() {}
    @CreationTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public LocalDateTime creationStamp;
    @UpdateTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime lastUpdatedDate;
    @Column(nullable = false, length = 100)
    public String title;
    @Column(nullable = false, length = 1000)
    public String content;
    @ElementCollection(targetClass=String.class)
    public List<String> tags;
    public LocalDateTime  getCreationStamp() {
        return creationStamp;
    }
    public void setCreationStamp(LocalDateTime  creationStamp) {
        this.creationStamp = creationStamp;
    }
    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
