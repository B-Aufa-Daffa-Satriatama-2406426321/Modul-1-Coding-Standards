package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.ui.Model;

public interface CreatableController<T> {
    
    String createItemPage(Model model);
    String createItemPost(T item, Model model);
}
