package com.example.exam.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Image Cloudinary pe upload karo
     * @return Cloudinary ka permanent URL (https://res.cloudinary.com/...)
     */
    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            // Cloudinary pe upload karo
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder",          "exam-profiles",   // Cloudinary mein folder
                            "resource_type",   "image",
                            "transformation",  "c_fill,w_300,h_300,g_face"  // Auto crop face
                    )
            );

            // Permanent HTTPS URL return karo
            return (String) result.get("secure_url");

        } catch (IOException e) {
            throw new RuntimeException("Cloudinary upload failed: " + e.getMessage(), e);
        }
    }
}