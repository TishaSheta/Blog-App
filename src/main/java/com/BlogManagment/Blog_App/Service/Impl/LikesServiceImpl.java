package com.BlogManagment.Blog_App.Service.Impl;

import com.BlogManagment.Blog_App.Entity.BlogLikes;
import com.BlogManagment.Blog_App.Repository.LikeRepository;
import com.BlogManagment.Blog_App.Service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public BlogLikes createLike(BlogLikes like) {
        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public void deleteLikeByBlogIdAndUserId(Long blogId, Long userId) {
        likeRepository.deleteByUserIdAndBlogId(userId, blogId);
    }

    @Override
    public boolean isLiked(Long userId, Long blogId) {
        return likeRepository.existsByUserIdAndBlogId(userId, blogId);
    }

    @Override
    public Page<BlogLikes> getAllLikeByBlog(Long blogId, Pageable pageable) {
        return likeRepository.findByBlogId(blogId, pageable);
    }

}