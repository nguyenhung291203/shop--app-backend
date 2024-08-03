package com.example.shopappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="fullname",length = 100)
    private String fullName;
    @Column(length = 100)
    private String email;
    @Column(name="phone_number",length = 10,nullable = false)
    private String phoneNumber;
    @Column(length = 200,nullable = false)
    private String address;
    @Column(length = 100)
    private String note;
    @Column(name="order_date")
    private Date orderDate;
    private String status;
    @Column(name="total_money")
    private Float totalMoney;
    @Column(name="shipping_method",length = 100)
    private String shippingMethod;
    @Column(name="shipping_address",length = 100)
    private String shippingAddress;
    @Column(name="shipping_date")
    private Date shippingDate;
    @Column(name="tracking_number",length = 100)
    private String trackingNumber;
    @Column(name = "payment_method",length = 100)
    private String paymentMethod;
    private boolean active;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}