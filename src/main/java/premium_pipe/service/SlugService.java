package premium_pipe.service;

import com.github.slugify.Slugify;
import org.springframework.stereotype.Service;

@Service
public class SlugService {
    public String generateSlug(final String value){
        Slugify slugify = Slugify.builder().transliterator(true).build();
        System.out.println("SLUG: " + slugify.slugify(value));
        return slugify.slugify(value);
    }
}
