package com.example.filmbooking.model;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String title;
	@Column(name = "image")
	 private String imagePath;
	    public String getImagePath() {
			return imagePath;
		}
		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
    private String description;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowTime> showTimes = new ArrayList<>();
    public Movie() {}
    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ShowTime> getShowTimes() {
		return showTimes;
	}
	public void setShowTimes(List<ShowTime> showTimes) {
		this.showTimes = showTimes;
	}
}
