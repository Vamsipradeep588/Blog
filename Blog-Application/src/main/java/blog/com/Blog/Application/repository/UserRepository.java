package blog.com.Blog.Application.repository;


import blog.com.Blog.Application.model.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<BlogUser, Long> {


}