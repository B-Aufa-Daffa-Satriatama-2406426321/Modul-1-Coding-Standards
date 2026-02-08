package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Optional<Product> findByProductId(UUID id){
        return productData.stream()
            .filter(product -> product.getProductId().equals(id))
            .findFirst();
    }

    public void delete(Product product){
        productData.remove(product);
    }

    public Product save(Product product){
        Optional<Product> existing = findByProductId(product.getProductId());

        if (existing.isPresent()) {
            Product existingProduct = existing.get();
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductQuantity(product.getProductQuantity());
            return existingProduct;
        } else {
            productData.add(product);
            return product;
        }
    }


}
