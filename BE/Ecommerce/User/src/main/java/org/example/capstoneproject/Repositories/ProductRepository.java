package org.example.capstoneproject.Repositories;

import org.example.capstoneproject.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

//    @Query("select p from Product p where p.category.name= :categoryName")
//    List<Product> getProductByCategoryName(@Param("categoryName") String categoryName);

//    @Query(value = "select * from product where category_id in (select category_id from category where name =:categoryName",nativeQuery = true)
//    List<Product> getProductByCategoryNameNative(@Param("categoryName") String categoryName);

    Page<Product> findByNameContaining(String query, Pageable pageable);
    Page<Product> findByNameContainingAndCategory_Id(String query, Long id, Pageable pageable);
}