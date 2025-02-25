package com.example.fullstack_backend.model.product;

import com.example.fullstack_backend.model.cart.Cart;
import com.example.fullstack_backend.model.cart_item.CartItem;
import com.example.fullstack_backend.model.cart_item.CartItemRepository;
import com.example.fullstack_backend.model.category.Category;
import com.example.fullstack_backend.model.category.CategoryRepository;
import com.example.fullstack_backend.model.category.CategoryService;
import com.example.fullstack_backend.model.image.Image;
import com.example.fullstack_backend.model.image.ImageRepository;
import com.example.fullstack_backend.model.image.dtoResponse.ImageDto;
import com.example.fullstack_backend.model.order_item.OrderItem;
import com.example.fullstack_backend.model.order_item.OrderItemRepository;
import com.example.fullstack_backend.model.product.dtoRequest.AddProductRequest;
import com.example.fullstack_backend.model.product.dtoRequest.UpdateProductRequest;
import com.example.fullstack_backend.model.product.dtoRespone.ProductDto;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public Product addProduct(AddProductRequest request) {
        throwExceptionIfProductExist(request.getName(), request.getBrand());
        Category category = categoryService.getOrCreateCategory(request.getCategory().getName());
        request.setCategory(category);
        return productRepository.save(createProduct(request));
    }

    private void throwExceptionIfProductExist(String name, String brand) {
        if (productRepository.existsByNameAndBrand(name, brand)) {
            throw new EntityExistsException(name + " already exists!");
        }
    }


    private Product createProduct(AddProductRequest request) {
        if (AddProductRequest.hasEmptyFields(request)){
            throw new IllegalStateException("Request can't have empty fields");
        };
        return Product.builder()
                .name(request.getName())
                .brand(request.getBrand())
                .price(request.getPrice())
                .inventory(request.getInventory())
                .description(request.getDescription())
                .category(request.getCategory())
                .build();
    }

    @Override
    public Product updateInventoryInProduct(UpdateProductRequest request, Long productId) {
        Product existingProduct = getProductById(productId);
        return productRepository.save(updateExistingProduct(existingProduct, request));
    }

    @Override
    public Product updateInventoryInProduct(Product product, int quantity) {
        product.setInventory(product.getInventory()-quantity);
        return productRepository.save(product);
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){
        UpdateProductRequest.validateUpdateRequest(request);
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryService.findCategoryByName((request.getCategory().getName()));
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    @Transactional
    public void deleteProductById(Long productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(product -> {
                    List<CartItem> cartItems = cartItemRepository.findByProductId(productId);
                    cartItems.forEach(cartItem ->{
                        Cart cart = cartItem.getCart();
                        cart.removeCartItem(cartItem);
                        cartItemRepository.delete(cartItem);
                    });
                    List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);
                    orderItems.forEach(orderItem-> {
                        orderItem.setProduct(null);
                        orderItemRepository.save(orderItem);
                    });
                    Optional.ofNullable(product.getCategory())
                            .ifPresent(category -> category.getProducts().remove(product));
                    product.setCategory(null);
                    productRepository.deleteById(product.getId());
                }, ()->{
                    throw new EntityNotFoundException("Product not found!");
                });
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryName, String brand) {
        return productRepository.findByCategoryNameAndBrand(categoryName, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List <ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }
    @Override
    public ProductDto convertToDto (Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map((image) -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
