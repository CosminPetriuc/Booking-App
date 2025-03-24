package com.example.homies.demo.model.hotel;

import com.example.homies.demo.model.dto.ReviewRequestDTO;
import com.example.homies.demo.model.user.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;


@Data
@NoArgsConstructor
@Entity(name = "Review")
@Table(name = "reviews")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reviewId")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviw_id")
    private Long reviewId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="description")
    private String description;

    @Column(name="rating")
    private float rating;

    public Review(Long reviewId, Hotel hotel, User user, String description, float rating) {
        this.reviewId = reviewId;
        this.hotel = hotel;
        this.user = user;
        this.description = description;
        this.rating = rating;
    }
    public Review(Hotel hotel, User user, ReviewRequestDTO reviewRequestDTO){
        this.setHotel(hotel);
        this.setUser(user);
        this.setDescription(reviewRequestDTO.getDescription());
        this.setRating(reviewRequestDTO.getRating());
    }

}
