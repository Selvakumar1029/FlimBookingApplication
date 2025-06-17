
package com.example.filmbooking.repository;

import com.example.filmbooking.model.Booking;
import com.example.filmbooking.model.User;
import com.example.filmbooking.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
       List<Booking> findByUser(User user);
       List<Booking> findByShowTime(ShowTime showTime);
}