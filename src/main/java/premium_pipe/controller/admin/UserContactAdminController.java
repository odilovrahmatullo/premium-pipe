package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.UserContactResponse;
import premium_pipe.service.admin.UserContactAdminService;
import premium_pipe.util.Paginate;
import java.util.List;

@Controller
@RequestMapping("/admin/contact")
@RequiredArgsConstructor
public class UserContactAdminController {
    private final UserContactAdminService userContactAdminService;

    @GetMapping({"", "/"})
    public String list(final @Valid RequestParams params, final Model model) {
        Page<UserContactResponse> contactList = userContactAdminService.getContactList(params);
        int totalPages = contactList.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        model.addAttribute("objects", contactList);
        model.addAttribute("size", params.size());
        model.addAttribute("page", params.page());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pagination", pagination);
        return "admin/contact/list";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userContactAdminService.deleteBy(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/contact";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Successfully deleted");
        return "redirect:/admin/contact";
    }
}
