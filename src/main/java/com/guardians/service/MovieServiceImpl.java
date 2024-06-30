package com.guardians.service;

import com.guardians.dto.MovieDto;
import com.guardians.entites.Movie;
import com.guardians.repositories.MovieRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Value("${project.poster}")
    private String path;
    private final FileService fileService;
    private final MovieRepositories movieRepository;

    @Value("${base.url}")
    private String baseUrl;


    public MovieServiceImpl(FileService fileService, MovieRepositories movieRepository) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
    }




    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //upload the file
       String uploadFileName = fileService.uploadFile(path, file);

        //set the value of poster as filename
        movieDto.setPoster(uploadFileName);

        //map dto to movie object
        Movie movie = new Movie(
                movieDto.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
                );

        //to save the movie object
        Movie savedMovie = (Movie) movieRepository.save(movie);

        //generate the posterUrl
        String posterUrl= baseUrl+"/file/"+uploadFileName;

        //map movie object to dto object and return it.
        MovieDto response=new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        return null;
    }

    @Override
    public List<MovieDto> getAllMovies() {
        return null;
    }
}
