package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import premium_pipe.entity.FileEntity;
import premium_pipe.entity.NewsEntity;
import premium_pipe.entity.NewsFileEntity;
import premium_pipe.mapper.FileMapper;
import premium_pipe.model.response.FileResponse;
import premium_pipe.repository.NewsFileRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFileService {
    private final NewsFileRepository newsFileRepository;
    private final FileService fileService;
    private final FileMapper fileMapper;

    public void create(NewsEntity news, List<String> fileIds) {
        for (String str : fileIds) {
            FileEntity file = fileService.getFileEntity(str);
            NewsFileEntity nfe = NewsFileEntity.builder()
                    .file(file)
                    .news(news)
                    .build();
            newsFileRepository.save(nfe);
        }
    }

    public List<FileResponse> getNewsFiles(NewsEntity n) {
        List<FileEntity> files = newsFileRepository.getFiles(n);
        return files.stream().map(fileMapper::toDTO).toList();
    }
}
