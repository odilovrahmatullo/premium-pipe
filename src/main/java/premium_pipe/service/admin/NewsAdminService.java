package premium_pipe.service.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.NewsEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.model.request.NewsRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.NewsAdminResponse;
import premium_pipe.repository.NewsRepository;
import premium_pipe.service.NewsFileService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NewsAdminService {
    private final NewsRepository newsRepository;
    private final NewsFileService newsFileService;

    public void create(NewsRequest newsRequest) {
        NewsEntity news = NewsEntity.builder()
                .title(newsRequest.getTitle())
                .description(newsRequest.getDescription())
                .build();
        newsRepository.save(news);
        newsFileService.create(news,newsRequest.getFileIds());
    }

    public Page<NewsAdminResponse> getNews(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<NewsEntity> news = newsRepository.getNewsPages(pageable);
        return news.map(this::getNewsResponse);
    }

    public NewsAdminResponse getNewsResponse(NewsEntity n) {
       return NewsAdminResponse.builder()
                .title(n.getTitle())
                .description(n.getDescription())
                .fileIds(newsFileService.getNewsFiles(n))
                .createdDate(n.getCreatedDate())
                .viewCount(n.getViewCount())
                .build();
    }
    public NewsEntity getNewsEntity(Long id){
        return newsRepository.getNews(id).orElseThrow(()
                -> new NotFoundException("News not found"));
    }

    public void update(Long id,NewsRequest newsRequest) {
        NewsEntity entity = getNewsEntity(id);
        entity.setTitle(newsRequest.getTitle());
        entity.setDescription(newsRequest.getDescription());
        newsRepository.save(entity);
        newsFileService.create(entity,newsRequest.getFileIds());
    }

    public void delete(Long id) {
        NewsEntity entity = getNewsEntity(id);
        entity.setDeletedDate(LocalDateTime.now());
        newsRepository.save(entity);
    }
}
