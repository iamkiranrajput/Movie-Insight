package com.guardians.service;

import com.guardians.dto.MovieDto;
import com.guardians.dto.MoviePageResponse;
import com.guardians.entites.Movie;
import com.guardians.exception.FileExistsException;
import com.guardians.exception.MovieNotFoundException;
import com.guardians.repositories.MovieRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Value("${project.poster}")
    private String path;
    private final FileService fileService;

    @Autowired
    private final MovieRepositories movieRepository;

    @Value("${base.url}")
    private String baseUrl;


    public MovieServiceImpl(FileService fileService, MovieRepositories movieRepository) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
    }

    @Override
    public String addMovieList(List<MovieDto> listMovieDto) throws IOException {
        String file = "default.jpg";
        ArrayList<Movie> movieList = new ArrayList<>();

        // Set the value of poster as filename and convert MovieDto to Movie
        for (MovieDto movieDto : listMovieDto) {
            movieDto.setPoster(file);
            Movie movie = new Movie(
                    null, // movieId should be null since it's auto-generated
                    movieDto.getTitle(),
                    movieDto.getDirector(),
                    movieDto.getStudio(),
                    movieDto.getMovieCast(),
                    movieDto.getReleaseYear(),
                    movieDto.getPoster()
            );
            movieList.add(movie);
        }
        movieRepository.saveAll(movieList);

        return "List of Movies Saved";
    }



    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //upload the file
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new FileExistsException("File already exists! please enter another file name. ");
        }


       String uploadFileName = fileService.uploadFile(path, file);

        //set the value of poster as filename
        movieDto.setPoster(uploadFileName);

        //map dto to movie object
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
                );

        //to save the movie object
        Movie savedMovie = movieRepository.save(movie);

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

        Movie movie= movieRepository.findById(Long.valueOf(movieId)).orElseThrow(()->new MovieNotFoundException("Movie not found with ID "+movieId));
//        getting file

        String posterUrl = baseUrl+"/file/"+movie.getPoster();

        MovieDto response = new MovieDto( movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl);

        return response;
    }

    @Override
    public List<MovieDto> getAllMovies() {

//        Fetch all movie data
            List <Movie> movies = movieRepository.findAll();
            List <MovieDto> movieDtos = new ArrayList<>();
            
//        Iterating through the list and generating poster url for each movie obj
        for (Movie movie: movies) {
            String posterUrl = baseUrl+"/file/"+movie.getPoster();
            MovieDto response = new MovieDto( movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl);

            movieDtos.add(response);
        }


//        and map to movie dto object
        return movieDtos ;

    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
//       1. check movie object exist or not with given object
       Movie mv = movieRepository.findById(Long.valueOf(movieId)).orElseThrow(()->new MovieNotFoundException("Movie not found with ID "+movieId));

//       2. if file is null, then do nothing with file service if file is not null then delete the existing file associated with the record and upload the new record
            String fileName = mv.getPoster();

            if(file !=null){
                Files.deleteIfExists(Paths.get(path + File.separator + fileName));
                fileName = fileService.uploadFile(path,file);
            }

//       3. set movieDtos  poster value according to steps
            movieDto.setPoster(fileName);

//       4. map it to movie object
            Movie movie = new Movie(
                    mv.getMovieId(),
                    movieDto.getTitle(),
                    movieDto.getDirector(),
                    movieDto.getStudio(),
                    movieDto.getMovieCast(),
                    movieDto.getReleaseYear(),
                    movieDto.getPoster()
            );
           Movie updatedMovie= movieRepository.save(movie);
//       5.  save the object->return saved movie object
            String posterUrl = baseUrl+"/file/"+fileName;
//         generate poster url for it
        MovieDto response = new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );



        return response;


    }

    @Override
    public String deleteMovie(Integer id) throws IOException {
        //check if move object exist or not
       Movie mv= movieRepository.findById(Long.valueOf(id)).orElseThrow(()->new MovieNotFoundException("Movie not found with ID "+id));
        //delete the file associated with object
        Files.deleteIfExists(Paths.get(path + File.separator + mv.getPoster()));
        //delete the movie object
        movieRepository.delete(mv);
        return "Movie Deleted with id = "+id;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageNumber,pageSize);

        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();


        List<MovieDto> movieDtos=new ArrayList<>();

        for (Movie movie: movies) {
            String posterUrl = baseUrl+"/file/"+movie.getPoster();
            MovieDto response = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl);

            movieDtos.add(response);
        }
        return new MoviePageResponse(movieDtos,pageNumber,pageSize, moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast() );
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();


        PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Movie> moviePages = movieRepository.findAll(pageable);
        List<Movie> movies = moviePages.getContent();


        List<MovieDto> movieDtos=new ArrayList<>();

        for (Movie movie: movies) {
            String posterUrl = baseUrl+"/file/"+movie.getPoster();
            MovieDto response = new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl);

            movieDtos.add(response);
        }

        return new MoviePageResponse(movieDtos,pageNumber,pageSize, moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast() );
    }
}
