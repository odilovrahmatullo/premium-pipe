package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.model.request.CategoryRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.service.admin.CategoryAdminService;
import premium_pipe.util.Paginate;
import java.util.List;


@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;

    @GetMapping("/create")
    public String createCategory(final Model model){
        CategoryRequest categoryRequest = new CategoryRequest();
        model.addAttribute("object",categoryRequest);
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("object") final CategoryRequest category,
                                       final BindingResult result,
                                       final Model model){
        if(result.hasErrors()) {
            model.addAttribute("object",category);
            return "admin/category/create";
        }
        try{
            categoryAdminService.create(category);
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
        model.addAttribute("pages",pagination);
        model.addAttribute("objects",categoryResponses);
        model.addAttribute("page",params.page());
        model.addAttribute("size",params.size());
        model.addAttribute("totalPages",totalPages);
        return "admin/category/list";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id, final Model model){
        CategoryAdminResponse car = categoryAdminService.getOne(id);
        model.addAttribute("object",car);
        return "admin/category/admin";
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
                                  RedirectAttributes redirectAttributes,
                                 final Model model){
        try{
            categoryAdminService.delete(id);
            redirectAttributes.addAttribute("successMessage", "Category Successfully deleted");
        }   catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/admin/category";
    }
}
