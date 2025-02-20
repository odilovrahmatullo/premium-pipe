package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final LocalizeMapper localizeMapper;
    @Value("${upload.url}")
    private String domainName;


    public Page<NewsResponse> getNews(@Valid RequestParams params, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<NewsEntity> news = newsRepository.getNewsPages(pageable,params.search());
        return news.map(n ->getOne(n,request));
    }

    public NewsResponse getOne(NewsEntity n,HttpServletRequest request) {
        return NewsResponse.builder()
                .id(n.getId())
                .createdDate(n.getCreatedDate())
                .slug(n.getSlug())
                .image(domainName+n.getImage())
                .viewCount(n.getViewCount())
                .title(localizeMapper.translate(n.getTitle(),request))
                .description(localizeMapper.translate(n.getDescription(),request))
                .build();
    }

    public NewsEntity getNewsEntity(String slug){
       NewsEntity news = newsRepository.getBySlug(slug).orElseThrow(
                ()-> new NotFoundException("News not found"));
       newsRepository.save(news);
       return news;
    }

    public List<NewsResponse> getNewsExceptThat(final NewsEntity news, final HttpServletRequest request){
        List<NewsEntity> otherNewsExceptThat = newsRepository.getNewsExceptThat(news.getId());
       return otherNewsExceptThat.stream().map(
                one -> getOne(one,request)
        ).toList();

    }

    public NewsResponse getNewsBySlug(final String slug, final HttpServletRequest request){
        NewsEntity news = getNewsEntity(slug);
        news.setViewCount(news.getViewCount()+1);
        newsRepository.save(news);
        NewsResponse response = getOne(news,request);
        List<NewsResponse> newsExcept = getNewsExceptThat(news,request);
        response.setOtherNews(newsExcept);
        return response;
    }
}
