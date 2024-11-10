package com.plane.user.service;

import java.util.List;

public interface UserPreferenceService {
	
	boolean updateTripStyles(String userId, List<Integer> tripStyle);
	
	boolean updateTripThemas(String userId, List<Integer> tripThema);
	
}
