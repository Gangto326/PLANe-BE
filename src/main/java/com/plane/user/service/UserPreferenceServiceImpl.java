package com.plane.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.user.repository.UserRepository;

@Service
@Transactional
public class UserPreferenceServiceImpl implements UserPreferenceService{
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserPreferenceServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	@Override
	public boolean updateTripStyles(String userId, List<Integer> tripStyle) {
		
        if (tripStyle == null) {
            return false;
        }
        
        userRepository.deleteTripStyle(userId);
        
        if (!tripStyle.isEmpty()) {
            userRepository.insertTripStyle(userId, tripStyle);
        }
        
        return true;
    }

	@Override
	public boolean updateTripThemas(String userId, List<Integer> tripThema) {
		
        if (tripThema == null) {
            return false;
        }
        
        userRepository.deleteTripThema(userId);
        
        if (!tripThema.isEmpty()) {
            userRepository.insertTripThema(userId, tripThema);
        }
        
        return true;
    }
	
}
