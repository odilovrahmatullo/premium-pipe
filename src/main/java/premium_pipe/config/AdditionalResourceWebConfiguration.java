package premium_pipe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    String uploadsPath = "file:" + Paths.get("uploads").toAbsolutePath().toString() + "/";

    registry.addResourceHandler("/uploads/**")
            .addResourceLocations(uploadsPath)
            .setCachePeriod(3600)
            .resourceChain(true);
  }
}
