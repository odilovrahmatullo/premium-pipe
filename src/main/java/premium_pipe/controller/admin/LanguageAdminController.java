package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.model.request.LanguageRequest;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.service.admin.LanguageAdminService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/language")
public class LanguageAdminController {
    private final LanguageAdminService languageAdminService;

    @GetMapping("/create")
    public String create(final Model model){
        LanguageRequest lang = new LanguageRequest();
        model.addAttribute("object",lang);
        return "admin/language/create";
    }

    @PostMapping("/create")
    public String createLang(@Valid @ModelAttribute("object") LanguageRequest languageRequest,
                             final BindingResult result,
                             final RedirectAttributes redirectAttributes,
                             final Model model){
        if(result.hasErrors()){
            model.addAttribute("object",languageRequest);
            return "admin/language/create";
        }
        try{
            languageAdminService.create(languageRequest);
        } catch (Exception e){
            model.addAttribute("createError");
            return "admin/language/create";
        }
        redirectAttributes.addFlashAttribute("successMessage","Successfully Created Language");
        return "redirect:/admin/language";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") Long id,Model model){
        LanguageResponse lang = languageAdminService.getLang(id);
        model.addAttribute("object",lang);
        return "admin/language/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id")Long id,
                       @Valid @ModelAttribute("object") final LanguageRequest request,
                       final BindingResult result,
                       final Model model,
                       final RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            model.addAttribute("object",request);
            return "admin/language/edit";
        }
        try{
            languageAdminService.update(id,request);
        }catch (Exception e){
            model.addAttribute("object",request);
            return "admin/language/edit";
        }
        redirectAttributes.addFlashAttribute("successMessage","Successfully updated");
        return "redirect:/admin/language";
    }
}
