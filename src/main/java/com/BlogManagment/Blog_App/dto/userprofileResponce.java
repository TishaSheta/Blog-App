package com.BlogManagment.Blog_App.dto;

import com.BlogManagment.Blog_App.Entity.Blog;
import lombok.Data;

import java.util.List;

@Data
public class userprofileResponce {

        private Long id;
        private String email;
        private String password;
        private List<Blog> blogs;
        private String name;

}
