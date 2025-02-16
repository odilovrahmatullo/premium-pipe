package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductFileEntity;
import premium_pipe.repository.ProductFileRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFileService {
    private final ProductFileRepository productFileRepository;
    private final FileDeleteService fileDeleteService;
    private final FileGetService fileGetService;
    @Value("${upload.url}")
    private String domainName;

    public void create(ProductEntity productEntity, List<String> imagesPaths) {
        if (imagesPaths == null || imagesPaths.isEmpty()) {
            imagesPaths = List.of(fileGetService.normalization(null));
        }
        for(String imagePath:imagesPaths){
            ProductFileEntity pf = ProductFileEntity.builder()
                    .product(productEntity)
                    .image(fileGetService.normalization(imagePath))
                    .build();
            productFileRepository.save(pf);
        }
    }

    public List<String> getProductFiles(ProductEntity entity){
        List<ProductFileEntity> fileResponses = productFileRepository.getProductFIle(entity);
        List<String> images = new ArrayList<>();
        for(ProductFileEntity image : fileResponses){
            images.add(domainName+image.getImage());
        }
        return images;
    }


    public void deleteProductFiles(ProductEntity productEntity) {
        List<ProductFileEntity> files = productFileRepository.getProductFIle(productEntity);
        for (ProductFileEntity file : files) {
            fileDeleteService.deleteFile(file.getImage());
            productFileRepository.delete(file);
        }
    }

}
