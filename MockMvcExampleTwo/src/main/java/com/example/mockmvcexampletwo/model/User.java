package com.example.mockmvcexampletwo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private Boolean isAdmin;
}
