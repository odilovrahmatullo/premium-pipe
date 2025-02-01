package premium_pipe.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.service.LanguageService;
import premium_pipe.service.admin.CategoryAdminService;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private final CategoryAdminService categoryAdminService;
    private final LanguageService languageService;



    @ModelAttribute("categoryResponses")
    public List<CategoryAdminResponse> populateCategories() {
        return categoryAdminService.getCategoryList();
    }
    @ModelAttribute("defaultLang")
    public LanguageEntity defaultLang(){
        return languageService.findDefault();
    }

    @ModelAttribute("page")
    public Integer getPage(HttpServletRequest request) {
        String page = request.getParameter("page");

        if (page == null) {
            page = "0";
        }

        return Integer.parseInt(page);
    }

    @ModelAttribute("size")
    public Integer getPageSize(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");

        if (pageSize == null) {
            pageSize = "20";
        }

        return Integer.parseInt(pageSize);
    }
    @ModelAttribute("languages")
    public List<LanguageResponse> getLanguages() {
        return languageService.getActives();
    }
}
