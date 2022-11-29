package com.codestates.mainproject.oneyearfourcut.global.util;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileUtils {

    private static final Tika tika = new Tika();

    public static boolean validFile(MultipartFile inputStream) {
        try {

            List<String> typeList = List.of("image/jpeg", "image/jpg", "image/png", "image/heic");
            String mimeType = tika.detect(inputStream.getInputStream());
            return typeList.stream().anyMatch(
                    type -> type.equalsIgnoreCase(mimeType));
        } catch (IOException e) {
            return false;
        }
    }


}
