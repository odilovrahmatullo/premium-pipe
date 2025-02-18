package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.entity.StaticTypeEntity;
import premium_pipe.model.StaticTypeRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.service.admin.StaticTypeAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequestMapping("/admin/type")
@RequiredArgsConstructor
public class StaticTypeAdminController {
    private final StaticTypeAdminService staticTypeAdminService;

    @GetMapping("/create")
    public String getCreate(Model model){
        StaticTypeRequest typeRequest = new StaticTypeRequest();
        model.addAttribute("object",typeRequest);
        return "admin/type/create";
    }

    @PostMapping("/create")
    public String postCreate(final StaticTypeRequest request,
                             final BindingResult result,
                             final RedirectAttributes redirectAttributes){
        if(result.hasErrors())
            return "admin/type/create";

        try{
            staticTypeAdminService.create(request);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("something went wrong");
        }
        return "redirect:/admin/type";
    }

    @GetMapping({"","/"})
    public String list(final RequestParams params, final Model model){
        Pageable pageable = PageRequest.of(params.page(),params.size());
        Page<StaticTypeEntity> types = staticTypeAdminService.getList(pageable);
        int totalPages = types.getTotalPages();
        List<Integer> pagination  = Paginate.get_pagination(totalPages,params.page());
        model.addAttribute("pages",pagination);
        model.addAttribute("objects",types);
        model.addAttribute("page",params.page());
        model.addAttribute("size",params.size());
        model.addAttribute("totalPages",totalPages);
        return "admin/type/list";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id, final Model model){
        StaticTypeEntity type = staticTypeAdminService.getEntity(id);
        model.addAttribute("object",type);
        model.addAttribute("id",id);
        return "admin/type/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("object")final StaticTypeRequest typeRequest,
            final BindingResult result,
            final Model model){

        if(result.hasErrors())
            return "admin/type/edit";

        try {
            staticTypeAdminService.update(id,typeRequest);
        }
        catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("object",typeRequest);
            return "admin/type/edit";
        }

        return "redirect:/admin/type";
    }

    @PostMapping("/{id}/delete")
    public String deleteType(@PathVariable("id") final Long id,
                                 final RedirectAttributes redirectAttributes,
                                 final Model model){
        try{
            staticTypeAdminService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Deleted");
        }   catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
        }
        return "redirect:/admin/type";
    }

}
