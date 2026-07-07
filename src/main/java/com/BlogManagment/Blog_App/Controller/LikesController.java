package com.BlogManagment.Blog_App.Controller;


import com.BlogManagment.Blog_App.Entity.Blog;
import com.BlogManagment.Blog_App.Entity.BlogLikes;
import com.BlogManagment.Blog_App.Entity.User;
import com.BlogManagment.Blog_App.Exception.BlogException;
import com.BlogManagment.Blog_App.Response.ApiResponse;
import com.BlogManagment.Blog_App.Rquest.LikeRequest;
import com.BlogManagment.Blog_App.Service.*;
import com.BlogManagment.Blog_App.dto.BlogLikesDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/like")
public class LikesController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private LikesService likesService;

    @Autowired
    private UserService userService;




    @PostMapping("/secure/create")
    public ResponseEntity<ApiResponse<?>> likeBlog(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody LikeRequest request,
            BindingResult bindingResult
            )
    {
        User authUser = userDetails.getUser();

        if (bindingResult.hasErrors())
        {
            throw new BlogException("Invalid Input" , HttpStatus.BAD_REQUEST);
        }
        if(authUser.getId()!= request.getUserId().longValue())
        {
           throw  new BlogException("Unauthorized" , HttpStatus.NOT_FOUND);
        }
        boolean alreadyLiked = likesService.isLiked(request.getUserId() , request.getBlogId());

        if(alreadyLiked){
            throw new BlogException("Blog Already Liked" , HttpStatus.BAD_REQUEST);

        }
        Blog findBlog = blogService.findBlogById(request.getBlogId());

        if (findBlog == null){
            throw new BlogException("Blog Not Found" , HttpStatus.NOT_FOUND);
        }
        User findUser = userService.findUserById(request.getUserId());

        if (findUser == null){
            throw new BlogException("User Not Found" , HttpStatus.NOT_FOUND);
        }

        BlogLikes newLike = new BlogLikes();
        newLike.setBlog(findBlog);
        newLike.setCreatedAt(LocalDateTime.now());
        newLike.setUser(findUser);
        newLike = likesService.createLike(newLike);

        return ResponseEntity.ok(new ApiResponse<>("success" , "Blog Liked Successfully" , newLike));
    }

    @PostMapping("/secure/dislike")
    public ResponseEntity<ApiResponse<?>> disLikeBlog(
            @AuthenticationPrincipal CustomUserDetails userDetails ,
            @Valid @RequestBody LikeRequest request ,
            BindingResult bindingResult)
    {
        User  authUser = userDetails.getUser();

        if(bindingResult.hasErrors())
        {
            throw new BlogException("Invalid Input", HttpStatus.BAD_REQUEST);
        }

        if(authUser.getId() != request.getUserId().longValue())
        {
            throw new BlogException("Unauthorized", HttpStatus.NOT_FOUND);
        }

        boolean alreadyLiked = likesService.isLiked(request.getUserId(),request.getBlogId());

        if(!alreadyLiked)
        {
            throw new BlogException("Blog is still not Liked", HttpStatus.NOT_FOUND);
        }

        Blog findBlog = blogService.findBlogById(request.getBlogId());
        if(findBlog==null)
        {
            throw new BlogException("Blog Not Found", HttpStatus.NOT_FOUND);
        }
        User findUser  = userService.findUserById(request.getUserId());

        if(findUser ==null)
        {
            throw new BlogException("User Not Found", HttpStatus.NOT_FOUND);
        }

        likesService.deleteLikeByBlogIdAndUserId(request.getUserId(),request.getBlogId());


        return ResponseEntity.ok(new ApiResponse<>("success", "Blog Liked successfully", null));
    }


    @PostMapping("/public/")
    public ResponseEntity<ApiResponse<?>> getAllLikeByBlog(
            @RequestParam Integer id,
            @RequestParam(required = false , defaultValue = "0" )Integer page,
            @RequestParam(required = false , defaultValue = "10" ) Integer limit

    )
    {

        Pageable pageable = PageRequest.of(page, limit , Sort.by("id").descending());
        Page<BlogLikesDTO> likes =
                likesService.getAllLikeByBlog(id.longValue(), pageable).map(like->{
            BlogLikesDTO blogLikeDTO = new BlogLikesDTO();
            blogLikeDTO.setBlogId(like.getBlog().getId());
            blogLikeDTO.setId(like.getId());
            blogLikeDTO.setUser(like.getUser().getName());
            blogLikeDTO.setUserId(like.getUser().getId());
            return blogLikeDTO;

        });


        return ResponseEntity.ok(new ApiResponse<>("success", "Blog Liked successfully",likes));
    }


}
