package com.example.Products.service;


import com.example.Products.model.Product;
import com.example.Products.repositories.ProductRepository;
import com.example.Products.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {


    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp (){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public  void testGetProducts() {
        Product product = new Product(1L, "SKU1", "Item", 5.0, true);
        List<Product> products = Collections.singletonList(product);
        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getProducts();
        assertEquals(1, result.size());
        assertEquals("SKU1", result.get(0).getSku());
    }

    @Test
    public void testDeleteProduct(){
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ResponseEntity<Object> response = productService.deleteProduct(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully", response.getBody());
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testNewProduct() {
        Product product = new Product();
        product.setSku("Item1");
        product.setName("Product1");
        product.setPrice(5.0);
        product.setStatus(true);

        ResponseEntity<Object> response = productService.newProduct(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product created successfully", response.getBody());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() {
        Long id = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(id);
        existingProduct.setSku("Item1");
        existingProduct.setName("Existing Product");
        existingProduct.setPrice(5.0);
        existingProduct.setStatus(true);

        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setSku("AItem2");
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(50.0);
        updatedProduct.setStatus(false);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Object> response = productService.updateProduct(id, updatedProduct);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product updated successfully", response.getBody());
        verify(productRepository, times(1)).save(existingProduct);

        assertEquals("AItem2", existingProduct.getSku());
        assertEquals("Updated Product", existingProduct.getName());
        assertEquals(50.0, existingProduct.getPrice());
        assertEquals(false, existingProduct.getStatus());
    }

}
