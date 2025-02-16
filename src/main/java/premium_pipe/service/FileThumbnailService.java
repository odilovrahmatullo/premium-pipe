package premium_pipe.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import premium_pipe.exception.BaseException;

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

    public String getThumbFilePath(final String filePath, final String size, final String format) {
        String fileExt = "." + FilenameUtils.getExtension(filePath);
        String fileName = FilenameUtils.getName(filePath).replace(fileExt, "");

        String path = getThumbFolder(filePath) + fileName;

        return String.format("%s.%s.%s", path, size, format);
    }


    public String getThumbnailImage(final String filePath, final int width, final int height, final String format) {
        // OS-ga mos yo‘lni olish
        String thumbFilePath = getThumbFilePath(filePath, String.format("%dx%d", width, height), format);

        // **OS mustaqil yo‘lni olish**
        String normalizedThumbFilePath = Paths.get(thumbFilePath).normalize().toString();

        boolean fileExists = new File(normalizedThumbFilePath).exists();

        if (!fileExists) {
            normalizedThumbFilePath = generateThumbnailImage(filePath, width, height, format);
        }

        return normalizedThumbFilePath == null ? "" : normalizedThumbFilePath;
    }


    public String generateThumbnailImage(
            final String filePath, final int width, final int height, final String format) {

        String thumbFilePath = getThumbFilePath(filePath, String.format("%dx%d", width, height), format);

        String folderPath = getThumbFolder(filePath);

        File dir = new File(folderPath);

        if (!dir.exists()) {
            boolean folderCreated = dir.mkdirs();

            if (!folderCreated) {
                throw new BaseException(Map.of("error", "Folder cannot be created"), 403);
            }
        }

        try {
            Thumbnails.of(filePath).size(width, height).outputFormat(format).toFile(thumbFilePath);
        } catch (Exception e) {
            thumbFilePath = null;
        }

        return thumbFilePath;
    }

    public void saveThumbnailAsWebp(final BufferedImage bufferedImage, final String savePath)
            throws IOException {
        File outputFile = new File(FilenameUtils.getName(savePath));

        ImageIO.write(bufferedImage, "webp", outputFile);
    }
}
