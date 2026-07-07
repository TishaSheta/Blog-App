package com.BlogManagment.Blog_App.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Blog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false ,length = 50000)
    private String content;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id" , nullable = false)
    private User author;


    @JsonIgnore
    @OneToMany(mappedBy = "blog" , cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<BlogLikes> likes;


    @JsonIgnore
    @OneToMany(mappedBy = "blog" , cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<BlogComment> comments;


    private String hashtag;

    private LocalDateTime createdAt;
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }


    private String imageUrl;

}
