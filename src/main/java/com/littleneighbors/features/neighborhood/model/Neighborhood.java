package com.littleneighbors.features.neighborhood.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "neighborhoods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Neighborhood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotNull(message = "Is a required field")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Is a required field")
    private String streetName;
}
