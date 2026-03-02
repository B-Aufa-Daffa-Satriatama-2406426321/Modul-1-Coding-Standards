package id.ac.ui.cs.advprog.eshop.controller;

public interface ItemController<T> extends 
        ReadableController<T>, 
        CreatableController<T>, 
        EditableController<T>, 
        DeletableController {
}
