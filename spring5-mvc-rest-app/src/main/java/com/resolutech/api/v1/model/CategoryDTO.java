package com.resolutech.api.v1.model;

import lombok.*;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDTO {
    private Long id;
    private String name;
}
