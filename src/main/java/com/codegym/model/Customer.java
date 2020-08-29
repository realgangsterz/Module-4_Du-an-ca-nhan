package com.codegym.model;

import javax.persistence.*;

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
