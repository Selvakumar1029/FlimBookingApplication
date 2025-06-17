package com.example.filmbooking.controller;
import com.example.filmbooking.model.*;
import com.example.filmbooking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private MovieRepository movieRepo;
    @Autowired private ShowTimeRepository showTimeRepo;
    @Autowired private SeatRepository seatRepo;
    @Autowired private BookingRepository bookingRepo;
    @Autowired private UserRepository userRepo;

    @GetMapping("/movies")
    public String listMovies(Model model) {
        model.addAttribute("movies", movieRepo.findAll());
        return "admin_movies";
    }
    @GetMapping("/movies/add")
    public String showAddMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin_add_movie";
    }
    @PostMapping("/movies/add")
    public String addMovie(@ModelAttribute Movie movie,
                           @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get("src/main/resources/static/images");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                movie.setImagePath("/images/" + fileName);
            }
            movieRepo.save(movie);
        } catch (IOException e) {
            e.printStackTrace();
            }
        return "redirect:/admin/movies";
    }
    @GetMapping("/movies/edit/{id}")
    public String showEditMovie(@PathVariable("id") Long id, Model model) {
        Movie movie = movieRepo.findById(id).orElse(null);
        if (movie == null) {
            return "redirect:/admin/movies";
        }
        model.addAttribute("movie", movie);
        return "admin_edit_movie";
    }
    @PostMapping("/movies/edit")
    public String editMovie(@ModelAttribute Movie movie,
            @RequestParam("imageFile") MultipartFile imageFile) {
try {
if (!imageFile.isEmpty()) {
 String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
 Path uploadPath = Paths.get("src/main/resources/static/images");
 if (!Files.exists(uploadPath)) {
     Files.createDirectories(uploadPath);
 }
 Path filePath = uploadPath.resolve(fileName);
 Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
 movie.setImagePath("/images/" + fileName);
}
movieRepo.save(movie);
} catch (IOException e) {
e.printStackTrace();
}
return "redirect:/admin/movies";
}
    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id) {
        movieRepo.deleteById(id);
        return "redirect:/admin/movies";
    }
    @GetMapping("/showtimes")
    public String listShowTimes(Model model) {
        List<ShowTime> showTimes = showTimeRepo.findAll();
        model.addAttribute("showTimes", showTimes);
        return "admin_showtimes";
    }
    @GetMapping("/showtimes/add")
    public String showAddShowTime(Model model) {
        model.addAttribute("showTime", new ShowTime());
        model.addAttribute("movies", movieRepo.findAll());
        return "admin_add_showtime";
    }
    @PostMapping("/showtimes/add")
    public String addShowTime(@RequestParam Long movieId,
                              @RequestParam String showTimeDateTime) {
        Movie movie = movieRepo.findById(movieId).orElse(null);
        if (movie == null) {
            return "redirect:/admin/showtimes";
        }
       LocalDateTime dateTime = LocalDateTime.parse(showTimeDateTime);
        ShowTime showTime = new ShowTime(movie, dateTime);
        showTimeRepo.save(showTime);
        String[] rows = {"A","B","C","D","E","F","G","H"};
        int seatsPerRow = 15;
        List<Seat> seats = new ArrayList<>();
        for (String row : rows) {
            for (int i = 1; i <= seatsPerRow; i++) {
                Seat seat = new Seat(row + i, showTime);
                seats.add(seat);
            }
        }
        seatRepo.saveAll(seats);
        return "redirect:/admin/showtimes";
    }
  @GetMapping("/showtimes/delete/{id}")
    public String deleteShowTime(@PathVariable("id") Long id) {
        showTimeRepo.deleteById(id);
        return "redirect:/admin/showtimes";
    }
    @GetMapping("/bookings")
    public String viewAllBookings(Model model) {
        model.addAttribute("bookings", bookingRepo.findAll());
        return "admin_bookings";
    }
}