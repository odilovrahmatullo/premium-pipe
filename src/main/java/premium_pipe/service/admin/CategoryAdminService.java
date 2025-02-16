package premium_pipe.service.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.CategoryMapper;
import premium_pipe.model.request.CategoryRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.repository.CategoryRepository;
import premium_pipe.service.CategorySlugService;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryAdminService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final FileSessionService fileSessionService;
    private final CategorySlugService categorySlugService;
    private final FileGetService fileGetService;

    public void create(final CategoryRequest categoryRequest, HttpSession session){
        String dropzoneKey = CategoryEntity.class.getName();
        String imagePath = fileSessionService.getImage(dropzoneKey,session);
        String slug = categorySlugService.generateSlug(categoryRequest.getName());
        CategoryEntity category = CategoryEntity
                .builder()
                .image(fileGetService.normalization(imagePath))
                .name(categoryRequest.getName())
                .slug(slug)
                .build();
        categoryRepository.save(category);
    }

    public Page<CategoryAdminResponse> list(RequestParams params) {
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<CategoryEntity> categoryEntities =categoryRepository.search(params.search(),pageable);
        return categoryEntities.map(categoryMapper::toDTO);
    }

    public CategoryAdminResponse getOne(Long id) {
        CategoryEntity entity = getCategoryEntity(id);
        return categoryMapper.toDTO(entity);
    }

    public void edit(Long id, CategoryRequest categoryRequest) {
        CategoryEntity entity = getCategoryEntity(id);
        entity.setName(categoryRequest.getName());
        categoryRepository.save(entity);
    }

    public CategoryEntity getCategoryEntity(Long id){
        return categoryRepository.getCategory(id).orElseThrow(
                () -> new NotFoundException("Category Not Found "));
    }


    public void delete(Long id) {
        CategoryEntity category = getCategoryEntity(id);
        category.setDeletedDate(LocalDateTime.now());
        categoryRepository.save(category);
    }

    public List<CategoryAdminResponse> getCategoryList() {
        List<CategoryEntity> categoryEntities = categoryRepository.getList();
        return categoryEntities.stream().map(categoryMapper::toDTO).toList();
    }

}
