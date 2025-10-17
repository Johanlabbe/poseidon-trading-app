package com.poseidon.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "curve_points")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CurvePoint {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer curveId;
    private LocalDateTime asOfDate;
    private Double term;
    @Column(name = "value_")
    private Double value;
    private LocalDateTime creationDate;
}
