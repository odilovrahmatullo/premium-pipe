package premium_pipe.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import premium_pipe.model.request.ProductAdminRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.admin.ProductAdminResponse;
import premium_pipe.service.admin.ProductAdminService;
import premium_pipe.util.Paginate;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductAdminService productAdminService;

    @GetMapping("/create")
    public String createProduct(final Model model) {
        ProductAdminRequest productAdminRequest = new ProductAdminRequest();
        model.addAttribute("object", productAdminRequest);
        return "admin/product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("object") final ProductAdminRequest adminRequest,
                         final BindingResult bindingResult,
                         final RedirectAttributes redirectAttributes,
                         final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("object", adminRequest);
            return "admin/product/create";
        }
        try {
            productAdminService.createProduct(adminRequest);
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        redirectAttributes.addFlashAttribute("successMessage", "SUCCESS");
        return "redirect:/admin/product";
    }

    @GetMapping({"","/"})
    public String list(final RequestParams params, Model model) {
        Page<ProductAdminResponse> products = productAdminService.getProducts(params);
        int totalPages = products.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        model.addAttribute("objects", products);
        model.addAttribute("pages", pagination);
        model.addAttribute("page", params.page());
        model.addAttribute("size", params.size());
        model.addAttribute("totalPages", totalPages);

        return "admin/product/list";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id, final Model model) {
        ProductAdminResponse par = productAdminService.getByEntity(productAdminService.getProductEntity(id));
        model.addAttribute("object", par);
        return "admin/product/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") final Long id,
                       @Valid @ModelAttribute("object") ProductAdminRequest par,
                       final BindingResult result,
                       final RedirectAttributes redirectAttributes,
                       final Model model) {
        if (result.hasErrors()) {
            model.addAttribute("object", par);
            return "admin/product/edit";
        }
        try{
            productAdminService.update(id, par);
           }
        catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("object", par);
            return "admin/product/edit";
        }
        redirectAttributes.addAttribute("successMessage", "Successfully Updated");
        return "redirect:/admin/product";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") final Long id,
                                 RedirectAttributes redirectAttributes,
                                 final Model model) {
        try {
            productAdminService.delete(id);
            redirectAttributes.addAttribute("successMessage", "Product Successfully deleted");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/product";
    }
}
