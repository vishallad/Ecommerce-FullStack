package org.example.capstoneproject.Services;

import org.example.capstoneproject.Repositories.ProductRepository;
import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {

    ProductRepository productRepository;

    public SearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public Page<Product> search(String query,Long id, int pageNo, int pageSize, String sortParam) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        System.out.println("query => " + query);
        if(id==null){
            return productRepository.findByNameContaining(query, pageable);
        }
       return productRepository.findByNameContainingAndCategory_Id(query,id, pageable);
    }

    @Override
    public Optional<Product> getProductById(long id) throws ProductNotFoundException{
        Optional<Product> p = productRepository.findById(id);
        return Optional.ofNullable(p.get());
    };


}
