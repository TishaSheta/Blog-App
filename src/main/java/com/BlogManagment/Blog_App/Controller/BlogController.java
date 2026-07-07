package com.BlogManagment.Blog_App.Controller;


import com.BlogManagment.Blog_App.Entity.Blog;
import com.BlogManagment.Blog_App.Entity.User;
import com.BlogManagment.Blog_App.Exception.BlogException;
import com.BlogManagment.Blog_App.Response.ApiResponse;
import com.BlogManagment.Blog_App.Rquest.CreateBlogRequest;
import com.BlogManagment.Blog_App.Service.BlogService;
import com.BlogManagment.Blog_App.Service.CustomUserDetails;
import com.BlogManagment.Blog_App.Service.LikesService;
import com.BlogManagment.Blog_App.dto.BlogDTO;
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
@RequestMapping("/public/blog")
public class BlogController {

    @Autowired
    private LikesService likeService;

    @Autowired
    private BlogService blogService;

    @PostMapping("/secure/create")
    public ResponseEntity<ApiResponse<?>> createBlog(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody CreateBlogRequest request,
            BindingResult bindingResult
            ){
        if (bindingResult.hasErrors()){
            throw new BlogException("Invalid Input Data" , HttpStatus.BAD_REQUEST);
        }

        User user = customUserDetails.getUser();

        Blog blog = new Blog();
        blog.setAuthor(user);
        blog.setContent(request.getContent());
        blog.setHashtag(request.getHashtags());
        blog.setTitle(request.getTitle());
        blog.setCreatedAt(LocalDateTime.now());
        blog = blogService.createBlog(blog);

        return ResponseEntity.ok(new ApiResponse<>("Succes" ,"Blog Created Succes" , null));
    }

    @PutMapping("/secure/update/{blogid}")
    public ResponseEntity<ApiResponse<?>> updateBolg(
            @PathVariable Long blogId,
            @Valid@RequestBody CreateBlogRequest request,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            throw new BlogException("Invalid Input Data" , HttpStatus.BAD_REQUEST);
    }

        Blog blog = blogService.findBlogById(blogId);

        if(blog == null){
            throw new BlogException("Blog Not Found" ,HttpStatus.NOT_FOUND);
        }

        blog.setContent(request.getContent());
        blog.setHashtag(request.getHashtags());
        blog.setLikes(blog.getLikes());
        blog.setTitle(request.getTitle());

        blog = blogService.updateBlog(blog);
        return ResponseEntity.ok(new ApiResponse<>("success","Blog updated Successfully", null));

    }

    @DeleteMapping("/secure/delete/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBlog(@PathVariable Long blogid) {

        Blog blog = blogService.findBlogById(blogid);

        if (blog == null) {
            throw new BlogException("Blog Not Found", HttpStatus.NOT_FOUND);
        }

        blogService.deleteBlog(blogid);
        return ResponseEntity.ok(new ApiResponse<>("success","Blog deleted Successfully", null));

    }

    @GetMapping("/public/{id}")
    public ResponseEntity<ApiResponse<?>> getBlogById(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long blogid,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {

        Blog blog = blogService.findBlogById(blogid);

        if(blog ==null)
        {
            throw new BlogException("Blog Not Found", HttpStatus.NOT_FOUND);
        }

        BlogDTO blogDTO = new BlogDTO();
        User user = blog.getAuthor();
        user.setPassword(null);
        blogDTO.setAuthor(user);
        blogDTO.setContent(blog.getContent());
        blogDTO.setId(blog.getId());
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setLikes(Long.parseLong(blog.getLikes().size() + ""));
        blogDTO.setComments(Long.parseLong(blog.getComments().size()+""));
        blogDTO.setHashtag(blog.getHashtag());
        blogDTO.setCreatedAt(blog.getCreatedAt());
        blogDTO.setLiked(customUserDetails == null ? false
                : likeService.isLiked(customUserDetails.getUser().getId(), blog.getId()));

        return ResponseEntity.ok(new ApiResponse<>("success","Blog Result", blogDTO));

    }

    @GetMapping("/public/")
    public ResponseEntity<ApiResponse<?>> getBlogs(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<Blog> blogs = blogService.findAllBlogs(q, pageable);
        Page<BlogDTO> blogsResponse = blogs.map(blog -> {
            BlogDTO blogDTO = new BlogDTO();
            User user = blog.getAuthor();
            user.setPassword(null);
            blogDTO.setAuthor(user);
            blogDTO.setContent(blog.getContent());
            blogDTO.setId(blog.getId());
            blogDTO.setTitle(blog.getTitle());
            blogDTO.setLikes(Long.parseLong(blog.getLikes().size() + ""));
            blogDTO.setComments(Long.parseLong(blog.getComments().size()+""));
            blogDTO.setHashtag(blog.getHashtag());
            blogDTO.setCreatedAt(blog.getCreatedAt());
            blogDTO.setLiked(customUserDetails == null ? false
                    : likeService.isLiked(customUserDetails.getUser().getId(), blog.getId()));
            return blogDTO;
        });
        return ResponseEntity.ok(new ApiResponse<>("success","Blog Result", blogsResponse));

    }

    @GetMapping("/public/user/{id}")
    public ResponseEntity<ApiResponse<?>> getBlogsUserId(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<Blog> blogs = blogService.findAllBlogsByUserId(id, pageable);
        Page<BlogDTO> blogsResponse = blogs.map(blog -> {

            BlogDTO blogDTO = new BlogDTO();
            User user = blog.getAuthor();
            user.setPassword(null);
            blogDTO.setAuthor(user);
            blogDTO.setContent(blog.getContent());
            blogDTO.setId(blog.getId());
            blogDTO.setLikes(Long.parseLong(blog.getLikes().size() + ""));
            blogDTO.setComments(Long.parseLong(blog.getComments().size()+""));
            blogDTO.setTitle(blog.getTitle());
            blogDTO.setHashtag(blog.getHashtag());
            return blogDTO;
        });

        return ResponseEntity.ok(new ApiResponse<>("success","Blog Result", blogsResponse));

    }

}


