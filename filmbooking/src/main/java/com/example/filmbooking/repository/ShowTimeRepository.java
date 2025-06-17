
package com.example.filmbooking.repository;

import com.example.filmbooking.model.ShowTime;
import com.example.filmbooking.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShowTimeRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findByMovie(Movie movie);
}
