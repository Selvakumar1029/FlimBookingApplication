package com.example.filmbooking.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "show_times")
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    private LocalDateTime showTime;
    @OneToMany(mappedBy = "showTime", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
   public ShowTime() {}
    public ShowTime(Movie movie, LocalDateTime showTime) {
        this.movie = movie;
        this.showTime = showTime;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public LocalDateTime getShowTime() {
		return showTime;
	}
	public void setShowTime(LocalDateTime showTime) {
		this.showTime = showTime;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
