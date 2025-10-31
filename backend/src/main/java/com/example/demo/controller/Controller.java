import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products") // Base URL for all product endpoints
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. GET for Product look up (All or Single)

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //  2. POST for Product creation

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        // Returns the created resource with a 201 Created status
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    // 3. PATCH for Product update 

    // PATCH /api/products/{id}
    // Using @RequestBody to receive the partial update data
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            // Handle the "Product not found" exception from the service layer
            return ResponseEntity.notFound().build();
        }
    }


    //  4. DELETE for Product removal

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            // 204 No Content is the standard response for successful DELETE
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Handle the "Product not found" exception
            return ResponseEntity.notFound().build();
        }
    }
}
