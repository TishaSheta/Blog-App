package com.BlogManagment.Blog_App.Service.Impl;


import com.BlogManagment.Blog_App.Entity.BlogComment;
import com.BlogManagment.Blog_App.Repository.CommentRepository;
import com.BlogManagment.Blog_App.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public BlogComment createComment(BlogComment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


    @Override
    public Page<BlogComment> findAllCommentsByBlogId(long blogId, Pageable pageable) {
        return commentRepository.findAllCommentsByBlogId(blogId, pageable);
    }

    @Override
    public Page<BlogComment> findAllComment(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
