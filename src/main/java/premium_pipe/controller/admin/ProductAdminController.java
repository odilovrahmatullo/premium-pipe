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
import premium_pipe.entity.ProductEntity;
import premium_pipe.model.request.ProductAdminRequest;
import premium_pipe.model.request.RequestParams;
import premium_pipe.model.response.LanguageResponse;
import premium_pipe.model.response.admin.CategoryAdminResponse;
import premium_pipe.model.response.admin.ProductAdminResponse;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileSessionService;
import premium_pipe.service.LanguageService;
import premium_pipe.service.ProductFileService;
import premium_pipe.service.admin.CategoryAdminService;
import premium_pipe.service.admin.ProductAdminService;
import premium_pipe.service.admin.ProductInfoAdminService;
import premium_pipe.util.Paginate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductAdminService productAdminService;
    private final LanguageService languageService;
    private final FileSessionService fileSessionService;
    private final CategoryAdminService categoryAdminService;
    private final ProductFileService productFileService;

    @GetMapping("/create")
    public String createProduct(final Model model) {
        ProductAdminRequest productAdminRequest = new ProductAdminRequest();
        setCommonAttributes(model, null, productAdminRequest);
        return "admin/product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("object") final ProductAdminRequest adminRequest,
                         final BindingResult bindingResult,
                         final RedirectAttributes redirectAttributes,
                         final Model model,
                         final HttpSession session) {
        if (bindingResult.hasErrors()) {
            setCommonAttributes(model, session, adminRequest);
            return "admin/product/create";
        }
        try {
            productAdminService.createProduct(adminRequest, session);
        } catch (Exception exception) {
            setCommonAttributes(model, session, adminRequest);
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        redirectAttributes.addFlashAttribute("successMessage", "SUCCESS");
        return "redirect:/admin/product";
    }

    @GetMapping({"", "/"})
    public String list(final RequestParams params, Model model) {
        Page<ProductAdminResponse> products = productAdminService.getProducts(params);
        int totalPages = products.getTotalPages();
        List<Integer> pagination = Paginate.get_pagination(totalPages, params.page());
        LanguageEntity defaultLang = languageService.findDefault();
        model.addAttribute("defaultLang", defaultLang);
        model.addAttribute("products", products);
        model.addAttribute("pages", pagination);
        model.addAttribute("page", params.page());
        model.addAttribute("size", params.size());
        model.addAttribute("totalPages", totalPages);
        return "admin/product/list";
    }

    @GetMapping("/{id}/edit")
    public String one(@PathVariable("id") final Long id, final Model model) {
        ProductEntity product = productAdminService.getProductEntity(id);
        ProductAdminResponse par = productAdminService.getByEntity(product);
        List<String> images = productFileService.getProductFiles(product);
        model.addAttribute("image", images.getFirst());
        setCommonAttributes(model, null, null);
        model.addAttribute("object", par);
        model.addAttribute("categoryId", par.getCategoryId());
        model.addAttribute("id", id);
        return "admin/product/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") final Long id,
                       @Valid @ModelAttribute("object") ProductAdminRequest par,
                       final BindingResult result,
                       final RedirectAttributes redirectAttributes,
                       final Model model,
                       final HttpSession session) {
        LanguageEntity language = languageService.findDefault();
        List<LanguageResponse> languages = languageService.getActives();
        ProductEntity product = productAdminService.getProductEntity(id);
        if (result.hasErrors()) {
            setCommonAttributes(model,session,par);
            model.addAttribute("categoryId", par.getCategoryId());
            return "admin/product/edit";
        }
        try {
            productAdminService.update(product, par, session);
        } catch (Exception e) {
            setCommonAttributes(model, session, par);
            model.addAttribute("categoryId", par.getCategoryId());
            return "admin/product/edit";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Successfully Updated");
        return "redirect:/admin/product";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") final Long id,
                         RedirectAttributes redirectAttributes) {
        try {
            productAdminService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product Successfully deleted");
        } catch (Exception e) {
            redirectAttributes.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/product";
    }

    @PostMapping("/{id}/deleteImage")
    public ResponseEntity<?> deleteImage(@PathVariable("id") final Long id) {
        try {
            productAdminService.deleteImage(id);
        } catch (Exception exception) {
            return ResponseEntity.status(403).body(Map.of("error", exception.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    private void setCommonAttributes(Model model, HttpSession session, ProductAdminRequest par) {
        List<LanguageResponse> languages = languageService.getActives();
        String dropzoneKey = ProductEntity.class.getName();
        LanguageEntity defaultLang = languageService.findDefault();
        List<CategoryAdminResponse> categories = categoryAdminService.getCategoryList();
        List<String> images = (session != null) ? fileSessionService.getImages(dropzoneKey, session) : new ArrayList<>();
        model.addAttribute("requestImage", images);
        model.addAttribute("object", par);
        model.addAttribute("categories", categories);
        model.addAttribute("defaultLang", defaultLang);
        model.addAttribute("dropzoneKey", dropzoneKey);
        model.addAttribute("languages", languages);
    }

}

