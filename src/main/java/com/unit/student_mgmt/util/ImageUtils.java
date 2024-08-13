package com.unit.student_mgmt.util;

import com.unit.student_mgmt.exception.AppException;
import com.unit.student_mgmt.exception.ErrorCode;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ImageUtils {
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final List<String> ACCEPTED_FILE_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif"
    );
    public static void validateFile(MultipartFile file) {
        if (!file.isEmpty()) {
            // check file size
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new AppException(ErrorCode.FILE_TOO_LARGE);
            }

            // check file type
            String contentType = file.getContentType();
            if (!ACCEPTED_FILE_TYPES.contains(contentType)) {
                throw new AppException(ErrorCode.INVALID_FILE_TYPE);
            }
        }
    }

    public static String convertToBase64(Blob photoBlob) {
        byte[] photoBytes = null;
        try {
            photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(photoBytes);
    }
}
