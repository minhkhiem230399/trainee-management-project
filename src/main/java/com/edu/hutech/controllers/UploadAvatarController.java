package com.edu.hutech.controllers;/**
 * @project IntelliJ IDEA
 * @author KhiemKM
 */

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: Khac Huy
 */
public class UploadAvatarController {

    @RequestMapping(value = "getimage/{photo}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo) {
        if (!photo.equals("") || photo != null) {
            try {
                Path filename = Paths.get("C:/Users/PC/IdeaProjects/trainee-management-project/upload", photo);
                byte[] buffer = Files.readAllBytes(filename);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png"))
                        .body(byteArrayResource);
            } catch (Exception e) {
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
