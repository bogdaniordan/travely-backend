package com.codecool.travely.util;

import com.codecool.travely.exception.customs.FileIsNotImageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.http.entity.ContentType.*;

@Service
public class FileChecker {
    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(
                IMAGE_JPEG.getMimeType(),
                IMAGE_PNG.getMimeType(),
                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new FileIsNotImageException("Uploaded file is not an image.");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty.");
        }
    }

    public Map<String, String> checkFile(MultipartFile file) {
        isFileEmpty(file);
        isImage(file);
        return extractMetadata(file);
    }
}
