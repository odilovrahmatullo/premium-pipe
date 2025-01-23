package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.FileEntity;
import premium_pipe.entity.ProductEntity;
import premium_pipe.entity.ProductFileEntity;
import premium_pipe.model.response.FileResponse;
import premium_pipe.repository.ProductFileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFileService {
    private final ProductFileRepository productFileRepository;
    private final FileService fileService;


    public void create(ProductEntity productEntity, List<String> fileIds) {
        for (String fileId : fileIds) {
            FileEntity file = fileService.getFileEntity(fileId);
            ProductFileEntity pf = ProductFileEntity.builder()
                    .file(file)
                    .product(productEntity)
                    .build();
            productFileRepository.save(pf);
        }
    }

    public List<FileResponse> getProductFiles(ProductEntity entity){
        List<ProductFileEntity> fileResponses = productFileRepository.getProductFIle(entity);
        List<FileResponse> files = new ArrayList<>();
        for(ProductFileEntity file : fileResponses){
            files.add(fileService.getById(file.getFile().getId()));
        }
        return files;
    }


}
