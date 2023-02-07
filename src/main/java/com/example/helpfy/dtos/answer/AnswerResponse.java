package com.example.helpfy.dtos.answer;

import com.example.helpfy.dtos.user.UserResponse;
import com.example.helpfy.models.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class AnswerResponse {
    private Long id;
    private String body;
    private UserResponse author;
    private List<Comment> comments;
    private int numberLikes;
    private int numberDislikes;
    private Set<Long> likes;
    private Set<Long> dislikes;
    private Date postDate;
    private Boolean solution;
}