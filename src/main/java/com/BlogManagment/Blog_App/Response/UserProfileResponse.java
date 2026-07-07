package com.BlogManagment.Blog_App.Response;


import lombok.Data;

@Data
public class UserProfileResponse {
   private Long id;
   private String email;
   private String name;
   private String profileImgUrl;
   private String password;
   private Long blogCount;


}



