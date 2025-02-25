package com.example.fullstack_backend.model.image;

import com.example.fullstack_backend.model.image.dtoResponse.ImageDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;


import java.sql.SQLException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    void updateImage(MultipartFile file,Long imageId);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);

    ByteArrayResource getByteArrayResource(Image image);
}
