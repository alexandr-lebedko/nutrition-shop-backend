package net.lebedko.nutritionshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
}
