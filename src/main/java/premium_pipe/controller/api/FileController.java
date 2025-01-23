package premium_pipe.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import premium_pipe.model.response.FileResponse;
import premium_pipe.service.FileService;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping()
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file){
        try{
            fileService.upload(file);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/open/{file}")
    public ResponseEntity<Resource> open(@PathVariable("file") String file){
        try{
            return fileService.open(file);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponse> getById(@PathVariable("id") String id){
        try {
           return ResponseEntity.ok(fileService.getById(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
