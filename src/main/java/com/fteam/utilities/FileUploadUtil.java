package com.fteam.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

//	private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtil.class);
	
	public static void saveFile(String uploadDir, String fileName, //
			MultipartFile multipartFile) throws IOException {

		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("Could not save file: " + fileName, e);
		}
	}

	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);

		try {
			Files.list(dirPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
//						LOGGER.error("Could not delete file: " + file);
						System.out.println("Could not delete file: " + file);
					}
				}
			});
		} catch (Exception e) {
//			LOGGER.error("Could not list directory: " + dirPath);
			System.out.println("Could not list directory: " + dirPath);
		}
	}
	
	public static void deleteFile(HttpServletRequest request, String folder, String fileName) {
		File dir = new File(request.getServletContext().getRealPath(folder));
		File fileToDelete = new File(dir, fileName);
		if (fileToDelete.isFile()) {
			fileToDelete.delete();
		}
	}

}