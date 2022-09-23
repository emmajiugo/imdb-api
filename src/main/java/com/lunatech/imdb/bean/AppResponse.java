package com.lunatech.imdb.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
public class AppResponse {

    private boolean status;
    private String message;
    private Object data;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
