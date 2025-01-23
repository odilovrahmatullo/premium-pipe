package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.model.request.GalleryRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.GalleryResponse;
import premium_pipe.service.admin.GalleryAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequestMapping("/admin/gallery")
@RequiredArgsConstructor
public class GalleryAdminController {
    private final GalleryAdminService galleryAdminService;

    @GetMapping("/create")
    public String create(final Model model){
        GalleryRequest request = new GalleryRequest("");
        model.addAttribute("object", request);
        return "admin/gallery/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("object") final GalleryRequest request,
                         final BindingResult result,
                         final RedirectAttributes redirectAttributes,
                         final Model model){
        if(result.hasErrors()) {
            model.addAttribute("object",request);
            return "admin/gallery/create";
        }

        try{
            galleryAdminService.create(request);
        }
        catch (Exception exception){
            redirectAttributes.addFlashAttribute("errorMessages", exception.getMessage());
        }
        redirectAttributes.addAttribute("successMessage","SUCCESS");
        return "redirect:/admin/gallery";
    }

    @GetMapping({"","/"})
    public String list(final RequestParams params, final Model model){
        Page<GalleryResponse> galleries = galleryAdminService.getGalleries(params);
        int totalPages = galleries.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages,params.page());
        model.addAttribute("objects",galleries);
        model.addAttribute("page",params.page());
        model.addAttribute("size",params.size());
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pages",pagination);
        return "admin/gallery/admin";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id,
                      final Model model){
        GalleryResponse galleryResponse = galleryAdminService.getGalleryResponse(id);
        model.addAttribute("object",galleryResponse);
        return "admin/gallery/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("object") GalleryRequest request,
                       final BindingResult result,
                       final RedirectAttributes redirectAttributes,
                       final Model model){
        if(result.hasErrors()){
            model.addAttribute("object",request);
            return "admin/gallery/edit";
        }
        try{
            galleryAdminService.update(id,request);
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("object",request);
        }

        redirectAttributes.addFlashAttribute("successMessage","Gallery successfully updated");
        return "admin/gallery/edit";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try{
            galleryAdminService.delete(id);
        }
        catch (Exception e){
            model.addAttribute("deleteError",e.getMessage());
        }
        redirectAttributes.addFlashAttribute("successMessage","Successfully deleted");

        return "redirect:/admin/gallery";
    }


}
