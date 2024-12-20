package example.goline.controller;

import example.goline.dto.ProductDto;
import example.goline.model.Product;
import example.goline.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createNewProduct(@RequestBody ProductDto productDto) {
        productService.createNewProduct(productDto);
        return ResponseEntity.ok("Create success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        productService.updateProductById(id, productDto);
        return ResponseEntity.ok("Update success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Delete success");
    }
}
