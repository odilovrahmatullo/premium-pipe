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
import premium_pipe.entity.GalleryEntity;
import premium_pipe.exception.BaseException;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.admin.GalleryAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequestMapping("/admin/gallery")
@RequiredArgsConstructor
public class GalleryAdminController {
    private final GalleryAdminService galleryAdminService;
    private final FileSessionService fileSessionService;
    private final FileGetService fileGetService;

    @GetMapping("/create")
    public String create(final Model model) {
        model.addAttribute("dropzoneKey", GalleryEntity.class.getName());
        return "admin/gallery/create";
    }

    @PostMapping("/create")
    public String create(final RedirectAttributes redirectAttributes,
                         final HttpSession session,
                         final Model model) {
        String dropzoneKey = GalleryEntity.class.getName();
        String image = fileSessionService.getImage(dropzoneKey,session);
        model.addAttribute("requestImage", image);
        try {
            galleryAdminService.create(session);
        } catch (Exception exception) {
            if(exception instanceof BaseException){
                model.addAttribute("error",((BaseException) exception).getErrors());
            }else {
                redirectAttributes.addFlashAttribute("errorMessages", exception.getMessage());
            }
            return "admin/gallery/create";
        }
        return "redirect:/admin/gallery";
    }

    @GetMapping({"", "/"})
    public String list(final RequestParams params, final Model model) {
        Page<GalleryResponse> galleries = galleryAdminService.getGalleries(params);
        int totalPages = galleries.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        model.addAttribute("objects", galleries);
        model.addAttribute("page", params.page());
        model.addAttribute("size", params.size());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pages", pagination);
        return "admin/gallery/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            galleryAdminService.delete(id);
        } catch (Exception e) {
            model.addAttribute("deleteError", e.getMessage());
            return "redirect:/admin/gallery";
        }
        return "redirect:/admin/gallery";
    }


}
