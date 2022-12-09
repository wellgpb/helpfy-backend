package com.example.helpfy.dtos.answer;

import com.example.helpfy.dtos.user.UserMapper;
import com.example.helpfy.models.Answer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class AnswerMapper {
    private final UserMapper userMapper;

    public AnswerMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AnswerResponse fromAnswer(Answer answer) {
        var author = userMapper.fromUserToResponse(answer.getAuthor());

        return AnswerResponse.builder()
                .body(answer.getBody())
                .postDate(answer.getPostDate())
                .id(answer.getId())
                .comments(answer.getComments())
                .numberLikes(answer.getNumberLikes())
                .numberDislikes(answer.getNumberDislikes())
                .solution(answer.isSolution())
                .author(author)
                .build();
    }

    public Answer toAnswerPOST(AnswerRequest answerRequest) {
        return Answer.builder()
                .comments(new ArrayList<>())
                .body(answerRequest.getBody())
                .postDate(new Date())
                .numberLikes(0)
                .numberDislikes(0)
                .solution(false)
                .build();
    }
}
