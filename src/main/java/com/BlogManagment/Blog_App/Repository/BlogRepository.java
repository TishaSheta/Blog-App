package com.BlogManagment.Blog_App.Repository;


import com.BlogManagment.Blog_App.Entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Page<Blog> findByHashtagContaining(String hashtag, Pageable pageable);


    @Query("SELECT b FROM Blog b WHERE b.author.id = :userId")
    Page<Blog> findByUserId(long userId, Pageable pageable);

}
