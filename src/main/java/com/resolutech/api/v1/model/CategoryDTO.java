package com.resolutech.api.v1.model;

import lombok.*;

/**
 * Created by jt on 9/24/17.
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDTO {
    private Long id;
    private String name;
}
