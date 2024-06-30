package com.guardians.repositories;

import com.guardians.entites.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepositories extends JpaRepository<Movie,Long> {
}
