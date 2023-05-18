package com.nohhow.randomimg.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ImageController {

    private static final String IMAGES_DIRECTORY = "static/images/mango";

    @GetMapping(value = "/random-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getRandomImage() throws IOException {
        Resource resource = new ClassPathResource(IMAGES_DIRECTORY);
        Path resourcePath = resource.getFile().toPath();
        List<String> images = listFilesInDirectory(resourcePath);

        String randomImage = getRandomElement(images);
        Resource randomImageResource = new ClassPathResource(IMAGES_DIRECTORY + "/" + randomImage);

        byte[] imageBytes = Files.readAllBytes(randomImageResource.getFile().toPath());
        return ResponseEntity.ok().body(imageBytes);
    }

    private List<String> listFilesInDirectory(Path directoryPath) throws IOException {
        List<String> files = new ArrayList<>();
        Files.list(directoryPath)
                .filter(Files::isRegularFile)
                .forEach(file -> files.add(file.getFileName().toString()));
        return files;
    }

    private <T> T getRandomElement(List<T> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
}