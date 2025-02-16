package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import premium_pipe.enums.FileType;
import premium_pipe.exception.BaseException;
import premium_pipe.exception.NotFoundException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileGetService fileGetService;
    private final FileStoreService fileStoreService;

    public String saveFromApi(MultipartFile file, boolean save) throws IOException {
        fileStoreService.deleteExpiredFiles();
        String filePath = saveFile(file);
        if(!save)
            fileStoreService.saveFile(filePath);
        return filePath;
    }

    private String saveFile(final MultipartFile file) throws IOException {
        String savedFilePath;
        byte [] fileBytes = file.getBytes();
        String dirPath = getFileUploadDir();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = getNewFileName(extension);
        File dir = new File(dirPath);

        if(!dir.exists()){
            boolean folderCreated = dir.mkdirs();
            if(!folderCreated){
                throw new BaseException(Map.of("error","Error with file uploading"),403);
            }
        }

        try{
            String savedFileFullPath = dir.getAbsolutePath()+"/";
            File savedFile = new File(savedFileFullPath,fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(savedFile));

            stream.write(fileBytes);
            stream.close();

            savedFilePath = fileGetService.getCorrectFilePath(savedFile.getPath());
        }catch (Exception e){
            throw new BaseException(Map.of("error",e.getMessage()),403);
        }
        return savedFilePath;

    }
    private String getNewFileName(String extension) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.format("%d.%s", timestamp.getTime(), extension);
    }

    private String getFileUploadDir() {
        String dirPath;
        LocalDate currentDate = LocalDate.now();
        dirPath =
                "uploads/"
                        + String.format(
                        "%s/%s/%s/",
                        currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());

        return dirPath;
    }

    public FileType setFileType(String filePath){
        if(!filePath.contains(".")) {
            throw new NotFoundException("File type not recognized");
        }
        int lastedIndex = filePath.lastIndexOf('.');
        String extension =  filePath.substring(lastedIndex+1);
        return switch (extension) {
            case "jpg", "jpeg", "png", "gif","webp" -> FileType.IMAGE;
            case "mp4", "mkv", "avi","mov","webm","f4v" -> FileType.VIDEO;
            default -> throw new NotFoundException("File type not recognized");
        };
    }
}
