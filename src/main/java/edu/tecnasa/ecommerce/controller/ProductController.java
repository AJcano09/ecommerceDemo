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

import edu.tecnasa.ecommerce.dao.IProductDAO;
import edu.tecnasa.ecommerce.dto.ProductDto;
import edu.tecnasa.ecommerce.entities.Product;
import edu.tecnasa.ecommerce.errors.ResourceNotFoundException;
import edu.tecnasa.ecommerce.service.FileStorageService;

@RestController("productController")
public class ProductController {
	
	@Inject
	private IProductDAO productDao;
	
	@Inject
	private FileStorageService fileStorageService;
	
	@RequestMapping(value="/api/Products", method=RequestMethod.GET)
	public ResponseEntity<Page<ProductDto>> getProducts(Pageable pageable) {
		
		Page<ProductDto> result = productDao.findAll(pageable).map(new ProductToProductDto());
		
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/api/Product/{id}", method=RequestMethod.GET)
	public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
		
		Optional<ProductDto> result = productDao.findById(id).map(new ProductToProductDto());
		
		return new ResponseEntity<>(result.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/Product", method=RequestMethod.POST)
	public ResponseEntity<?> newProduct(@RequestBody Product product) {
		
		product.setProductId(null);
		product = productDao.save(product);
		
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newUri = ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(product.getId())
						.toUri();
		responseHeaders.setLocation(newUri);
		
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/api/Product/{id}", method=RequestMethod.PUT)
	public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		
		verifyProduct(id);
		
		product.setProductId(id);
		
		// Save the entity
		product = productDao.save(product);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/Product/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
		verifyProduct(id);
		
		productDao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Product verifyProduct(Long id) throws ResourceNotFoundException {
		Optional<Product> Product = productDao.findById(id);
		if(!Product.isPresent()){
			throw new ResourceNotFoundException("Product #" + id + " not found.");
		}
		return Product.get();
	}
	
	@RequestMapping(value="/api/Product/{id}/file", method= {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {
		
		Product product = verifyProduct(id);

		fileStorageService.storeFile(product, file);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/Product/{id}/file", method= RequestMethod.GET)
	public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws Exception {
		Product product = verifyProduct(id);

		fileStorageService.streamFile(request, response, product, FileStorageService.ImageType.NORMAL);
	}
	
	@RequestMapping(value="/api/Product/{id}/thumbnail", method=RequestMethod.GET)
	public void getThumbnail(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
		
		Product product = verifyProduct(id);

		fileStorageService.streamFile(request, response, product, FileStorageService.ImageType.THUMBNAIL);
	}
	
	@RequestMapping(value="/api/Product/GetProductsByCategory", method=RequestMethod.GET)
	public ResponseEntity<Page<ProductDto>> getProductsByCategory(Pageable pageable, @RequestParam("idcategory") Long idCategory) {
		
		Page<ProductDto> result = productDao.SearchByCategoryId(pageable, idCategory).map(new ProductToProductDto());
		
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	private class ProductToProductDto implements Function<Product, ProductDto> {
		@Override
		public ProductDto apply(Product t) {
			
			return new ProductDto(t.getId(),
					t.getProductTitle(),
					t.getProductPrice(),
					t.getProductSpecial(),
					t.getProductDescriptions(),
					t.getCategories(),
					String.format("/api/Product/%d/thumbnail", t.getId()), 
					String.format("/api/Product/%d/file", t.getId()));
		}
	}
	
}

