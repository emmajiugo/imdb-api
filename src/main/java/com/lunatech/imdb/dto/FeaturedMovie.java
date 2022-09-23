/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedMovie implements Serializable {
    private String id;
    private String actorName;
    private String primaryTitle;
    private String originalTitle;
    private List<Cast> casts;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
