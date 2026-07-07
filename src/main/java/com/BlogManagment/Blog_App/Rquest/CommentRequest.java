package com.BlogManagment.Blog_App.Rquest;


import lombok.Data;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
public class CommentRequest {

    @NotNull
    private long userId;

    @NotNull
    private Long blogId;

    @NotBlank
    private String content;

    private String comment;
}


