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
import premium_pipe.entity.HomeEntity;
import premium_pipe.entity.LanguageEntity;
import premium_pipe.exception.BaseException;
import premium_pipe.exception.NotFoundException;
import premium_pipe.model.request.HomeRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.model.response.admin.HomeAdminResponse;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.LanguageService;
import premium_pipe.service.admin.HomeAdminService;
import premium_pipe.util.Paginate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/about")
@RequiredArgsConstructor
public class HomeAdminController {
    private final HomeAdminService homeAdminService;
    private final LanguageService languageService;
    private final FileSessionService fileSessionService;
    private final FileGetService fileGetService;

    @GetMapping("/create")
    public String create(final Model model) {
        HomeRequest home = new HomeRequest();
        List<LanguageResponse> languages = languageService.getActives();
        LanguageEntity defaultLang = languageService.findDefault();
        model.addAttribute("object", home);
        model.addAttribute("languages", languages);
        model.addAttribute("defaultLang", defaultLang);
        model.addAttribute("dropzoneKey", HomeEntity.class.getName());
        return "admin/about/create";
    }

    @PostMapping("/create")
    public String createPost(final Model model,
                             @ModelAttribute("object") HomeRequest homeRequest,
                             final BindingResult result,
                             final HttpSession session,
                             final RedirectAttributes redirectAttributes) {
        List<LanguageResponse> languages = languageService.getActives();

        LanguageEntity defaultLang = languageService.findDefault();

        String dropzoneKey = HomeEntity.class.getName();

        String image = fileSessionService.getImage(dropzoneKey, session);
        model.addAttribute("requestImage", image);

        if (result.hasErrors()) {
            model.addAttribute("languages", languages);
            model.addAttribute("defaultLang", defaultLang);
            model.addAttribute("object", homeRequest);
            return "admin/about/create";
        }

        try {
            homeAdminService.create(homeRequest, session);
        } catch (Exception ex) {
            if (ex instanceof BaseException) {
                model.addAttribute("error", ((BaseException) ex).getErrors());
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            }
            return "admin/about/create";
        }
        return "redirect:/admin/about";
    }

    @GetMapping({"", "/"})
    public String list(@Valid RequestParams params,
                       final Model model) {
        Page<HomeAdminResponse> homeList = homeAdminService.getHomes(params);
        int totalPages = homeList.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        LanguageEntity defaultLang = languageService.findDefault();
        model.addAttribute("defaultLang", defaultLang);
        model.addAttribute("objects", homeList);
        model.addAttribute("page", params.page());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pagination", pagination);
        model.addAttribute("size", params.size());
        return "admin/about/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") final Long id,
                         final RedirectAttributes redirectAttributes,
                         final Model model) {
        HomeEntity home = homeAdminService.getHome(id);
        try {
            homeAdminService.delete(home);
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("successMessage", "Successfully deleted");
        return "redirect:/admin/about";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id,
                      final Model model) {
        HomeEntity home = homeAdminService.getHome(id);
        List<LanguageResponse> languages = languageService.getActives();
        LanguageEntity defaultLang = languageService.findDefault();
        model.addAttribute("object", home);
        model.addAttribute("defaultLang", defaultLang);
        model.addAttribute("languages", languages);
        model.addAttribute("dropzoneKey", HomeEntity.class.getName());
        model.addAttribute("image", home.getImage());
        model.addAttribute("id", id);
        return "admin/about/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") final Long id,
                       @ModelAttribute("object") final HomeRequest request,
                       final BindingResult result,
                       final Model model,
                       final RedirectAttributes redirectAttributes,
                       final HttpSession session
    ) {
        HomeEntity home = homeAdminService.getHome(id);

        LanguageEntity defaultLang = languageService.findDefault();
        List<LanguageResponse> languages = languageService.getActives();
        model.addAttribute("id",id);

        String dropzoneKey = HomeEntity.class.getName();;
        String requestImage = fileSessionService.getImage(dropzoneKey,session);
        if(requestImage!=null){
            model.addAttribute("requestImage",requestImage);
        }
        else{
            String image = home.getImage();
            model.addAttribute("image",fileGetService.getFileAbsoluteUrl(image,300,300));
        }

        if (result.hasErrors()) {
            model.addAttribute("defaultLang", defaultLang);
            model.addAttribute("languages", languages);
            model.addAttribute("object", request);
            return "admin/about/edit";
        }
        try {
            homeAdminService.update(home, request, session);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully Updated");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "admin/about/edit";
        }

        return "redirect:/admin/about";

    }

    @PostMapping("/{id}/deleteImage")
    public ResponseEntity<?> deleteImage(@PathVariable("id") final Long id) {
        try {
            homeAdminService.deleteImage(id);
        } catch (Exception exception) {
            return ResponseEntity.status(403).body(Map.of("error", exception.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

}
