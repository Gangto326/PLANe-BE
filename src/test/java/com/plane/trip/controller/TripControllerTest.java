package com.plane.trip.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.repository.AccompanyRepository;
import com.plane.accompany.service.AccompanyService;
import com.plane.trip.dto.CoordinateDto;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.repository.TripRepository;
import com.plane.trip.service.TripService;


@AutoConfigureMockMvc
@SpringBootTest(
		properties = {
			"spring.config.location=classpath:application.properties" 
		}
	)
public class TripControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private TripService tripService;
	
	@Autowired
    private TripRepository tripRepository;
	
	
	@Test
	@DisplayName("여행 생성하기")
//	@Disabled
	void testTripCreate() throws Exception {
		System.out.println("==== TripCreate Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjEwMjY5NiwiZXhwIjoxNzMyMTM4Njk2fQ.jwz8EMP-G_nlxkqC-_voVdiWdGVhgM3gEEi22M75BwSNQm-gqFzdK7qf7IzGhNYM";
		
		TripCreateRequest tripCreateRequest = new TripCreateRequest();
		tripCreateRequest.setRegionId(1);
		tripCreateRequest.setAccompanyNum(3L);
		tripCreateRequest.setArrivedDate(LocalDate.now().plusDays(1));
		tripCreateRequest.setDepartureDate(LocalDate.now().plusDays(1));
		tripCreateRequest.setState("저장");
		tripCreateRequest.setTripDays(1);
		tripCreateRequest.setTripName("여행제목");
		
		List<CoordinateDto> coor = new ArrayList<>();
		
		CoordinateDto coordinateDto = new CoordinateDto();
		coordinateDto.setTripOrder(1);
		coordinateDto.setTitle("여행지명");
		coordinateDto.setAddr1("주소주소");
		coordinateDto.setMemo("메모합니다");
		coordinateDto.setMapx(36.12352633);
		coordinateDto.setMapy(121.12422355);
		
		coor.add(coordinateDto);
		
		tripCreateRequest.setDay1(coor);
		
		mockMvc.perform(post("/api/plane")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(objectMapper.writeValueAsString(tripCreateRequest)))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== TripCreate Test End ====");

	}
}