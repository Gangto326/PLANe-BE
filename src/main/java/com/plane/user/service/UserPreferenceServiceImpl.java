package com.plane.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.trip.repository.TripRepository;
import com.plane.user.repository.UserRepository;

@Service
@Transactional
public class UserPreferenceServiceImpl implements UserPreferenceService{
	
	private final UserRepository userRepository;
	private final TripRepository tripRepository;
	
	@Autowired
	public UserPreferenceServiceImpl(UserRepository userRepository, TripRepository tripRepository) {
		this.userRepository = userRepository;
		this.tripRepository = tripRepository;
	}

	
	@Override
	public boolean updateTripStyles(String userId, List<Integer> tripStyle) {
		
        if (tripStyle == null) {
            return false;
        }
        
        tripRepository.deleteTripStyle(userId);
        
        if (!tripStyle.isEmpty()) {
        	tripRepository.insertTripStyle(userId, tripStyle);
        }
        
        return true;
    }

	@Override
	public boolean updateTripThemas(String userId, List<Integer> tripThema) {
		
        if (tripThema == null) {
            return false;
        }
        
        tripRepository.deleteTripThema(userId);
        
        if (!tripThema.isEmpty()) {
        	tripRepository.insertTripThema(userId, tripThema);
        }
        
        return true;
    }
	
}
