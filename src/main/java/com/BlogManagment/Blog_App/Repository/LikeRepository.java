package com.BlogManagment.Blog_App.Repository;

import com.BlogManagment.Blog_App.Entity.BlogLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<BlogLikes, Long> {

    boolean existsByUserIdAndBlogId(Long userId, Long blogId);

    Page<BlogLikes> findByBlogId(Long blogId, Pageable pageable);

    void deleteByUserIdAndBlogId(Long userId, Long blogId);

    long countByBlogId(Long blogId);

}