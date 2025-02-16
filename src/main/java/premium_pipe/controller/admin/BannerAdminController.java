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
import premium_pipe.entity.BannerEntity;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.exception.BaseException;
import premium_pipe.model.request.BannerAdminRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.BannerResponse;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.model.response.admin.BannerAdminResponse;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.LanguageService;
import premium_pipe.service.admin.BannerAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/banner")
public class BannerAdminController {
    private final BannerAdminService bannerAdminService;
    private final FileSessionService fileSessionService;
    private final LanguageService languageService;
    private final FileGetService fileGetService;


    @GetMapping("/create")
    public String create(final Model model) {
        BannerAdminRequest ban = new BannerAdminRequest();
        List<LanguageResponse> languages = languageService.getActives();
        LanguageEntity language = languageService.findDefault();
        model.addAttribute("object",ban);
        model.addAttribute("languages",languages);
        model.addAttribute("defaultLang",language);
        model.addAttribute("dropzoneKey", BannerEntity.class.getName());
        return "admin/banner/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("object") BannerAdminRequest request,
            final BindingResult result,
            final RedirectAttributes redirectAttributes,
            final HttpSession session,
            final Model model) {
        String dropzoneKey = BannerEntity.class.getName();
        String image = fileSessionService.getImage(dropzoneKey, session);
        model.addAttribute("requestImage", image);
        if(result.hasErrors()){
            if(image!=null) {
                model.addAttribute("requestImage",fileGetService.normalization(image));
            }
            return "admin/banner/create";
        }
        try {
            bannerAdminService.create(request,session);
        } catch (Exception exception) {
            if (exception instanceof BaseException) {
                model.addAttribute("error", ((BaseException) exception).getErrors());
            } else {
                redirectAttributes.addFlashAttribute("errorMessages", exception.getMessage());
            }
            model.addAttribute("requestImage",fileGetService.normalization(image));
            return "admin/banner/create";
        }
        return "redirect:/admin/banner";
    }

    @GetMapping({"", "/"})
    public String list(final RequestParams params, final Model model) {
        Page<BannerAdminResponse> banners = bannerAdminService.getBanners(params);
        int totalPages = banners.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        model.addAttribute("objects", banners);
        model.addAttribute("page", params.page());
        model.addAttribute("defaultLang",languageService.findDefault());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("size", params.size());
        model.addAttribute("pages", pagination);
        return "admin/banner/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            bannerAdminService.delete(id);
        } catch (Exception e) {
            model.addAttribute("deleteError", e.getMessage());
            return "redirect:/admin/banner";
        }
        redirectAttributes.addFlashAttribute("successMessage", "DELETED");
        return "redirect:/admin/banner";
    }

}
