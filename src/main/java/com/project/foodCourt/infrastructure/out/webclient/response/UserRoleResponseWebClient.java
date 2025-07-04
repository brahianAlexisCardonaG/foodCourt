package com.project.foodCourt.infrastructure.out.webclient.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRoleResponseWebClient {
    private Long id;
    private String firstName;
    private String lastName;
    private String documentNumber;
    private String phone;
    private LocalDate birthDate;
    private String email;
    private RoleResponseWebClient role;
}