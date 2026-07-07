package com.BlogManagment.Blog_App.Service.Impl;


import com.BlogManagment.Blog_App.Entity.Blog;
import com.BlogManagment.Blog_App.Repository.BlogRepository;
import com.BlogManagment.Blog_App.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> findAllBlogs(String searchtag, Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> findAllBlogsByUserId(long userId, Pageable pageable) {
        return blogRepository.findByUserId(userId, pageable);
    }

    @Override
    public Blog findBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}

