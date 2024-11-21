package com.plane.common.service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.plane.common.exception.custom.FileDeleteException;
import com.plane.common.exception.custom.FileUploadException;
import com.plane.common.exception.custom.InvalidFileException;

@Service
public class S3Service {
	
	// 이미지 관련
	private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;
	private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
		".jpg", ".jpeg", ".png"
	);
    private static final List<String> ALLOWED_IMAGE_CONTENT_TYPES = Arrays.asList(
        "image/jpeg", "image/png"
    );
	
    
    // 문서 관련
	private static final long MAX_DOCUMENT_SIZE = 10 * 1024 * 1024;
	private static final List<String> ALLOWED_DOCUMENT_EXTENSIONS = Arrays.asList(
		".pdf"
	);
	private static final List<String> ALLOWED_DOCUMENT_CONTENT_TYPES = Arrays.asList(
	    "application/pdf"
	);
	
	
    private final AmazonS3Client amazonS3Client;
    private final String bucket;

    public S3Service(AmazonS3Client amazonS3Client, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }


    public String uploadFile(MultipartFile file) {
    	
        try {
            String fileName = createFileName(file.getOriginalFilename());
            
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            
            amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
            );

            return amazonS3Client.getUrl(bucket, fileName).toString();
            
        } catch (Exception e) {
            throw new FileUploadException("파일 업로드에 실패했습니다." + e.getMessage());
        }
    }


    public List<String> uploadFiles(List<MultipartFile> files) {
    	
        List<String> urls = new ArrayList<>();
        
        for (MultipartFile file : files) {
            urls.add(uploadFile(file));
        }
        
        return urls;
    }

    
    // 파일 삭제 에러를 던지지 않게 변경
    public void deleteFile(String fileUrl) {
    	
        try {
            String fileName = extractFileName(fileUrl);
            amazonS3Client.deleteObject(bucket, fileName);
        } catch (Exception e) {
        	return;
        }
    }

    
    // 파일명 생성
    private String createFileName(String originalFileName) {
    	
        return UUID.randomUUID() + getExtension(originalFileName);
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
        
        if (file.getSize() > MAX_IMAGE_SIZE) {
            throw new InvalidFileException("파일 크기는 5MB를 초과할 수 없습니다.");
        }
        
        String extension = getExtension(file.getOriginalFilename()).toLowerCase();
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new InvalidFileException("지원하지 않는 파일 형식입니다.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_CONTENT_TYPES.contains(contentType)) {
            throw new InvalidFileException("올바르지 않은 이미지 파일입니다.");
        }
        
        // 실제 파일 내용 검증
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image == null) {
                throw new InvalidFileException("유효하지 않은 이미지 파일입니다.");
            }
            
        } catch (Exception e) {
            throw new InvalidFileException("이미지 파일 검증에 실패했습니다.");
        }
        
    }
    
    
    // 유효성 검사 (파일)
    private void validateDocumentFile(MultipartFile file) {
    	
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("문서 파일이 없습니다.");
        }
        
        if (file.getSize() > MAX_DOCUMENT_SIZE) {
            throw new InvalidFileException("문서 크기는 10MB를 초과할 수 없습니다.");
        }
        
        String extension = getExtension(file.getOriginalFilename()).toLowerCase();
        if (!ALLOWED_DOCUMENT_EXTENSIONS.contains(extension)) {
            throw new InvalidFileException("지원하지 않는 문서 형식입니다.");
        }
        
        String contentType = file.getContentType();
        if (!ALLOWED_DOCUMENT_CONTENT_TYPES.contains(contentType)) {
            throw new InvalidFileException("올바르지 않은 문서 파일입니다.");
        }
    }
    
}