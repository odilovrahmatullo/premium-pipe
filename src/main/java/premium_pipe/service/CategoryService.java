package premium_pipe.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.LocalizeMapper;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.CategoryApiResponse;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.repository.CategoryRepository;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LocalizeMapper localizeMapper;

    public Page<CategoryApiResponse> getList(RequestParams params, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<CategoryEntity> categoryEntities =categoryRepository.search(params.search(),pageable);
        return categoryEntities.map(
                c ->
                        CategoryApiResponse
                                .builder()
                                .id(c.getId())
                                .name(localizeMapper.translate(c.getName(),request))
                                .build());
    }

    public CategoryEntity getCategoryEntity(final Long categoryId) {
        return categoryRepository.getCategory(categoryId)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));
    }


}
