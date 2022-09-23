package com.lunatech.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cast implements Serializable {
    private String id;
    private String name;
    private String category;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
