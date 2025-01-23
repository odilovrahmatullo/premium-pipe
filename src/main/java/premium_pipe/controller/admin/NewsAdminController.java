package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.model.request.NewsRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.NewsAdminResponse;
import premium_pipe.service.admin.NewsAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/news")
public class NewsAdminController {
    private final NewsAdminService newsAdminService;

    @GetMapping("/create")
    public String create(Model model){
        NewsRequest news = new NewsRequest();
        model.addAttribute("object",news);
        return "admin/news/create";
    }

    @PostMapping("/create")
    public String createNews(@Valid @ModelAttribute("object") final NewsRequest newsRequest,
                             final BindingResult result,
                             final Model model,
                             final RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            model.addAttribute("object",newsRequest);
            return "admin/newss/create";
        }
        try {
            newsAdminService.create(newsRequest);
        }
        catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("object",newsRequest);
        }
        redirectAttributes.addFlashAttribute("successMessage","SUCCESS");
        return "redirect:/admin/news";
    }

    @GetMapping({"","/"})
    public String list(final RequestParams params, final Model model){
        Page<NewsAdminResponse> news = newsAdminService.getNews(params);
        int totalPages = news.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages,params.page());
        model.addAttribute("page",params.page());
        model.addAttribute("size",params.size());
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pagination",pagination);
        model.addAttribute("objects",news);
        return "admin/news/list";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") Long id,
                      final Model model){
        NewsAdminResponse response = newsAdminService.getNewsResponse(newsAdminService.getNewsEntity(id));
        model.addAttribute("object",response);
        model.addAttribute("id",id);
        return "admin/news/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("object") final NewsRequest newsRequest,
                       final BindingResult result,
                       final RedirectAttributes redirectAttributes,
                       final Model model){
        if(result.hasErrors()){
            model.addAttribute("object",newsRequest);
            return "admin/news/edit";
        }
        try{
            newsAdminService.update(id,newsRequest);
        }
        catch (Exception e){
            model.addAttribute("object",newsRequest);
            redirectAttributes.addFlashAttribute("errorMessage",e.getMessage());
            return "admin/news/edit";
        }

        redirectAttributes.addFlashAttribute("successMessage","SUCCESS");
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") final Long id,
                         final Model model,
                         final RedirectAttributes redirectAttributes){
        try {
            newsAdminService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage","Successfully deleted");
        }
        catch (Exception e){
            model.addAttribute("deleteError",e.getMessage());
        }
        return "redirect:/admin/news";
    }
}
