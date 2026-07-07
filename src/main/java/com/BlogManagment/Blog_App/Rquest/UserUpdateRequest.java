package com.BlogManagment.Blog_App.Rquest;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {
    
    private long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private MultipartFile image;

    private boolean imageChanged;
}
