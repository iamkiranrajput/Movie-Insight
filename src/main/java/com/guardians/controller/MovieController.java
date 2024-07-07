package com.guardians.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guardians.AppConstant;
import com.guardians.dto.MovieDto;
import com.guardians.dto.MoviePageResponse;
import com.guardians.exception.EmptyFileException;
import com.guardians.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    @Autowired
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }





    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add-movielist")
    public ResponseEntity<String> addMovieListHandler(@RequestBody List<MovieDto> movieDtoList) throws IOException {
        return  new ResponseEntity<>(movieService.addMovieList(movieDtoList),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException, EmptyFileException {

        if(file.isEmpty()) {
                throw new EmptyFileException("File is empty! please add a file");
        }
        MovieDto dto = convertToMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }
    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();
        MovieDto movieDto = objectMapper.readValue(movieDtoObj, MovieDto.class);
        return  movieDto;
    }


    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovieHandler(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId, @RequestPart MultipartFile file, @RequestPart String movieDtoObj) throws IOException {

        if(file.isEmpty())file = null;
        MovieDto movieDto = convertToMovieDto(movieDtoObj);
        return ResponseEntity.ok((movieService.updateMovie(movieId,movieDto,file)));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }


    @GetMapping("/allMoviePages")
    public ResponseEntity<MoviePageResponse> getMoviesWithPagination(@RequestParam(defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber, @RequestParam(defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize){

        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber,pageSize));

    }

    @GetMapping("/allMoviePagesSort")
    public ResponseEntity<MoviePageResponse> getMoviesWithPaginationAndSort(@RequestParam(defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber, @RequestParam(defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize, @RequestParam(defaultValue = AppConstant.SORT_DIR, required = false) String dir, @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy){

        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir));

    }
}
