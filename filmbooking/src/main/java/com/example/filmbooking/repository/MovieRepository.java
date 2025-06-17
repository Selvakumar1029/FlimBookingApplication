package com.example.filmbooking.repository;

import com.example.filmbooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> { }
