package org.example.capstoneproject.Services;

import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface SearchService {

    Page<Product> search(String query,Long id, int pageNo, int pageSize, String sortParam);

    Optional<Product> getProductById(long id) throws ProductNotFoundException;
}
