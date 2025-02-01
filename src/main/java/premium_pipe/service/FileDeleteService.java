package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import premium_pipe.exception.BaseException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final FileThumbnailService fileThumbnailService;

    public boolean deleteFile(final String filePath) {
        File file = new File("uploads/",filePath.replace("uploads",""));
        String thumbPath = fileThumbnailService.getThumbFolder(filePath);
        deleteThumbnails(thumbPath);
        return file.delete();
    }

    public void deleteThumbnails(final String folderPath) {
        File folderToDelete = new File(folderPath);

        try {
            FileUtils.deleteDirectory(folderToDelete);
        } catch (IOException ex) {
            throw new BaseException(Map.of("error", ex.getMessage()), 403);
        }
    }
}
