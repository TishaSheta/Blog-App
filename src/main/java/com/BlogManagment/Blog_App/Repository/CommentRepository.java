package com.BlogManagment.Blog_App.Repository;


import com.BlogManagment.Blog_App.Entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<BlogComment, Long> {

    @Query("SELECT c FROM BlogComment c WHERE c.blog.id = :blogId")
    Page<BlogComment> findAllCommentsByBlogId(long blogId, Pageable pageable);
}
