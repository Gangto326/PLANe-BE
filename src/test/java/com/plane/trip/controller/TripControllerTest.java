package com.plane.trip.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.plane.trip.dto.CoordinateDto;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripUpdateRequest;
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
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjI2MjQxNywiZXhwIjoxNzMyMjk4NDE3fQ.-6px6sMLg7kAy_5-XeYOmzSTvX8Y9E49oYqwkxTqEN3-4ONn2499AAMY1CANQ1nt";
		
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
		coordinateDto.setMapx(36.12352);
		coordinateDto.setMapy(121.1242);
		coor.add(coordinateDto);
		
		CoordinateDto coordinateDto2 = new CoordinateDto();
		coordinateDto2.setTripOrder(3);
		coordinateDto2.setTitle("여행지명");
		coordinateDto2.setAddr1("주소주소");
		coordinateDto2.setMemo("메모합니다");
		coordinateDto2.setMapx(1234.12352);
		coordinateDto2.setMapy(121.1242);
		coor.add(coordinateDto2);
		
		CoordinateDto coordinateDto3 = new CoordinateDto();
		coordinateDto3.setTripOrder(2);
		coordinateDto3.setTitle("여행지명");
		coordinateDto3.setAddr1("주소주소");
		coordinateDto3.setMemo("메모합니다");
		coordinateDto3.setMapx(9.12352);
		coordinateDto3.setMapy(121.121234542);
		coor.add(coordinateDto3);
		
		tripCreateRequest.setDay1(coor);
		
		mockMvc.perform(post("/api/plane")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(objectMapper.writeValueAsString(tripCreateRequest)))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== TripCreate Test End ====");

	}
	
	
	@Test
	@DisplayName("여행 불러오기")
	@Disabled
	void testGetTrip() throws Exception {
		System.out.println("==== GetTrip Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjE1Njc5OSwiZXhwIjoxNzMyMTkyNzk5fQ.6p_n_Kh0IhjGPeNBoAptwbeHgTunRDXMTkxEUTA6U82301r06RiTBYEQk_Bx6Mz8";
		
		mockMvc.perform(get("/api/plane/{tripLd}", 10)
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== GetTrip Test End ====");

	}
	
	
	@Test
	@DisplayName("여행 수정하기")
	@Disabled
	void testUpdateTrip() throws Exception {
		System.out.println("==== UpdateTrip Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjE1Njc5OSwiZXhwIjoxNzMyMTkyNzk5fQ.6p_n_Kh0IhjGPeNBoAptwbeHgTunRDXMTkxEUTA6U82301r06RiTBYEQk_Bx6Mz8";
		
		TripUpdateRequest tripUpdateRequest = new TripUpdateRequest();
		tripUpdateRequest.setTripId(14L);
		tripUpdateRequest.setAccompanyNum(5L);
		tripUpdateRequest.setArrivedDate(LocalDate.now().plusDays(1));
		tripUpdateRequest.setDepartureDate(LocalDate.now().plusDays(2));
		tripUpdateRequest.setState("저장");
		tripUpdateRequest.setTripDays(1);
		tripUpdateRequest.setTripName("asd수정된 여행 제목");
		
		List<CoordinateDto> coor = new ArrayList<>();
		
		CoordinateDto coordinateDto = new CoordinateDto();
		coordinateDto.setTripOrder(1);
		coordinateDto.setTitle("수정 여행지명");
		coordinateDto.setAddr1("주소주소");
		coordinateDto.setMemo("메모합니다");
		coordinateDto.setMapx(36.12352);
		coordinateDto.setMapy(121.1242);
		coor.add(coordinateDto);
		
		CoordinateDto coordinateDto2 = new CoordinateDto();
		coordinateDto2.setTripOrder(3);
		coordinateDto2.setTitle("여행지명");
		coordinateDto2.setAddr1("수정 주소주소");
		coordinateDto2.setMemo("메모합니다");
		coordinateDto2.setMapx(100.12352);
		coordinateDto2.setMapy(121.1242);
		coor.add(coordinateDto2);
		
		CoordinateDto coordinateDto3 = new CoordinateDto();
		coordinateDto3.setTripOrder(2);
		coordinateDto3.setTitle("여행지명");
		coordinateDto3.setAddr1("주소주소");
		coordinateDto3.setMemo("수정 메모합니다");
		coordinateDto3.setMapx(9.12352);
		coordinateDto3.setMapy(121.121234542);
		coor.add(coordinateDto3);
		
		tripUpdateRequest.setDay1(coor);
		
		mockMvc.perform(patch("/api/plane")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(objectMapper.writeValueAsString(tripUpdateRequest)))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== UpdateTrip Test End ====");

	}
}