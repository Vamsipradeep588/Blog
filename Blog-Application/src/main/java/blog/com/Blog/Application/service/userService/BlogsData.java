package blog.com.Blog.Application.service.userService;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BlogsData {

    private static final Logger logger = LoggerFactory.getLogger(BlogsData.class);

    @Autowired
    private BlogRepository blogRepository;

    public List<Blog> getAllBlogsFromDB() {
        try {
            logger.info("Fetching all blogs from database...");
            List<Blog> blogs = blogRepository.findAll();
            logger.info("Fetched {} blogs successfully.", blogs.size());
            return blogs;
        } catch (Exception e) {
            logger.error("Failed to fetch blogs from database: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Optional<Blog> getAllBlogByIdFromDB(Long id) {
        try {
            logger.info("Fetching blog with ID: {}", id);
            Optional<Blog> blog = blogRepository.findById(id);
            if (blog.isPresent()) {
                logger.info("Blog found with ID: {}", id);
            } else {
                logger.warn("No blog found with ID: {}", id);
            }
            return blog;
        } catch (Exception e) {
            logger.error("Error while fetching blog with ID {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }
}
