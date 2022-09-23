package com.lunatech.imdb.dto;

import com.lunatech.imdb.util.PatternFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SearchRequest {

    @Size(min = 1, message = "search keyword must not be an empty field")
    @NotBlank(message = "search keyword is required")
    private String keyword;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
