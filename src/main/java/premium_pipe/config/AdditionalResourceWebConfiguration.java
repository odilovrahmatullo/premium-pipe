package premium_pipe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/api/files/uploads/**")
        .addResourceLocations("file:uploads\\")
        .setCachePeriod(3600)
        .resourceChain(true);
  }
}
