package premium_pipe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import premium_pipe.exception.BaseException;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileThumbnailService {
    public String getThumbFolder(final String filePath) {
        String fileExt = "." + FilenameUtils.getExtension(filePath);
        String fileName = FilenameUtils.getName(filePath).replace(fileExt, "");

        String fileNameWithExt = fileName + fileExt;

        String mainFolderPath = new File(filePath).getAbsolutePath().replace(fileNameWithExt, "");

        String folderPath = String.format("%s_THUMBS/", fileName);

        return mainFolderPath + folderPath;
    }


    public String getThumbnailImage(String filePath, int width, int height, String format) {
        String thumbFilePath = getThumbFilePath(filePath, String.format("%dx%d", width, height), format);

        thumbFilePath = Arrays.stream(thumbFilePath.split("/uploads/")).toList().getLast();

        boolean fileExists = new File("uploads/", thumbFilePath).exists();

        if (!fileExists) {
            thumbFilePath = generateThumbnailImage(filePath, width, height, format);
        }

        return thumbFilePath == null ? "" : thumbFilePath;
    }

    public String getThumbFilePath(final String filePath, final String size, final String format) {
        String fileExt = "." + FilenameUtils.getExtension(filePath);
        String fileName = FilenameUtils.getName(filePath).replace(fileExt, "");

        String path = getThumbFolder(filePath) + fileName;

        return String.format("%s.%s.%s", path, size, format);
    }

    public String generateThumbnailImage(
            final String filePath, final int width, final int height, final String format) {

        String thumbFilePath = getThumbFilePath(filePath, String.format("%dx%d", width, height), format);

        String folderPath = getThumbFolder(filePath);

        File dir = new File(folderPath);

        if (!dir.exists()) {
            boolean folderCreated = dir.mkdirs();

            if (!folderCreated) {
                log.error("Failed to create directory: {}", folderPath);
                throw new BaseException(Map.of("error", "Folder cannot be created"), 403);
            }
        }
        try {
            Thumbnails.of(filePath)
                    .size(width, height)
                    .outputFormat(format)
                    .toFile(thumbFilePath);
        } catch (Exception e) {
            thumbFilePath = null;
        }
        return thumbFilePath;
    }
}
