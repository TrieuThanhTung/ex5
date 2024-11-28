package example.goline.service.product;

import example.goline.dto.ProductDto;
import example.goline.exception.CustomException;
import example.goline.model.Product;
import example.goline.repository.ProductRepository;
import example.goline.service.kafka.KafkaProducerService;
import example.goline.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final RedisService redisService;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void createNewProduct(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findByName(productDto.getName());
        if(optionalProduct.isPresent()) throw new CustomException("Product is available", HttpStatus.UNPROCESSABLE_ENTITY);
        Product newProduct = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .rating(0F)
                .image(productDto.getImage())
                .build();
        productRepository.save(newProduct);
        kafkaProducerService.sendMessage(productDto.getName());
    }

    @Override
    public Product getProductById(Integer id) {
        String key = String.format("Product_%d", id);
        Product product = redisService.get(key, Product.class);
        if(Objects.isNull(product)) {
            product = productRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Not found product", HttpStatus.NOT_FOUND));
            redisService.set(key, product);
        }
        return product;
    }

    @Override
    public void updateProductById(Integer id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not found product", HttpStatus.NOT_FOUND));
        if(!productDto.getName().equals(product.getName())) {
            product.setName(productDto.getName());
        }
        if(!productDto.getDescription().equals(product.getDescription())) {
            product.setDescription(productDto.getDescription());
        }
        if(!productDto.getPrice().equals(product.getPrice())) {
            product.setPrice(productDto.getPrice());
        }
        if(!productDto.getImage().equals(product.getImage())) {
            product.setImage(productDto.getImage());
        }
        productRepository.save(product);
        redisService.deletePatternKey("Product");
    }

    @Override
    public void deleteProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not found product", HttpStatus.NOT_FOUND));
        productRepository.delete(product);
        redisService.deletePatternKey(String.format("Product_%d", id));
    }
}
