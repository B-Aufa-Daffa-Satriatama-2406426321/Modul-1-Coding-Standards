package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.ui.Model;

public interface ReadableController<T> {
    
    String listItemPage(Model model);
}
