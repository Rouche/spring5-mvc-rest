package com.resolutech.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.*;

/**
 * Created by jealar2 on 5/31/2018
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VendorDTO {

    private Long id;
    private String name;
    @ApiModelProperty(value = "This url can be used to perform operations on single vendor")
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
