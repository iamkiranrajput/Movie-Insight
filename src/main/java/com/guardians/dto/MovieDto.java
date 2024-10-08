package com.guardians.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovieDto {
    private Integer movieId;

    @NotBlank(message = "Please provide Movie's Title")
    private String title;

    @NotBlank(message = "Please provide Movie's director")
    private String director;

    @NotBlank(message = "Please provide Movie's studio")
    private String studio;

    private Set<String> movieCast;
    private Integer releaseYear;

    @NotBlank(message = "Please provide Movie's poster")
    private String poster;

    @NotBlank(message = "Please provide poster url")
    private String posterUrl;


}
