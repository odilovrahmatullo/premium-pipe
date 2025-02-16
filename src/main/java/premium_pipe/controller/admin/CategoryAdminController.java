package premium_pipe.controller.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.GalleryEntity;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.model.request.CategoryRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.model.response.admin.ProductAdminResponse;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.LanguageService;
import premium_pipe.service.admin.CategoryAdminService;
import premium_pipe.service.admin.ProductAdminService;
import premium_pipe.util.Paginate;
import java.util.List;


@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;
    private final LanguageService languageService;
    private final ProductAdminService productAdminService;
    private final FileSessionService fileSessionService;

    @GetMapping("/create")
    public String createCategory(final Model model){
        CategoryRequest categoryRequest = new CategoryRequest();
        List<LanguageResponse> languages = languageService.getActives();
        LanguageEntity language = languageService.findDefault();
        model.addAttribute("object",categoryRequest);
        model.addAttribute("languages",languages);
        model.addAttribute("defaultLang",language);
        model.addAttribute("dropzoneKey",CategoryEntity.class.getName());
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("object") final CategoryRequest category,
                         final BindingResult result,
                         final Model model,
                         final HttpSession session){
        String dropzoneKey = CategoryEntity.class.getName();
        String image = fileSessionService.getImage(dropzoneKey,session);
        model.addAttribute("requestImage", image);
        if(result.hasErrors()) {
            model.addAttribute("requestImage",image);
            model.addAttribute("object",category);
            return "admin/category/create";
        }
        try{
            categoryAdminService.create(category,session);
        }
        catch (Exception e){
            model.addAttribute("object",category);
            model.addAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/admin/category";
    }

        @GetMapping({"","/"})
        public String list(final RequestParams params,final Model model){
            Page<CategoryAdminResponse> categoryResponses = categoryAdminService.list(params);
            int totalPages = categoryResponses.getTotalPages();
            List<Integer> pagination  = Paginate.get_pagination(totalPages,params.page());
            LanguageEntity language = languageService.findDefault();
            model.addAttribute("defaultLang",language);
            model.addAttribute("pages",pagination);
            model.addAttribute("categories",categoryResponses);
            model.addAttribute("page",params.page());
            model.addAttribute("size",params.size());
            model.addAttribute("totalPages",totalPages);
            return "admin/category/list";
        }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, final Model model,
                               @Valid RequestParams params) {
        CategoryEntity category = categoryAdminService.getCategoryEntity(id);
        Page<ProductAdminResponse> products = productAdminService.getProductsByCategory(category,params);
        int totalPages = products.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages,params.page());
        LanguageEntity language = languageService.findDefault();
        model.addAttribute("defaultLang",language);
        model.addAttribute("pages",pagination);
        model.addAttribute("page",params.page());
        model.addAttribute("size",params.size());
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("category", category);
        model.addAttribute("products",products);
        return "admin/category/view";
    }


    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id, final Model model){
        CategoryAdminResponse car = categoryAdminService.getOne(id);
        LanguageEntity language = languageService.findDefault();
        List<LanguageResponse> languages = languageService.getActives();
        model.addAttribute("languages",languages);
        model.addAttribute("defaultLang",language);
        model.addAttribute("object",car);
        model.addAttribute("id",id);
        return "admin/category/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("object")final CategoryRequest categoryRequest,
            final BindingResult result,
            final Model model){

        if(result.hasErrors()){
            model.addAttribute("object",categoryRequest);
            return "admin/category/edit";
        }
        try {
            categoryAdminService.edit(id,categoryRequest);
        }
        catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("object",categoryRequest);
            return "admin/category/edit";
        }

        return "redirect:/admin/category";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") final Long id,
                                 final RedirectAttributes redirectAttributes,
                                 final Model model){
        try{
            categoryAdminService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Deleted");
        }   catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/admin/category";
    }
}
