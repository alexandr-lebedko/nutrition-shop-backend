package net.lebedko.nutritionshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@EqualsAndHashCode
public class CategoryRequest {
    @JsonProperty("name")
    @NotEmpty
    private String name;
}
