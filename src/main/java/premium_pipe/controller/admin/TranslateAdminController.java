package premium_pipe.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.entity.StaticTypeEntity;
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
    @GetMapping("/create")
    public String getCreate(final Model model){
        TranslateAdminRequest obj = new TranslateAdminRequest();
        List<StaticTypeEntity> list = staticTypeAdminService.getTypes();
        model.addAttribute("types",list);
        model.addAttribute("object",obj);
        return "admin/translation/create";
    }

    @PostMapping("/create")
    public String postCreate(final TranslateAdminRequest request, BindingResult result,
                             final Model model,
                             final RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            return "admin/translation/create";

        try{
            translateAdminService.create(request);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
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
}
