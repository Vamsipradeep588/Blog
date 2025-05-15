package blog.com.Blog.Application.service.adminService;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.repository.BlogRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(Transactional.TxType.REQUIRED)

public class AdminService {

    @Autowired
    private BlogRepository blogRepository;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

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

    public Optional<Blog> getBlogByIdFromDB(Long id) {
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

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = {IOException.class})
    public Blog getBlogFromDBUsingId(Long id) {
        try {
            Blog getBlog = blogRepository.getBlogFromDBUsingId(id);
            if (getBlog != null) {
                return getBlog;
            } else {
                logger.warn("Blog not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error while fetching blog: {}", e.getMessage(), e);
            return null;
        }
    }

    public Blog saveBlog(Blog addBlogFromAdmin) {
        try {
            return blogRepository.save(addBlogFromAdmin);
        } catch (Exception e) {
            logger.error("Error while saving blog: {}", e.getMessage(), e);
            throw new RuntimeException("Error while saving blog", e);
        }
    }

    public boolean deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
