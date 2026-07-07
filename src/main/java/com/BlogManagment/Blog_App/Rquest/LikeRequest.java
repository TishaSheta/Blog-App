package com.BlogManagment.Blog_App.Rquest;


import lombok.Data;


import jakarta.validation.constraints.NotNull;


@Data
public class LikeRequest {

    @NotNull
    private Long blogId;

    @NotNull
    private Long userId;
}
