package com.plane.common.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.plane.common.exception.custom.FileDeleteException;
import com.plane.common.exception.custom.FileUploadException;
import com.plane.common.exception.custom.InvalidFileException;

@Service
public class S3Service {
	
    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public S3Service(AmazonS3Client amazonS3Client, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }

   
    public String uploadFile(String directory, MultipartFile file) {
    	
        try {
            String fileName = createFileName(directory, file.getOriginalFilename());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead)
            );

            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (Exception e) {
            throw new FileUploadException("파일 업로드에 실패했습니다.");
        }
    }

   
    public List<String> uploadFiles(String directory, List<MultipartFile> files) {
    	
        List<String> urls = new ArrayList<>();
        
        for (MultipartFile file : files) {
            urls.add(uploadFile(directory, file));
        }
        
        return urls;
    }

    
    public void deleteFile(String fileUrl) {
    	
        try {
            String fileName = extractFileName(fileUrl);
            amazonS3Client.deleteObject(bucket, fileName);
        } catch (Exception e) {
            throw new FileDeleteException("파일 삭제에 실패했습니다.");
        }
    }

    
    // 파일명 생성 (경로 포함)
    private String createFileName(String directory, String originalFileName) {
    	
        return directory + "/" + UUID.randomUUID() + getExtension(originalFileName);
    }


    // 파일 확장자 추출
    private String getExtension(String fileName) {
    	
        return fileName.substring(fileName.lastIndexOf("."));
    }

    
    // URL에서 파일명 추출
    private String extractFileName(String fileUrl) {
    	
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }

    
    // 유효성 검사 (이미지)
    public void validateImageFile(MultipartFile file) {
    	
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("이미지 파일이 없습니다.");
        }

        
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new InvalidFileException("파일 크기는 5MB를 초과할 수 없습니다.");
        }

        
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new InvalidFileException("이미지 파일만 업로드 가능합니다.");
        }

        
        List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png");
        String extension = getExtension(file.getOriginalFilename()).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new InvalidFileException("지원하지 않는 파일 형식입니다.");
        }
    }
    
    
    // 유효성 검사 (파일)
    
}