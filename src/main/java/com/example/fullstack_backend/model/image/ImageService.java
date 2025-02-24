package com.example.fullstack_backend.model.image;

import com.example.fullstack_backend.model.image.dtoResponse.ImageDto;
import com.example.fullstack_backend.model.product.IProductService;
import com.example.fullstack_backend.model.product.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found!"));
    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, () -> {
            throw new EntityNotFoundException("Image not found!");
        });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);


                Image savedImage = imageRepository.save(image);
                String downloadUrl = "/api/v1/images/"+savedImage.getId()+"/attachment";
                savedImage.setDownloadUrl(downloadUrl);
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImages.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImages;
    }
    @Override
        public ByteArrayResource getByteArrayResource(Image image) {
        try {
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
            return resource;
        } catch (SQLException e){
            throw new RuntimeException("Error while downloading image from database");
    }
    }
































}
