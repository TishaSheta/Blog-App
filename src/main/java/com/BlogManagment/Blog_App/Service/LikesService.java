package com.BlogManagment.Blog_App.Service;

import com.BlogManagment.Blog_App.Entity.BlogLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikesService {

    BlogLikes createLike(BlogLikes like);

    void deleteLike(Long id);

    void deleteLikeByBlogIdAndUserId(Long blogId, Long userId);

    boolean isLiked(Long userId, Long blogId);

    Page<BlogLikes> getAllLikeByBlog(Long blogId, Pageable pageable);

}