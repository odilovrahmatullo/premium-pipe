package premium_pipe.controller.admin;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.entity.BannerEntity;
import premium_pipe.entity.CategoryEntity;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.exception.BaseException;
import premium_pipe.mapper.BannerMapper;
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
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/banner")
public class BannerAdminController {
    private final BannerAdminService bannerAdminService;
    private final FileSessionService fileSessionService;
    private final LanguageService languageService;
    private final FileGetService fileGetService;
    private final BannerMapper bannerMapper;


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

    @PostMapping("/{id}/deleteImage")
    public ResponseEntity<?> deleteImage(final Long id){
        try{
            bannerAdminService.deleteImage(id);
        }catch (Exception exception) {
            return ResponseEntity.status(403).body(Map.of("error", exception.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/edit")
    public String editGet(@PathVariable final Long id, final Model model){
        BannerEntity bannerEntity = bannerAdminService.getBanner(id);

        BannerAdminResponse bannerResponse = bannerMapper.entityToResponse(bannerEntity);

        LanguageEntity language = languageService.findDefault();
        List<LanguageResponse> languages = languageService.getActives();
        model.addAttribute("languages",languages);
        model.addAttribute("defaultLang",language);

        model.addAttribute("object", bannerResponse);
        model.addAttribute("id",id);
        model.addAttribute("image",bannerEntity.getImage());
        model.addAttribute("dropzoneKey",BannerEntity.class.getName());

        return "admin/banner/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable("id") final Long id,
                           @Valid @ModelAttribute("object") BannerAdminRequest request,
                           final BindingResult result,
                           final HttpSession session,
                           final Model model,
                           final RedirectAttributes redirectAttributes){

        BannerEntity banner = bannerAdminService.getBanner(id);

        String dropzoneKey = BannerEntity.class.getName();

        String requestImage = fileSessionService.getImage(dropzoneKey,session);

        if(requestImage!=null){
            model.addAttribute("requestImage",requestImage);
        }else {
            String image = banner.getImage();
            model.addAttribute("image",fileGetService.getFileAbsoluteUrl(image,300,300));
        }
        if(result.hasErrors())
            return "admin/banner/edit";
        try{
            bannerAdminService.update(banner,request,session);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                model.addAttribute("error", ((BaseException) ex).getErrors());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            }

            return "admin/banner/edit";
        }

        return "redirect:/admin/banner";
    }




}
