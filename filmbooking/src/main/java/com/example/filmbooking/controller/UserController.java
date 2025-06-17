package com.example.filmbooking.controller;
import com.example.filmbooking.model.*;
import com.example.filmbooking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private MovieRepository movieRepo;
    @Autowired private ShowTimeRepository showTimeRepo;
    @Autowired private SeatRepository seatRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;

   @GetMapping("/home")
    public String userHome(Model model) {
        List<Movie> movies = movieRepo.findAll();
        model.addAttribute("movies", movies);
        return "user_home";
    }
    @GetMapping("/movie/{movieId}")
    public String showShowtimes(@PathVariable("movieId") Long movieId, Model model) {
        Movie movie = movieRepo.findById(movieId).orElse(null);
        if (movie == null) {
            return "redirect:/user/home";
        }
        List<ShowTime> showTimes = showTimeRepo.findByMovie(movie);
        model.addAttribute("movie", movie);
        model.addAttribute("showTimes", showTimes);
        return "showtimes";
    }
    @GetMapping("/book/{showTimeId}")
    public String showSeatSelection(@PathVariable("showTimeId") Long showTimeId, Model model) {
        ShowTime showTime = showTimeRepo.findById(showTimeId).orElse(null);
        if (showTime == null) {
            return "redirect:/user/home";
        }
        List<Seat> seats = seatRepo.findByShowTimeAndBookedFalse(showTime);
        model.addAttribute("showTime", showTime);
        model.addAttribute("seats", seats);
        return "seats";
    }
   @PostMapping("/book")
    public String bookTickets(@RequestParam("showTimeId") Long showTimeId,
                              @RequestParam("seatIds") List<Long> seatIds,
                              @AuthenticationPrincipal UserDetails userDetails) {
        ShowTime showTime = showTimeRepo.findById(showTimeId).orElse(null);
        if (showTime == null || seatIds == null || seatIds.isEmpty()) {
            return "redirect:/user/home";
        }
      User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
       Booking booking = new Booking(user, showTime, LocalDateTime.now());
        bookingRepo.save(booking);
        for (Long seatId : seatIds) {
            Seat seat = seatRepo.findById(seatId).orElse(null);
            if (seat != null && !seat.isBooked()) {
                seat.setBooked(true);
                seat.setBooking(booking);
                seatRepo.save(seat);
            }
        }
        return "redirect:/user/bookings";
    }
    @GetMapping("/bookings")
    public String viewMyBookings(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        List<Booking> bookings = bookingRepo.findByUser(user);
        model.addAttribute("bookings", bookings);
        return "user_bookings";
    }
}