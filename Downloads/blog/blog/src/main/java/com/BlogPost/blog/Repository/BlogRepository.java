package com.BlogPost.blog.Repository;

import com.BlogPost.blog.Entity.Blog;
import com.BlogPost.blog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByUser(User user);

}
