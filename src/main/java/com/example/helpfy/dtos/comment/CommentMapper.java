package com.example.helpfy.dtos.comment;


import com.example.helpfy.dtos.user.UserMapper;

import com.example.helpfy.dtos.user.UserResponse;
import com.example.helpfy.models.Comment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CommentMapper {
    private UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Comment toCommentPOST(CommentRequest commentRequest){
        return Comment.builder()
                .body(commentRequest.getContent())
                .postDate(new Date())
                .build();
    }

    public CommentResponse fromComment(Comment commentResult) {
        UserResponse author = userMapper.fromUserToResponse(commentResult.getAuthor());
        return CommentResponse.builder()
                .id(commentResult.getId())
                .content(commentResult.getBody())
                .createdAt(commentResult.getPostDate())
                .author(author)
                .build();
    }
    public Comment toCommentPUT(CommentRequest commentRequest) {
        return Comment.builder()
               .body(commentRequest.getContent())
                .build();
    }
}
