package com.dictionary.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileConfig.class);

    @Value("${dictionary.path.pattern}")
    private String pattern;

    @Value("${dictionary.app.audio.location}")
    private String location;

    @Value("${dictionary.app.audio.folder}")
    private String folder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        createDefaultFolder();
        registry.addResourceHandler(pattern)
                .addResourceLocations(location)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    private void createDefaultFolder(){
        File file = new File(folder);
        if (!file.exists()){
            boolean isFolderCreated = file.mkdir();
            LOGGER.debug("Is drive created ? {}", isFolderCreated);
        }
    }
}
