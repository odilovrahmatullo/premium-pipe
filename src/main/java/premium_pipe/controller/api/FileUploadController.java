package premium_pipe.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpHead;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import premium_pipe.service.CategoryService;
import premium_pipe.service.FileDeleteService;
import premium_pipe.service.FileGetService;
import premium_pipe.service.FileUploadService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;
    private final FileGetService fileGetService;
    private final FileDeleteService fileDeleteService;


    @PostMapping("/upload")
    public Map<String, String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "fileKey", defaultValue = "images") String fileKey,
            @RequestParam(value = "save", defaultValue = "false") boolean save,
            HttpServletResponse response,
            HttpServletRequest request,
            HttpSession session
    ) {
        Map<String, String> result = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                String fileSavedPath = fileUploadService.saveFromApi(file, save);
                String filePath = fileGetService.getCorrectFilePath(fileSavedPath);
                result.put("filePath", filePath);
                result.put("deletePath", fileSavedPath);
                result.put("showUrl", fileGetService.getShowUrl(filePath));

                if (save) {
                    Object sessionImagesObject = session.getAttribute(fileKey);
                    List<String> sessionImages;

                    if (sessionImagesObject instanceof List<?>) {
                        sessionImages = (List<String>) sessionImagesObject;
                    } else {
                        sessionImages = new ArrayList<>();
                        session.setAttribute(fileKey, sessionImages);
                    }
                    sessionImages.add(filePath);
                }

            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                result.put("error", e.toString());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            result.put("error", "file cannot be null");
        }
        return result;
    }


    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestParam("filePath") String filePath,
                                      @RequestParam(value = "fileKey", defaultValue = "images") String fileKey,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      HttpSession session){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();

        if (!filePath.isEmpty()) {
            try {
                Boolean deleted = fileDeleteService.deleteFile(filePath);
                result.put("deleted", deleted);
                Object sessionImagesObject = session.getAttribute(fileKey);

                if (sessionImagesObject instanceof List<?>) {
                    List<String> sessionImages = (List<String>) sessionImagesObject;
                    String fileDeletePath = fileGetService.getCorrectFilePath(filePath);
                    sessionImages.remove(fileDeletePath);
                }
            } catch (Exception e) {
                errors.put("error",e.getMessage());
            }
        }
        else{
            errors.put("error","filePath cannot be null");
        }
        if(!errors.isEmpty()){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return errors;
        }
        return result;
    }


}
