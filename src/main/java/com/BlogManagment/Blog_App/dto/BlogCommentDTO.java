package com.BlogManagment.Blog_App.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogCommentDTO {

    private Long id;

    private long userId;

    private long blogId;

    private String comment;

    private String userName;

    private LocalDateTime createdAt;
}
