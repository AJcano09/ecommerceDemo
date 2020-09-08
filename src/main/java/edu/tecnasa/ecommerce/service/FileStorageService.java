package edu.tecnasa.ecommerce.service;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.tecnasa.ecommerce.entities.Identifiable;
import edu.tecnasa.ecommerce.errors.EcommerceGeneralException;
import edu.tecnasa.ecommerce.util.FileStorageProperties;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileStorageService {

	@Inject
	private FileStorageProperties fileStorageProperties;
	
	public enum ImageType {
		NORMAL,THUMBNAIL 
	}
	
	public void streamFile(HttpServletRequest request, HttpServletResponse response, Identifiable entity,
			ImageType imageType) {

		try {
			File file = findFile(entity, imageType);

			if (file == null) {
				if (imageType == FileStorageService.ImageType.NORMAL) {
					response.sendRedirect("http://placehold.it/1110x480");
				} else {
					response.sendRedirect("http://placehold.it/270x171");
				}

				return;
			}

			// Try to determine file's content type
			String contentType = null;
			contentType = request.getServletContext().getMimeType(file.getAbsolutePath());

			// Fallback to the default content type if type could not be determined
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			response.setContentType(contentType);

			IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (Exception e) {
			throw new EcommerceGeneralException(EcommerceGeneralException.getCausaOriginal(e).getMessage());
		}
	}
	
	private File findFile(Identifiable entity, ImageType imageType) {
		if(entity==null || entity.getId()==null) return null;
		
		File[] files = listFiles(entity);
		if(files==null || files.length<=0) return null;
		
		for(File f:files) {
			if(f.getName().contains("thumb") && imageType==ImageType.THUMBNAIL) {
				return f;
			}
			
			if(!f.getName().contains("thumb") && imageType==ImageType.NORMAL) {
				return f;
			}
		}
		
		return null;
	}
	
	public File[] listFiles(Identifiable entity) {
		String folderPath = fileStorageProperties.getUploadDir() + File.separator + entity.getClass().getSimpleName();
		File folder = new File(folderPath);
		
		if(folder.exists() && folder.isDirectory()) {
			FileFilter fileFilter = new WildcardFileFilter(Arrays.asList(entity.getId() + "*.png", entity.getId() + "*.jpg"), IOCase.INSENSITIVE);
			File[] files = folder.listFiles(fileFilter);
			
			return files;
		}
		
		return null;
	}
	
	public File storeFile(Identifiable entity, MultipartFile file) {
		
		try {
			
			validateImageFile(file);
			
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String extension = FilenameUtils.getExtension(fileName).toLowerCase();
			
			String folderPath = fileStorageProperties.getUploadDir() + File.separator + entity.getClass().getSimpleName();
			File folder = new File(folderPath);
			FileUtils.forceMkdir(folder);
			
			// remove old files
			FileUtils.cleanDirectory(folder); 
			
			String path = folderPath + File.separator + entity.getId() + "." + extension;
			String thumbnailPath = folderPath + File.separator + entity.getId() + "-thumb." + extension;
	
			File mainFilePath = new File(path);
			
			//File 
			FileUtils.copyInputStreamToFile(file.getInputStream(), mainFilePath);
			
			BufferedImage bimg = ImageIO.read(mainFilePath);
			
			Thumbnails.of(bimg)
				.size(270, 171)
				.toFile(thumbnailPath);
			
			return mainFilePath;
			
		} catch(Exception e) {
			throw new EcommerceGeneralException(EcommerceGeneralException.getCausaOriginal(e).getMessage());
		}
		
	}
	
	private void validateImageFile(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = FilenameUtils.getExtension(fileName);
		
		if(!StringUtils.hasText(extension)) {
			throw new EcommerceGeneralException("File is not valid.");
		}
		
		if(!"jpg".equalsIgnoreCase(extension) && !"png".equalsIgnoreCase(extension)) {
			throw new EcommerceGeneralException("Only JPG and PNG extensions are allowed.");
		}
		
		String contentType = file.getContentType();
		
		if(!contentType.toLowerCase().startsWith("image/png") && 
				!contentType.toLowerCase().startsWith("image/x-png") && 
				!contentType.toLowerCase().startsWith("image/jpeg")) {
			throw new EcommerceGeneralException("Only JPG and PNG files are allowed.");
		}
	}
}
