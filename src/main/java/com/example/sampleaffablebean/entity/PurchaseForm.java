package com.example.sampleaffablebean.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PurchaseForm {

    @NotEmpty(message = "name cannot be empty")
    @Pattern(regexp = "[A-Za-z ]*",message = "Name cannot contain illegal characters.")
    private String name;
    @NotEmpty(message = "Phone cannot be empty")
    private String phone;
    @Email(message = "Invalid Email format!")
    private String email;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotEmpty(message = "Credit Card Number cannot be empty")
    private String creditCardNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate = LocalDate.now();
}
