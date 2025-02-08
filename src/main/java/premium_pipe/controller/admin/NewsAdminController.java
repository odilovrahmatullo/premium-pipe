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
import premium_pipe.entity.LanguageEntity;
import premium_pipe.entity.NewsEntity;
import premium_pipe.exception.BaseException;
import premium_pipe.model.request.NewsRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.model.response.admin.NewsAdminResponse;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.LanguageService;
import premium_pipe.service.admin.NewsAdminService;
import premium_pipe.util.Paginate;

import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/news")
public class NewsAdminController {
    private final NewsAdminService newsAdminService;
    private final FileSessionService fileSessionService;
    private final FileGetService fileGetService;
    private final LanguageService languageService;

    private void setCommonAttributes(Model model, HttpSession session, NewsRequest newsRequest) {
        List<LanguageResponse> languages = languageService.getActives();
        String dropzoneKey = NewsEntity.class.getName();
        LanguageEntity defaultLang = languageService.findDefault();
        if (session != null) {
            String image = fileSessionService.getImage(dropzoneKey, session);
            model.addAttribute("requestImage", image);
        }
        model.addAttribute("defaultLang",defaultLang);
        model.addAttribute("languages", languages);
        model.addAttribute("dropzoneKey", dropzoneKey);
        model.addAttribute("object", newsRequest);
    }

    @GetMapping("/create")
    public String create(Model model) {
        NewsRequest news = new NewsRequest();
        setCommonAttributes(model, null, news);
        return "admin/news/create";
    }

    @PostMapping("/create")
    public String createNews(@Valid @ModelAttribute("object") final NewsRequest newsRequest,
                             final BindingResult result,
                             final Model model,
                             final HttpSession session,
                             final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            setCommonAttributes(model, session, newsRequest);
            return "admin/news/create";
        }

        try {
            newsAdminService.create(newsRequest, session);
        } catch (BaseException e) {
            model.addAttribute("error", e.getErrors());
            return "admin/news/create";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "admin/news/create";
        }

        return "redirect:/admin/news";
    }

    @GetMapping({"", "/"})
    public String list(final RequestParams params, final Model model) {
        Page<NewsAdminResponse> news = newsAdminService.getNews(params);
        int totalPages = news.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        LanguageEntity defaultLang = languageService.findDefault();

        model.addAttribute("defaultLang", defaultLang);
        model.addAttribute("page", params.page());
        model.addAttribute("size", params.size());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pagination", pagination);
        model.addAttribute("objects", news);

        return "admin/news/list";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id, final Model model) {
        NewsAdminResponse response = newsAdminService.getNewsResponse(newsAdminService.getNewsEntity(id));
        setCommonAttributes(model, null, null);
        model.addAttribute("image", response.getImage());
        model.addAttribute("object", response);
        model.addAttribute("id", id);
        return "admin/news/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("object") final NewsRequest newsRequest,
                       final BindingResult result,
                       final RedirectAttributes redirectAttributes,
                       final Model model,
                       final HttpSession session) {
        NewsEntity news = newsAdminService.getNewsEntity(id);
        String requestImage = fileSessionService.getImage(NewsEntity.class.getName(), session);
        model.addAttribute("requestImage",requestImage);
        if(requestImage!=null){
            model.addAttribute("requestImage",requestImage);
        }else{
            String image = news.getImage();
            model.addAttribute("image",fileGetService.getFileAbsoluteUrl(image,300,300));
        }
        if (result.hasErrors())
            return "admin/news/edit";

        try {
            newsAdminService.update(news, newsRequest, session);
        } catch (Exception e) {
            model.addAttribute("object", newsRequest);
            model.addAttribute("requestImage", requestImage);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "admin/news/edit";
        }

        redirectAttributes.addFlashAttribute("successMessage", "SUCCESS");
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") final Long id,
                         final Model model,
                         final RedirectAttributes redirectAttributes) {
        try {
            newsAdminService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully deleted");
        } catch (Exception e) {
            model.addAttribute("deleteError", e.getMessage());
        }
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/deleteImage")
    public ResponseEntity<?> deleteImage(@PathVariable("id") final Long id) {
        try {
            newsAdminService.deleteImage(id);
        } catch (Exception exception) {
            return ResponseEntity.status(403).body(Map.of("error", exception.getMessage()));
        }
        return ResponseEntity.ok().build();
    }
}
