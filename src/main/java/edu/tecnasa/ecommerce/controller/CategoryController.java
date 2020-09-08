package edu.tecnasa.ecommerce.controller;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.tecnasa.ecommerce.dao.ICategoryDAO;
import edu.tecnasa.ecommerce.dto.CategoryDto;
import edu.tecnasa.ecommerce.entities.Category;
import edu.tecnasa.ecommerce.errors.ResourceNotFoundException;
import edu.tecnasa.ecommerce.service.FileStorageService;

@RestController("categoryController")
public class CategoryController {

	@Inject
	private ICategoryDAO categoryDao;
	
	@Inject
	private FileStorageService fileStorageService;
	
	@RequestMapping(value="/api/Categories", method=RequestMethod.GET)
	public ResponseEntity<Page<CategoryDto>> getCategories(Pageable pageable) {
		
		Page<CategoryDto> result = categoryDao.findAll(pageable).map(new CategoryToCategoryDto());
		
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/api/Category/{id}", method=RequestMethod.GET)
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
		
		Optional<CategoryDto> result = categoryDao.findById(id).map(new CategoryToCategoryDto());
		
		return new ResponseEntity<>(result.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/Category", method=RequestMethod.POST)
	public ResponseEntity<?> newCategory(@RequestBody Category category) {
		
		category.setCategoryId(null);
		category = categoryDao.save(category);
		
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(category.getId())
						.toUri();
		responseHeaders.setLocation(newUri);
		
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/api/Category/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable Long id) {
		verifyCategory(id);
		
		category.setCategoryId(id);
		
		// Save the entity
		category = categoryDao.save(category);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping(value="/api/Category/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
		verifyCategory(id);
		
		categoryDao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Category verifyCategory(Long id) throws ResourceNotFoundException {
		Optional<Category> category = categoryDao.findById(id);
		if(!category.isPresent()){
			throw new ResourceNotFoundException("Category #" + id + " not found.");
		}
		return category.get();
	}
	
	@RequestMapping(value="/api/Category/{id}/file", method= {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {
		
		Category category = verifyCategory(id);

		fileStorageService.storeFile(category, file);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/Category/{id}/file", method= RequestMethod.GET)
	public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		Category category = verifyCategory(id);

		fileStorageService.streamFile(request, response, category, FileStorageService.ImageType.NORMAL);
	}
	
	@RequestMapping(value="/api/Category/{id}/thumbnail", method=RequestMethod.GET)
	public void getThumbnail(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		
		Category category = verifyCategory(id);

		fileStorageService.streamFile(request, response, category, FileStorageService.ImageType.THUMBNAIL);
		
	}
	
	private class CategoryToCategoryDto implements Function<Category, CategoryDto> {
		@Override
		public CategoryDto apply(Category t) {
			
			return new CategoryDto(t.getId(),
					t.getCategory_Title(),
					t.getCategory_Descriptions(), 
					String.format("/api/Category/%d/thumbnail", t.getId()), 
					String.format("/api/Category/%d/file", t.getId()));
		}
	}
}
