package com.example.helpfy.dtos.question;

import com.example.helpfy.dtos.answer.AnswerMapper;
import com.example.helpfy.dtos.answer.AnswerResponse;
import com.example.helpfy.dtos.user.UserMapper;
import com.example.helpfy.models.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    private final UserMapper userMapper;
    private final AnswerMapper answerMapper;

    public QuestionMapper(UserMapper userMapper, AnswerMapper answerMapper) {
        this.userMapper = userMapper;
        this.answerMapper = answerMapper;
    }

    public QuestionResponse fromQuestionToResponse(Question question) {
        var author = userMapper.fromUserToResponse(question.getAuthor());
        List<AnswerResponse> answers = null;
        if (question.getAnswers() != null) {
            answers = question.getAnswers().stream()
                    .map(answerMapper::fromAnswer)
                    .collect(Collectors.toList());
        }

        return QuestionResponse.builder()
                .id(question.getId())
                .author(author)
                .title(question.getTitle())
                .body(question.getBody())
                .tags(question.getTags())
                .numberLikes(question.getIdsFromUsersLikes().size())
                .numberDislikes(question.getIdsFromUsersDislikes().size())
                .createdAt(question.getCreatedAt())
                .answered(question.isAnswered())
                .answers(answers)
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
