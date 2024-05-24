package org.example.boardproject.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Board {
    @Id
    private Long id;
    private String name;
    private String title;
    private String content;
    private String password;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;


}
