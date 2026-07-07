package com.BlogManagment.Blog_App.dto;


import com.BlogManagment.Blog_App.Entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogDTO {

    private Long id;
    private String title;
    private String content;
    private User author;
    private String hashtags;
    private Long likes;
    private Long comments;
    private String hashtag;
    private LocalDateTime createdAt;
    private String imageUrl;
    private boolean isLiked;

}
