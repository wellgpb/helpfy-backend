package com.example.helpfy.dtos.question;

import com.example.helpfy.dtos.answer.AnswerResponse;
import com.example.helpfy.dtos.user.UserResponse;
import com.example.helpfy.models.Answer;
import com.example.helpfy.models.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class QuestionResponse {
    private Long id;
    private String body;
    private UserResponse author;
    private List<AnswerResponse> answers;
    private List<Comment> comments;
    private Set<String> tags;
    private String title;
    private int numberLikes;
    private int numberDislikes;
    private Date createdAt;
    private boolean answered;
}
