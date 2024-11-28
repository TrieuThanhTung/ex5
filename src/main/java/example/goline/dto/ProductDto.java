package example.goline.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    @NonNull
    private String name;
    private String description;
    @NonNull
    private Double price;
    @NonNull
    private String image;
}
