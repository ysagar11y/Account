package com.sbank.Account.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account extends BaseEntity{

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="account_number")
    @Id
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;
}
