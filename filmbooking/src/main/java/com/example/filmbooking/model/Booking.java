package com.example.filmbooking.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;
    private LocalDateTime bookingTime;
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
    public Booking() {}
    public Booking(User user, ShowTime showTime, LocalDateTime bookingTime) {
        this.user = user;
        this.showTime = showTime;
        this.bookingTime = bookingTime;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ShowTime getShowTime() {
		return showTime;
	}
	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}
	public LocalDateTime getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
