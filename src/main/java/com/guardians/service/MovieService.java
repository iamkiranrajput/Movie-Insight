package com.guardians.service;

import com.guardians.dto.MovieDto;
import com.guardians.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    String addMovieList(List<MovieDto> listMovieDto) throws IOException;

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    MovieDto getMovie(Integer movieId);
    List<MovieDto> getAllMovies();
    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException;
    String deleteMovie(Integer id) throws IOException;
    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);
    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber,Integer pageSize, String sortBy, String dir);


}
