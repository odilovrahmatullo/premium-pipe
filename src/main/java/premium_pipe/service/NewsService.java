package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.NewsEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.LocalizeMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.NewsResponse;
import premium_pipe.repository.NewsRepository;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final LocalizeMapper localizeMapper;

    public Page<NewsResponse> getNews(@Valid RequestParams params, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<NewsEntity> news = newsRepository.getNewsPages(pageable);
        return news.map(n ->getOne(n,request));
    }

    public NewsResponse getOne(NewsEntity n,HttpServletRequest request) {
        return NewsResponse.builder()
                .id(n.getId())
                .createdDate(n.getCreatedDate())
                .viewCount(n.getViewCount())
                .title(localizeMapper.translate(n.getTitle(),request))
                .description(localizeMapper.translate(n.getDescription(),request))
                .build();
    }

    public NewsEntity getNewsEntity(Long id){
       NewsEntity news = newsRepository.getNews(id).orElseThrow(
                ()-> new NotFoundException("News not found"));
       news.setViewCount(news.getViewCount()+1);
       newsRepository.save(news);
       return news;
    }
}
