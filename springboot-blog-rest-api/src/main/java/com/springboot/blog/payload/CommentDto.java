package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be null or empty")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body should have at least 10 characters")
    private String body;
}
