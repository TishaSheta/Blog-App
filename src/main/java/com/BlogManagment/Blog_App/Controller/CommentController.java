package com.BlogManagment.Blog_App.Controller;


import com.BlogManagment.Blog_App.Entity.Blog;
import com.BlogManagment.Blog_App.Entity.BlogComment;
import com.BlogManagment.Blog_App.Entity.User;
import com.BlogManagment.Blog_App.Exception.BlogException;
import com.BlogManagment.Blog_App.Response.ApiResponse;
import com.BlogManagment.Blog_App.Rquest.CommentRequest;
import com.BlogManagment.Blog_App.Service.BlogService;
import com.BlogManagment.Blog_App.Service.CommentService;
import com.BlogManagment.Blog_App.Service.CustomUserDetails;
import com.BlogManagment.Blog_App.Service.UserService;
import com.BlogManagment.Blog_App.dto.BlogCommentDTO;
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

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;


    @PostMapping("/secure/create")
    public ResponseEntity<ApiResponse<?>> postComment(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody CommentRequest request,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()){
            throw new BlogException("Invalid Input Data" , HttpStatus.BAD_REQUEST);
        }
        User authUser = customUserDetails.getUser();

        if (authUser.getId() != request.getUserId()) {
            throw new BlogException("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        User findUser = userService.findUserById(request.getUserId());

        if (findUser == null){
            throw new BlogException("User Not Found" ,HttpStatus.NOT_FOUND);
        }

        Blog findBlog = blogService.findBlogById(request.getBlogId());
         if(findBlog == null){
             throw new BlogException("Blog Not Found" ,HttpStatus.NOT_FOUND);
         }
        BlogComment  comment = new BlogComment();
         comment.setBlog(findBlog);
         comment.setComment(request.getComment());
         comment.setUser(findUser);
         comment = commentService.createComment(comment);

         return ResponseEntity.ok(new ApiResponse<>("Succes" , "Comment Added Successfully" , null));

    }

    @GetMapping("/public/blog")
    public ResponseEntity<ApiResponse<?>> getAllCommentOfBlog(
            @RequestParam Long blogId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("blogId").descending());

        Page<BlogCommentDTO> comments = commentService.findAllCommentsByBlogId(blogId,pageable).map(comment ->{
            BlogCommentDTO commentDTO = new BlogCommentDTO();
            commentDTO.setBlogId(blogId);
            commentDTO.setComment(comment.getComment());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setId(comment.getId());
            commentDTO.setUserId(comment.getUser().getId());
            commentDTO.setUserName(comment.getUser().getName());
            return commentDTO;
        });
        return ResponseEntity.ok(new ApiResponse<>("success", "Comments for Blog", comments));
    }
    }
