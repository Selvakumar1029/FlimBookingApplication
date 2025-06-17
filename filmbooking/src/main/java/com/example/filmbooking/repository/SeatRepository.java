
package com.example.filmbooking.repository;

import com.example.filmbooking.model.Seat;
import com.example.filmbooking.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByShowTimeAndBookedFalse(ShowTime showTime);
}
