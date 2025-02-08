package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.FileStoreEntity;
import premium_pipe.repository.FileStoreRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileStoreService {
    private final FileDeleteService fileDeleteService;
    private final FileStoreRepository fileStoreRepository;

    public void deleteExpiredFiles(){
        LocalDateTime now = LocalDateTime.now();
        List<FileStoreEntity> files = fileStoreRepository.findByExpireAt(now);
        for(FileStoreEntity file : files){
            String filePath = file.getFile();
            fileDeleteService.deleteFile(filePath);
        }
        fileStoreRepository.deleteByExpiredAt(now);
    }

    public void saveFile(String filePath) {
        FileStoreEntity file =
                FileStoreEntity.builder().file(filePath).expiredAt(LocalDateTime.now().plusDays(2)).build();
        fileStoreRepository.save(file);
    }
}
