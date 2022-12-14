package com.example.helpfy.dtos.comment;

import com.example.helpfy.dtos.user.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Date createdAt;
    private UserResponse author;
}
