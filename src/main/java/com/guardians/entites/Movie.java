package com.guardians.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import javax.annotation.processing.Generated;
import java.util.Set;


@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "Please provide Movie's Title")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Please provide Movie's director")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Please provide Movie's studio")
    private String studio;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Please provide Movie's poster")
    private String poster;


}
