package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProductController implements ItemController<Product> {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping("/product/create")
    public String createItemPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @Override
    @PostMapping("/product/create")
    public String createItemPost(@ModelAttribute Product product, Model model){
        productService.create(product);
        return "redirect:list";
    }

    @Override
    @GetMapping("/product/list")
    public String listItemPage(Model model){
        List<Product> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @Override
    @GetMapping("/product/edit/{productId}")
    public String editItemPage(@PathVariable("productId") String productId, Model model){
        Product product = productService.findByProductId(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @Override
    @PostMapping("/product/edit")
    public String editItemPost(@ModelAttribute Product product, Model model){
        System.out.println(product.getProductId());
        productService.update(product.getProductId().toString(), product);
        return "redirect:list";
    }

    @Override
    @PostMapping("/product/delete")
    public String deleteItem(@RequestParam("productId") String productId){
        productService.deleteProductById(productId);
        return "redirect:list";
    }

    @GetMapping("/")
    public String home() {
        return "Home";
    }
}


