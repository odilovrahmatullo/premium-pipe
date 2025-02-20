package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.config.RedisConfig;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.entity.StaticTypeEntity;
import premium_pipe.entity.TranslateEntity;
import premium_pipe.exception.NotFoundException;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.model.response.admin.TranslateAdminRequest;
import premium_pipe.model.response.admin.TranslationAdminResponse;
import premium_pipe.service.admin.StaticTypeAdminService;
import premium_pipe.service.admin.TranslateAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequestMapping("/admin/translation")
@RequiredArgsConstructor
public class TranslateAdminController {
    private final TranslateAdminService translateAdminService;
    private final StaticTypeAdminService staticTypeAdminService;
    private final RedisConfig redisConfig;

    @GetMapping("/create")
    public String getCreate(final Model model){
        TranslateAdminRequest obj = new TranslateAdminRequest();
        List<StaticTypeEntity> list = staticTypeAdminService.getTypes();
        model.addAttribute("types",list);
        model.addAttribute("object",obj);
        return "admin/translation/create";
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("object") TranslateAdminRequest request,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()) {
            List<StaticTypeEntity> list = staticTypeAdminService.getTypes();
            model.addAttribute("types",list);
            return "admin/translation/create";
        }

        try{
            translateAdminService.create(request);
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "admin/translation/create";
        }
        return "redirect:/admin/translation";
    }

    @GetMapping({"","/"})
    public String list(final RequestParams params, final Model model){
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<TranslationAdminResponse> translations = translateAdminService.translationAdminResponses(pageable, params.staticId());
        int totalPages = translations.getTotalPages();
        List<Integer> pagination  = Paginate.get_pagination(totalPages,params.page());
        List<StaticTypeEntity> list = staticTypeAdminService.getTypes();
        model.addAttribute("types",list);
        model.addAttribute("params",params);
        model.addAttribute("pages",pagination);
        model.addAttribute("objects",translations);
        model.addAttribute("page",params.page());
        model.addAttribute("size",params.size());
        model.addAttribute("totalPages",totalPages);
        return "admin/translation/list";
    }

    @GetMapping("/{id}/edit")
    public String getEdit(final @PathVariable Long id,Model model){
        TranslateEntity translation = translateAdminService.getEntity(id);

        TranslationAdminResponse response = translateAdminService.map(translation);

        List<StaticTypeEntity> list = staticTypeAdminService.getTypes();
        model.addAttribute("types",list);

        model.addAttribute("staticId",response.getStaticId());
        model.addAttribute("id",id);
        model.addAttribute("object",response);
        return "admin/translation/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable final Long id,
                           @ModelAttribute("object") final TranslateAdminRequest request,
                           final BindingResult result,
                           final Model model,
                           final RedirectAttributes redirectAttributes){
        TranslateEntity translate = translateAdminService.getEntity(id);

        if(result.hasErrors())
            return "admin/translation/edit";
        try{
            translateAdminService.update(translate,request);
        } catch (NotFoundException e){
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("object",request);
            return "admin/translation/edit";
        }

        redirectAttributes.addFlashAttribute("successMessage","Updated");
        return "redirect:/admin/translation";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable final Long id,final RedirectAttributes redirectAttributes){
        try{
            translateAdminService.delete(id);
        }catch (NotFoundException ex){
            redirectAttributes.addFlashAttribute("errorMessage",ex.getMessage());
            return "redirect:/admin/translation";
        }
        redirectAttributes.addFlashAttribute("successMessage","DELETED");
        return "redirect:/admin/translation";
    }

}
