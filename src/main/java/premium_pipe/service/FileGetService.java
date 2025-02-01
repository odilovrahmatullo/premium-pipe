package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;

@Service
@RequiredArgsConstructor
public class FileGetService {
    private final FileThumbnailService fileThumbnailService;
    @Value("${upload.url}")
    private String domainName;

    public String getCorrectFilePath(String path) {
        String[] pathSplit = path.split("uploads/");
        return "uploads/" + pathSplit[pathSplit.length-1];
    }
    public String getShowUrl(final String filePath) {
        if (filePath == null) return null;
        String newFilePath = getCorrectFilePath(filePath);
        if (newFilePath != null) {
            newFilePath = domainName + "api/files/" + newFilePath;
        }
        return newFilePath;
    }
    public String getFileAbsoluteUrl(final String photoUrl, final int width, final int height) {
        return getFileAbsoluteUrl(photoUrl, width, height, "jpeg");
    }

    public String getFileAbsoluteUrl(
            final String photoUrl, final int width, final int height, final String format) {
        if (photoUrl == null || photoUrl.isEmpty()) {
            return null;
        }

        String fileThumbUrl = fileThumbnailService.getThumbnailImage(photoUrl, width, height, format);

        if (fileThumbUrl == null || fileThumbUrl.isEmpty()) {
            return null;
        }

        return getShowUrl(fileThumbUrl);
    }
}
