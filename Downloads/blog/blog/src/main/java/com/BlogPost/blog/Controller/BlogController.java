package com.BlogPost.blog.Controller;

import com.BlogPost.blog.Entity.Blog;
import com.BlogPost.blog.Entity.User;
import com.BlogPost.blog.Service.BlogService;
import com.BlogPost.blog.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }


    @PostMapping
    public ResponseEntity<Map<String, String>> createBlog(@RequestBody Blog blog, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("❌ Session is NULL - User is not logged in.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Unauthorized: Please log in"));
        }

        Object userIdObj = session.getAttribute("userId");

        if (userIdObj == null) {
            System.out.println("❌ userId is NULL in session.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Unauthorized: Please log in"));
        }

        Long userId = (Long) userIdObj;
        System.out.println("✅ Session UserID: " + userId);

        blog.setUser(userService.getUserById(userId)); // Assign blog to user
        blogService.createBlog(blog);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Blog created successfully");
        return ResponseEntity.ok(response);
    }




    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Blog updatedBlog = blogService.updateBlog(id, blog, user);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        blogService.deleteBlog(id, user);
        return ResponseEntity.ok("Blog deleted successfully!");
    }

}
