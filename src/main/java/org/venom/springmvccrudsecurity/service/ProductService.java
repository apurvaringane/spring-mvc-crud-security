package org.venom.springmvccrudsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.venom.springmvccrudsecurity.model.Product;
import org.venom.springmvccrudsecurity.repository.ProductRepository;
import org.venom.springmvccrudsecurity.model.Product;
import org.venom.springmvccrudsecurity.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> getAllProducts(){
        return repository.findAll();
    }




    public Product getProductById(int id){
        return repository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product){
        return repository.save(product);
    }

    public void removeProductById(int id){
        repository.deleteById(id);
    }

    public List<Product> searchProduct(String name){
        return repository.searchProduct(name);
    }
}
