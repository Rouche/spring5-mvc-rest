package com.resolutech.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by jealar2 on 5/31/2018
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDTO {

    private Long id;
    private String firstname;
    private String lastname;
    @JsonProperty("customer_url")
    private String customerUrl;
}
