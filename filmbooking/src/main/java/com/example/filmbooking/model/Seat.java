package com.example.filmbooking.model;
import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    private boolean booked = false;
    @ManyToOne
    @JoinColumn(name = "show_time_id")
    private ShowTime showTime;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public boolean isBooked() {
		return booked;
	}
	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	public ShowTime getShowTime() {
		return showTime;
	}
	public void setShowTime(ShowTime showTime) {
		this.showTime = showTime;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    public Seat() {}
    public Seat(String seatNumber, ShowTime showTime) {
        this.seatNumber = seatNumber;
        this.showTime = showTime;
    }
}
