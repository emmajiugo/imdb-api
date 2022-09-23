package com.lunatech.imdb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class BaconRequest {

    @Size(min = 3, message = "name must be greater than or equal to 3 xters")
    @NotBlank(message = "name is required")
    private String actorName;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
