package com.BlogManagment.Blog_App.dto;


import lombok.Data;

@Data
public class BlogLikesDTO {

    private long id;
    private String user;
    private Long userId;
    private Long blogId;

}
