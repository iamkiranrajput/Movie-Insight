package com.guardians.service;

import com.guardians.dto.MovieDto;
import com.guardians.entites.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();


    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException;

    String deleteMovie(Integer id) throws IOException;

}
