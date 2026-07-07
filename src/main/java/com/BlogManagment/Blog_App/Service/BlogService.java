package com.BlogManagment.Blog_App.Service;

import com.BlogManagment.Blog_App.Entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    public Blog createBlog(Blog blog);

    public Blog updateBlog(Blog blog);

    public Page<Blog> findAllBlogs(String searchtag , Pageable pageable);

    public Page<Blog> findAllBlogsByUserId(long userId , Pageable pageable);

    public Blog findBlogById(Long id);

    public void deleteBlog(Long id);
}
