package com.example.helpfy.dtos.question;

import com.example.helpfy.dtos.user.UserMapper;
import com.example.helpfy.models.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

@Component
public class QuestionMapper {

    private final UserMapper userMapper;

    public QuestionMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public QuestionResponse fromQuestionToResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .author(userMapper.fromUserToResponse(question.getAuthor()))
                .title(question.getTitle())
                .body(question.getBody())
                .tags(question.getTags())
                .numberLikes(question.getIdsFromUsersLikes().size())
                .numberDislikes(question.getIdsFromUsersDislikes().size())
                .createdAt(question.getCreatedAt())
                .answered(question.isAnswered())
                .answers(question.getAnswers())
                .comments(question.getComments())
                .build();
    }

    public Question fromRequestToQuestion(QuestionRequest questionRequest) {
        return Question.builder()
                .title(questionRequest.getTitle())
                .body(questionRequest.getBody())
                .tags(questionRequest.getTags())
                .idsFromUsersLikes(new HashSet<>())
                .idsFromUsersDislikes(new HashSet<>())
                .createdAt(new Date())
                .answered(false)
                .answers(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    public Question toQuestionPUT(QuestionRequestPUT questionRequestPUT) {
        return Question.builder()
                .title(questionRequestPUT.getTitle())
                .body(questionRequestPUT.getBody())
                .tags(questionRequestPUT.getTags())
                .build();
    }

}
