package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.NewsEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.NewsMapper;
import premium_pipe.model.request.NewsRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.NewsAdminResponse;
import premium_pipe.repository.NewsRepository;
import premium_pipe.service.FileDeleteService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.NewsSlugService;

@Service
@RequiredArgsConstructor
public class NewsAdminService {
    private final NewsRepository newsRepository;
    private final FileSessionService fileSessionService;
    private final NewsMapper newsMapper;
    private final NewsSlugService newsSlugService;
    private final FileDeleteService fileDeleteService;

    public void create(NewsRequest newsRequest, HttpSession session) {
        String dropzoneKey = NewsEntity.class.getName();
        String imagePath = fileSessionService.getImage(dropzoneKey, session);
        NewsEntity news = newsMapper.toEntity(newsRequest);
        String slug = newsSlugService.generateSlug(news.getTitle());
        news.setSlug(slug);
        news.setImage(image(imagePath));
        newsRepository.save(news);
        fileSessionService.deleteFilesFromSession(dropzoneKey, session);
    }

    public Page<NewsAdminResponse> getNews(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(), params.size());
        Page<NewsEntity> news = newsRepository.getNewsPages(pageable, params.search());
        return news.map(newsMapper::toDTO);
    }

    public NewsAdminResponse getNewsResponse(NewsEntity n) {
        return newsMapper.toDTO(n);
    }

    public NewsEntity getNewsEntity(Long id) {
        return newsRepository.getNews(id).orElseThrow(()
                -> new NotFoundException("News not found"));
    }

    public void delete(Long id) {
        NewsEntity entity = getNewsEntity(id);
        newsRepository.delete(entity);
    }

    public void update(final NewsEntity news, final NewsRequest newsRequest, final HttpSession session) {
        Long id = news.getId();
        String dropzoneKey = NewsEntity.class.getName();
        String imagePath = fileSessionService.getImage(dropzoneKey, session);
        newsMapper.updateNews(newsRequest, news);
        String slug = newsSlugService.generateSlug(id, news.getTitle());
        news.setSlug(slug);
        if (imagePath != null) {
            news.setImage(image(imagePath));
        }
        newsRepository.save(news);
        fileSessionService.deleteFilesFromSession(dropzoneKey, session);
    }

    private String image(String path) {
        int index = path.lastIndexOf("uploads");
        String relativePath = path.substring(index - 1);
        return relativePath.replace('\\', '/' );
    }

    public void deleteImage(Long id) {
        NewsEntity news = getNewsEntity(id);
        fileDeleteService.deleteFile(news.getImage());
        newsRepository.save(news);
    }
}
