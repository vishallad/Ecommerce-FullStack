package org.example.capstoneproject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseModel implements Serializable {

        private String orderId;

        @ManyToOne
        private Product product;
        private OrderStatus status;

    }
