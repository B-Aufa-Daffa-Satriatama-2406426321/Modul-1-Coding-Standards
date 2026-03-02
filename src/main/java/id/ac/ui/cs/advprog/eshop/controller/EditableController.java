package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.ui.Model;

public interface EditableController<T> {
    
    String editItemPage(String itemId, Model model);
    String editItemPost(T item, Model model);
}
