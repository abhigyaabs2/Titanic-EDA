package com.BlogPost.blog.Service;

import com.BlogPost.blog.Entity.Blog;
import com.BlogPost.blog.Entity.User;
import com.BlogPost.blog.Repository.BlogRepository;
import com.BlogPost.blog.Repository.UserRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Transactional
    public Blog createBlog(Blog blog) {
        if (blog.getUser() == null || blog.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        // Ensure user exists
        User existingUser = userRepository.findById(blog.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        blog.setUser(existingUser); // Associate blog with the existing user

        return blogRepository.save(blog);
    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public Blog updateBlog(Long id, Blog blog, User user) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        // Ensure only the owner can update
        if (!existingBlog.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this blog");
        }

        existingBlog.setTitle(blog.getTitle());
        existingBlog.setContent(blog.getContent());
        existingBlog.setUpdatedAt(blog.getUpdatedAt());

        return blogRepository.save(existingBlog);
    }

    @Transactional
    public void deleteBlog(Long id, User user) {
        Blog existingBlog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        // Ensure only the owner can delete
        if (!existingBlog.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this blog");
        }

        blogRepository.delete(existingBlog);
    }
}
