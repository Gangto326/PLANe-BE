package com.plane.trip.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.plane.trip.dto.CoordinateDto;
import com.plane.trip.dto.TripMakeResponse;

@Service
public class TripMakeService {
	
	// 두 좌표 간의 유클리드 거리 계산
    private double calculateDistance(double[] coord1, double[] coord2) {
    	
        double x1 = coord1[0], y1 = coord1[1];
        double x2 = coord2[0], y2 = coord2[1];
        
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    
    public TripMakeResponse assignTripOrder(List<CoordinateDto> coordinates) {
    	
        int n = coordinates.size();
        if (n == 0) return new TripMakeResponse(); // 빈 리스트 처리

        // 좌표 추출
        List<double[]> coords = new ArrayList<>();
        for (CoordinateDto dto : coordinates) {
            coords.add(new double[]{dto.getMapx(), dto.getMapy()});
        }

        // 최적 경로 계산
        Result result = findOptimalRouteWithTracking(coords);

        // 최적 경로에 따라 tripOrder 설정
        List<Integer> optimalPath = result.path;
        for (int i = 0; i < optimalPath.size(); i++) {
            int order = optimalPath.get(i);
            coordinates.get(order).setTripOrder(i);
        }
        
        // TripMakeResponse 생성 및 데이터 분류
        TripMakeResponse response = new TripMakeResponse();
        List<CoordinateDto> day1 = new ArrayList<>();
        List<CoordinateDto> day2 = new ArrayList<>();
        List<CoordinateDto> day3 = new ArrayList<>();
        
        // coordinates를 tripOrder 기준으로 정렬
        coordinates.sort(Comparator.comparing(CoordinateDto::getTripOrder));
        
        // tripOrder에 따라 각 day 리스트에 할당하고 재정렬
        for (CoordinateDto dto : coordinates) {
            int tripOrder = dto.getTripOrder();
            if (tripOrder >= 0 && tripOrder <= 2) {
                dto.setTripOrder(tripOrder - 0); // 0, 1, 2
                day1.add(dto);
            } else if (tripOrder >= 3 && tripOrder <= 5) {
                dto.setTripOrder(tripOrder - 3); // 0, 1, 2
                day2.add(dto);
            } else {
                dto.setTripOrder(tripOrder - 6); // 0, 1, ...
                day3.add(dto);
            }
        }
        
        response.setDay1(day1);
        response.setDay2(day2);
        response.setDay3(day3);
        
        return response;
    }

    
    public Result findOptimalRouteWithTracking(List<double[]> coords) {
    	
        int n = coords.size(); // 좌표 개수

        // 거리 행렬 계산
        double[][] distance = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distance[i][j] = calculateDistance(coords.get(i), coords.get(j));
            }
        }

        // DP 테이블 및 경로 추적 테이블 초기화
        double[][] dp = new double[1 << n][n];
        int[][] parent = new int[1 << n][n]; // 추적 정보 저장

        for (int i = 0; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Double.MAX_VALUE;
                parent[i][j] = -1;
            }
        }

        // 출발점(0번 좌표)에서 시작
        dp[1][0] = 0;

        // DP 테이블 채우기
        for (int mask = 1; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue; // u가 mask에 포함되지 않으면 건너뜀
                for (int v = 0; v < n; v++) {
                    if ((mask & (1 << v)) != 0) continue; // v가 이미 방문된 노드라면 건너뜀
                    int nextMask = mask | (1 << v);
                    double newCost = dp[mask][u] + distance[u][v];
                    if (newCost < dp[nextMask][v]) {
                        dp[nextMask][v] = newCost;
                        parent[nextMask][v] = u; // 경로 추적 정보 저장
                    }
                }
            }
        }

        // 모든 노드를 방문하고 시작점으로 돌아오는 최적 경로 계산
        double minCost = Double.MAX_VALUE;
        int lastNode = -1;
        for (int u = 1; u < n; u++) {
            double cost = dp[(1 << n) - 1][u] + distance[u][0];
            if (cost < minCost) {
                minCost = cost;
                lastNode = u;
            }
        }

        // 경로 추적
        List<Integer> path = new ArrayList<>();
        int currentMask = (1 << n) - 1;
        int currentNode = lastNode;

        while (currentNode != -1) {
            path.add(0, currentNode); // 현재 노드를 경로에 추가
            int prevNode = parent[currentMask][currentNode];
            currentMask ^= (1 << currentNode); // 현재 노드 비트 제거
            currentNode = prevNode;
        }

        path.add(0, 0); // 시작 노드 추가

        return new Result(minCost, path);
    }
    
    
    private static class Result {
    	
        double cost;
        List<Integer> path;

        public Result(double cost, List<Integer> path) {
            this.cost = cost;
            this.path = path;
        }
    }
}
