package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductFileEntity;
import premium_pipe.repository.ProductFileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFileService {
    private final ProductFileRepository productFileRepository;
    public void create(ProductEntity productEntity, List<String> imagesPaths) {
        for(String imagePath:imagesPaths){
            int index = imagePath.lastIndexOf("uploads");
            String relativePath = imagePath.substring(index-1);
            relativePath = relativePath.replace('\\', '/');
            ProductFileEntity pf = ProductFileEntity.builder()
                    .product(productEntity)
                    .image(relativePath)
                    .build();
            productFileRepository.save(pf);
        }
    }

    public List<String> getProductFiles(ProductEntity entity){
        List<ProductFileEntity> fileResponses = productFileRepository.getProductFIle(entity);
        List<String> images = new ArrayList<>();
        for(ProductFileEntity image : fileResponses){
            images.add(image.getImage());
        }
        return images;
    }


}
