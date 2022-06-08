package com.fteam;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String dirName = "images/products";
		Path productPhotoDir = Paths.get(dirName);

		String productPhotosPath = productPhotoDir.toFile().getAbsolutePath();

		registry.addResourceHandler("/" + dirName + "/**") //
				.addResourceLocations("file:/" + productPhotosPath + "/");
	}

}
