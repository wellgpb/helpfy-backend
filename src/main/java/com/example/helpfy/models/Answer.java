package com.example.helpfy.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @ElementCollection
    private Set<Long> idsFromUsersLikes;
    @ElementCollection
    private Set<Long> idsFromUsersDislikes;
    private Date postDate;
    private boolean solution;
}
