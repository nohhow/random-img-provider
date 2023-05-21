package com.nohhow.randomimg.controller;


import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Random;

@RestController
class ImageController {

    @GetMapping(value = "/random-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getRandomImage() throws IOException {
        Resource[] resources = ResourcePatternUtils
                .getResourcePatternResolver(new DefaultResourceLoader())
                .getResources("classpath*:static/images/mango/**");

        // 랜덤하게 이미지 선택
        Random random = new Random();
        int randomIndex = random.nextInt(resources.length);
        Resource randomImageResource = resources[randomIndex];

        // 이미지 데이터 읽기
        byte[] imageData = StreamUtils.copyToByteArray(randomImageResource.getInputStream());

        // ResponseEntity로 이미지 데이터 반환
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageData.length);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}