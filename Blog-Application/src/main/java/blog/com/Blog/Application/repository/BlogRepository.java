package blog.com.Blog.Application.repository;

import blog.com.Blog.Application.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {



    @Query(value = "SELECT * FROM blogs WHERE id =:id ",nativeQuery = true)
    Blog getBlogFromDBUsingId(@Param("id") Long id);
}