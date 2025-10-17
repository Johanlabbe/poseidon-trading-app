package com.poseidon.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rules")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Rule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String json;
    @Column(columnDefinition = "TEXT")
    private String template;
    @Column(columnDefinition = "TEXT")
    private String sqlStr;
    @Column(columnDefinition = "TEXT")
    private String sqlPart;
}
