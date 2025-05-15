package blog.com.Blog.Application.controller.userController;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.service.userService.BlogsData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {
        "http://localhost:5500",
        "http://127.0.0.1:5500",
        "http://localhost:5501",
        "http://127.0.0.1:5501"
})
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BlogsData blogsData;

    @GetMapping("/blogs") // http://localhost:8080/user/blogs
    public ResponseEntity<?> getBlogs() {
        try {
            logger.info("Fetching all blogs for user...");
            List<Blog> getAllBlogs = blogsData.getAllBlogsFromDB();
            logger.info("Successfully fetched {} blogs.", getAllBlogs.size());
            return new ResponseEntity<>(getAllBlogs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching blogs: {}", e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching blogs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/blogs/{id}")  // http://localhost:8080/user/blogs/{id}
    public ResponseEntity<?> getBlogsById(@PathVariable Long id) {
        try {
            logger.info("Fetching blog with ID: {}", id);
            Optional<Blog> getBlogByID = blogsData.getAllBlogByIdFromDB(id);
            if (getBlogByID.isPresent()) {
                logger.info("Blog found with ID: {}", id);
                return new ResponseEntity<>(getBlogByID, HttpStatus.OK);
            } else {
                logger.warn("No blog found with ID: {}", id);
                return new ResponseEntity<>("No blog found with the given ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while fetching blog by ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching the blog.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
