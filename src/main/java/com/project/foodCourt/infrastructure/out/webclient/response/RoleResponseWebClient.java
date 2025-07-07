package com.project.foodCourt.infrastructure.out.webclient.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseWebClient {
    private Long id;
    private String name;
    private String description;
}
