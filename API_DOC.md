# AdminPanel API Documentation

**Base URL:** `http://localhost:8080/admin`

---

## Endpoints

### 1. Get All Blogs

- **URL:** `/blogs`
- **Method:** `GET`
- **Description:** Retrieves all blog entries.

#### Success Response

- **Code:** `200 OK`
- **Content:**  
  ```json
  [
    {
      "id": 1,
      "title": "First Blog",
      "content": "This is my first blog post.",
      "category": "Technology",
      "imageName": "blog1.png",
      "imageType": "image/png",
      "createdAt": "2025-05-01T14:30:00Z"
    },
    {
      "id": 2,
      "title": "Second Blog",
      "content": "Second blog content.",
      "category": "Science",
      "imageName": "blog2.jpg",
      "imageType": "image/jpeg",
      "createdAt": "2025-05-05T10:15:00Z"
    }
  ]
  ```

#### Error Response

- **Code:** `500 Internal Server Error`
- **Content:**  
  ```
  Something went wrong while fetching blogs
  ```

---

### 2. Get Blog By ID

- **URL:** `/blogs/{id}`
- **Method:** `GET`
- **Description:** Retrieves a single blog post by its ID.
- **URL Params:**  
  - `id` (integer, required): Blog ID

#### Success Response

- **Code:** `200 OK`
- **Content:**  
  ```json
  {
    "id": 1,
    "title": "First Blog",
    "content": "This is my first blog post.",
    "category": "Technology",
    "imageName": "blog1.png",
    "imageType": "image/png",
    "createdAt": "2025-05-01T14:30:00Z"
  }
  ```

#### Error Responses

- **Code:** `404 Not Found`  
  **Content:** `"No blog found with the given ID"`
- **Code:** `500 Internal Server Error`  
  **Content:** `"Something went wrong while fetching the blog."`

---

### 3. Add New Blog

- **URL:** `/addBlog`
- **Method:** `POST`
- **Description:** Adds a new blog post with an image.
- **Request Headers:**  
  - `Content-Type: multipart/form-data`
- **Request Body:**  
  - `addBlogFromAdmin` (text): JSON string of blog data  
  - `imageFile` (file): Image file to upload

#### Example JSON for `addBlogFromAdmin`

```json
{
  "title": "My First Blog",
  "content": "This is the content of my first blog post.",
  "category": "Technology"
}
```

#### Success Response

- **Code:** `201 Created`
- **Content:** `"Blog added successfully!"`

#### Error Response

- **Code:** `500 Internal Server Error`
- **Content:** `"Failed to add blog"`

#### Postman Setup

- Method: `POST`
- URL: `http://localhost:8080/admin/addBlog`
- Under **Body** tab, choose **form-data**
  - Key: `addBlogFromAdmin` (type: Text), paste JSON string
  - Key: `imageFile` (type: File), upload an image file

---

### 4. Update Blog By ID

- **URL:** `/blogs/{id}`
- **Method:** `PUT`
- **Description:** Updates a blog post, optionally updating the image.
- **URL Params:**  
  - `id` (integer, required): Blog ID
- **Request Headers:**  
  - `Content-Type: multipart/form-data`
- **Request Body:**  
  - `blogUpdateFromAdmin` (text): JSON string of updated blog data  
  - `imageFile` (file, optional): New image file

#### Example JSON for `blogUpdateFromAdmin`

```json
{
  "title": "Updated Blog Title",
  "content": "Updated content for the blog post.",
  "category": "Science"
}
```

#### Success Response

- **Code:** `200 OK`
- **Content:** `"Blog updated successfully!"`

#### Error Responses

- **Code:** `404 Not Found`  
  **Content:** `"Blog not found"`
- **Code:** `500 Internal Server Error`  
  **Content:** `"Failed to update blog"`

#### Postman Setup

- Method: `PUT`
- URL: `http://localhost:8080/admin/blogs/{id}` (replace `{id}` with actual ID)
- Under **Body** tab, choose **form-data**
  - Key: `blogUpdateFromAdmin` (type: Text), paste JSON string
  - Key: `imageFile` (type: File), upload new image file (optional)

---

### 5. Delete Blog By ID

- **URL:** `/blogs/{id}`
- **Method:** `DELETE`
- **Description:** Deletes a blog post by ID.
- **URL Params:**  
  - `id` (integer, required): Blog ID
- **Request Body:** None

#### Success Response

- **Code:** `200 OK`
- **Content:** `"Blog deleted successfully!"`

#### Error Response

- **Code:** `404 Not Found`
- **Content:** `"Blog not found"`

---

## Notes

- The `createdAt` field is automatically set when creating or updating blogs.
- Image files should be typical image formats (PNG, JPEG).
- Make sure to include `Content-Type: multipart/form-data` for POST and PUT requests involving files.
- All timestamps are in ISO 8601 format.
