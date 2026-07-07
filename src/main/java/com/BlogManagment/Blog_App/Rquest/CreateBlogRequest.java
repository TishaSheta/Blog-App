package com.BlogManagment.Blog_App.Rquest;


import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class CreateBlogRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String image;
    @NotBlank
    private String hashtags;
}
