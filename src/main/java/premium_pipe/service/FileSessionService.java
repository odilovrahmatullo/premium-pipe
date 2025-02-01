package premium_pipe.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileSessionService {
    private final FileDeleteService fileDeleteService;
    public String getImage(final String fileKey, final HttpSession session) {
        List<String> sessionImages = (List<String>) session.getAttribute(fileKey);

        String image = null;

        if (sessionImages != null && !sessionImages.isEmpty()) {
            image = sessionImages.getLast();
        }

        return image;
    }

    public void deleteFilesFromSession(String filesKey, HttpSession session) {
        List<String> images = getImages(filesKey, session);

        if (images != null && !images.isEmpty()) {
            images.removeLast();
        }

        for (String image : images) {
            fileDeleteService.deleteFile(image);
        }

        List<String> emptyList = new ArrayList<>();

        session.setAttribute(filesKey, emptyList);
    }

    public List<String> getImages(final String fileKey, final HttpSession session) {
        Object sessionImages = session.getAttribute(fileKey);

        if (sessionImages == null) {
            return new ArrayList<>();
        }

        return (List<String>) sessionImages;
    }
}
