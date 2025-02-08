package premium_pipe.controller.admin;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.entity.PartnerEntity;
import premium_pipe.exception.BaseException;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.PartnerResponse;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.admin.PartnerAdminService;
import premium_pipe.util.Paginate;
import java.util.List;

@RequestMapping("/admin/partner")
@Controller
@RequiredArgsConstructor
public class PartnerAdminController {
    private final PartnerAdminService partnerAdminService;
    private final FileSessionService fileSessionService;

    @GetMapping("/create")
    public String create(final Model model) {
        model.addAttribute("dropzoneKey", PartnerEntity.class.getName());
        return "admin/partner/create";
    }

    @PostMapping("/create")
    public String createPost(final Model model,
                             final RedirectAttributes redirectAttributes,
                             final HttpSession session) {
        String dropzoneKey = PartnerEntity.class.getName();
        String image = fileSessionService.getImage(dropzoneKey, session);
        try {
            partnerAdminService.create(session);
        } catch (Exception e) {
            if (e instanceof BaseException) {
                model.addAttribute("error", e.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "SUCCESSFULLY ADDED");
            }
            model.addAttribute("image", image);
            return "admin/partner/create";
        }
        return "redirect:/admin/partner";
    }

    @GetMapping({"","/"})
    public String list(final RequestParams params, final Model model) {
        Page<PartnerResponse> partners = partnerAdminService.getAll(params);
        int totalPages = partners.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        model.addAttribute("page", params.page());
        model.addAttribute("pagination", pagination);
        model.addAttribute("size", params.size());
        model.addAttribute("partners", partners);
        model.addAttribute("totalPages",totalPages);
        return "admin/partner/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        try {
            partnerAdminService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Partner not deleted");
        }
        return "redirect:/admin/partner";
    }

}
