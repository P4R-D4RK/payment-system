package com.p4r.paymentsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String code;

    private String idProgram;

    private String photo;
}