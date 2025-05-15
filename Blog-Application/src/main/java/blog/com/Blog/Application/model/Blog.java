package blog.com.Blog.Application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    @Column(length = 10000)
    private String content;

    @NotBlank(message = "Category is required")
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String imageName;
    private String imageType;

    @Lob
    @Basic(fetch = FetchType.EAGER) // add this
    private byte[] imageData;


    public Blog() {}

    public Blog(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Blog(String imageName, String imageType, byte[] imageData) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }

    public Blog(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getImageType() { return imageType; }
    public void setImageType(String imageType) { this.imageType = imageType; }

    public byte[] getImageData() { return imageData; }
    public void setImageData(byte[] imageData) { this.imageData = imageData; }
}
