package com.example.helpfy.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_questions")
public class Question {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String body;
    @ManyToOne
    private User author;
    @OneToMany
    @JoinColumn(name = "question_id")
    private List<Answer> answers;
    @OneToMany
    @JoinColumn(name = "question_id")
    private List<Comment> comments;
    @ElementCollection
    private Set<String> tags;
    private String title;
    private int numberLikes;
    private int numberDislikes;
    private Date createdAt;
    private boolean answered;
}
