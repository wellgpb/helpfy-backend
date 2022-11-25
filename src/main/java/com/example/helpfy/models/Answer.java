package com.example.helpfy.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_answers")
public class Answer {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String body;
    @OneToMany
    @JoinColumn(name = "answer_id")
    private List<Comment> comments;
    @ManyToOne
    private User author;
    private int numberLikes;
    private int numberDislikes;
    private Date postDate;
    private boolean solution;
}
