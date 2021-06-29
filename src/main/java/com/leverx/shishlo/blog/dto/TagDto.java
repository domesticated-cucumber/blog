package com.leverx.shishlo.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private Long id;
    @NotBlank
    private String name;
    private List<Long> articles;
}
