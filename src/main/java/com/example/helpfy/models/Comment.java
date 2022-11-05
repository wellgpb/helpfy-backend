package com.example.helpfy.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity @Table(name = "tb_comment")
public class Comment {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String body;
    @ManyToOne
    private User author;
    private Date postDate;
}
