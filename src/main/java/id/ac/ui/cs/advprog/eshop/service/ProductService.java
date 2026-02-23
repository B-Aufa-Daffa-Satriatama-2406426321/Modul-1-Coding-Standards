
package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public Product edit(Product product, String newName, int newQuantity);
    // public Product delete(Product product);
    public List<Product> findAll();
}


