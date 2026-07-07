package com.BlogManagment.Blog_App.Service;

import com.BlogManagment.Blog_App.Entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    public BlogComment createComment(BlogComment comment);
    public  void deleteComment(Long id);
    public Page<BlogComment> findAllCommentsByBlogId(long blogId , Pageable pageable);
    public Page<BlogComment>  findAllComment (Pageable pageable);
}
