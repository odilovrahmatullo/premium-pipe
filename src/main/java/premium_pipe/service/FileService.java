package premium_pipe.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import premium_pipe.entity.FileEntity;
import premium_pipe.enums.FileType;
import premium_pipe.exception.NotFoundException;
import premium_pipe.mapper.FileMapper;
import premium_pipe.model.response.FileResponse;
import premium_pipe.repository.FileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileMapper mapper;
    @Value("${upload.url}")
    private String domainName;
    private String folderName = "files";

    public void upload(MultipartFile file) {
        String pathFolder = getYmDString();
        String key = UUID.randomUUID().toString();
        String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));

        File folder = new File(folderName + "/" + pathFolder);
        if(!folder.exists()){
            folder.mkdirs();
        }
        try {
            byte [] bytes = file.getBytes();
            Path path = Paths.get(folderName+"/"+pathFolder+"/"+key+"."+extension);
            Files.write(path,bytes);
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(key+"."+extension);
            fileEntity.setPath(pathFolder);
            fileEntity.setExtension(extension);
            fileEntity.setOriginName(file.getOriginalFilename());
            fileEntity.setSize(file.getSize());
            fileRepository.save(fileEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public FileType setFileType(FileEntity file) {
        Path filePath = Paths.get(file.getPath());
        if (filePath.startsWith("video")) {
            return FileType.VIDEO;
        } else if (filePath.startsWith("image")) {
            return FileType.PHOTO;
        }
        throw new NotFoundException("File type not recognized");
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }


    public ResponseEntity<Resource> open(String file) {
        FileEntity entity = getFileEntity(file);
        Path filePath = Paths.get(folderName + "/" + entity.getPath() + "/" + entity.getId()).normalize();
        Resource resource = null;
        try{
            resource = new UrlResource(filePath.toUri());
            if(!resource.exists()){
                throw new NotFoundException("Resource not found ");
            }
            String contentType = Files.probeContentType(filePath);
            if(contentType==null){
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public FileResponse getById(String id){
        FileResponse fileResponse = mapper.toDTO(getFileEntity(id));
        fileResponse.setUrl(getUrl(fileResponse.getId()));
        return fileResponse;
    }

    public FileEntity getFileEntity(String id){
        return fileRepository.getById(id).orElseThrow(() ->
                new NotFoundException("File not found"));
    }

    public String getUrl(String id) {
        return domainName + "/api/file/open/" + id;
    }

}
