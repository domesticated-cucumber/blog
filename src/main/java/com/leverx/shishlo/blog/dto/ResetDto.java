package com.leverx.shishlo.blog.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ResetDto {

    @NotBlank
    private String token;
    @NotBlank
    private String password;
}
