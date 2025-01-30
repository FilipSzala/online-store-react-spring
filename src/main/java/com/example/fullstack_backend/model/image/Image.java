package com.example.fullstack_backend.model.image;
import com.example.fullstack_backend.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    private Blob image;
    private String downloadUrl;
    @ManyToOne
    @JoinColumn(name = "product_id",
                referencedColumnName = "id",
                foreignKey = @ForeignKey (
                        name = "image_product_fk"
                ))
    private Product product;
}
