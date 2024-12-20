package example.goline.service.product;

import example.goline.dto.ProductDto;
import example.goline.model.Product;

public interface ProductService {
    void createNewProduct(ProductDto productDto);

    Product getProductById(Integer id);

    void updateProductById(Integer id, ProductDto productDto);

    void deleteProductById(Integer id);
}
