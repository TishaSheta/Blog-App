package com.BlogManagment.Blog_App.Rquest;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserSignInRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
