package com.leverx.shishlo.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class TagCloudDto {

    @NotBlank
    private String tagName;
    @NotBlank
    private Long postCount;
}
