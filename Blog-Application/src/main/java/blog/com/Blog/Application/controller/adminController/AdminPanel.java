package blog.com.Blog.Application.controller.adminController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.service.adminService.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = {
        "http://localhost:5500",
        "http://127.0.0.1:5500",
        "http://localhost:5501",
        "http://127.0.0.1:5501"
})
@RequestMapping("/admin")
public class AdminPanel {

    @Autowired
    private AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminPanel.class);

    @GetMapping("/blogs") // http://localhost:8080/admin/blogs
    public ResponseEntity<?> getBlogs() {
        try {
            logger.info("Fetching all blogs for user...");
            List<Blog> getAllBlogs = adminService.getAllBlogsFromDB();
            logger.info("Successfully fetched {} blogs.", getAllBlogs.size());
            return new ResponseEntity<>(getAllBlogs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching blogs: {}", e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching blogs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/blogs/{id}") // http://localhost:8080/admin/blogs/1
    public ResponseEntity<?> getBlogsById(@PathVariable Long id) {
        try {
            logger.info("Fetching blog with ID: {}", id);
            Optional<Blog> getBlogByID = adminService.getBlogByIdFromDB(id);
            if (getBlogByID.isPresent()) {
                logger.info("Blog found with ID: {}", id);
                return new ResponseEntity<>(getBlogByID, HttpStatus.OK);
            } else {
                logger.warn("No blog found with ID: {}", id);
                return new ResponseEntity<>("No blog found with the given ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while fetching blog by ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching the blog.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addBlog") // http://localhost:8080/admin/addBlog

    public ResponseEntity<String> addBlogToDB(@RequestPart Blog addBlogFromAdmin,
            @RequestPart MultipartFile imageFile) {
        logger.info("Received request to add a blog: {}", addBlogFromAdmin.getTitle());

        try {
            addBlogFromAdmin.setImageName(imageFile.getOriginalFilename());
            addBlogFromAdmin.setImageType(imageFile.getContentType());
            addBlogFromAdmin.setImageData(imageFile.getBytes());
            addBlogFromAdmin.setCreatedAt(new Date());

            adminService.saveBlog(addBlogFromAdmin);

            logger.info("Blog added successfully with title: {}", addBlogFromAdmin.getTitle());
            return new ResponseEntity<>("Blog added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while adding blog: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to add blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/blogs/{id}")  http://localhost:8080/admin/blogs/1
    public ResponseEntity<String> updateBlogToDB(
            @PathVariable Long id,
            @RequestPart("blogUpdateFromAdmin") Blog blogUpdateFromAdmin,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        logger.info("Received request to update blog with ID: {}", id);

        try {
            Blog existingBlog = adminService.getBlogFromDBUsingId(id);
            if (existingBlog == null) {
                logger.warn("Blog not found with ID: {}", id);
                return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
            }

            existingBlog.setTitle(blogUpdateFromAdmin.getTitle());
            existingBlog.setContent(blogUpdateFromAdmin.getContent());
            existingBlog.setCategory(blogUpdateFromAdmin.getCategory());
            existingBlog.setCreatedAt(new Date());

            // If a new image is uploaded
            if (imageFile != null && !imageFile.isEmpty()) {
                existingBlog.setImageName(imageFile.getOriginalFilename());
                existingBlog.setImageType(imageFile.getContentType());
                existingBlog.setImageData(imageFile.getBytes());
            }

            adminService.saveBlog(existingBlog);
            logger.info("Blog updated successfully with ID: {}", id);
            return new ResponseEntity<>("Blog updated successfully!", HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error while updating blog: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to update blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/blogs/{id}")  // http://localhost:8080/admin/blogs/1
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        boolean deleted = adminService.deleteBlog(id);
        if (deleted) {
            return ResponseEntity.ok("Blog deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
        }
    }
}
