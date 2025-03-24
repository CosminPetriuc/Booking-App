package com.example.homies.demo.service;

import com.example.homies.demo.model.dto.ReviewRequestDTO;
import com.example.homies.demo.model.hotel.Hotel;
import com.example.homies.demo.model.hotel.Review;
import com.example.homies.demo.model.user.User;
import com.example.homies.demo.repository.HotelRepository;
import com.example.homies.demo.repository.ReviewRepository;
import com.example.homies.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {
    @Autowired
    private final HotelRepository hotelRepository;

    @Autowired
    private final ReviewRepository reviewRepository;

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public Review saveReviewFromDTO(Long hotelId, Long userId, ReviewRequestDTO reviewRequestDTO){
        // Fetch the hotel
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with ID: " + hotelId));

        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create the Review
        Review review = new Review(hotel, user, reviewRequestDTO);

        // Save and return the Review
        return reviewRepository.save(review);
    }
}
