/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.dto;

import com.lunatech.imdb.model.TitleCrew;
import com.lunatech.imdb.model.TitlePrincipals;
import com.lunatech.imdb.model.TitleRatings;
import lombok.*;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {
    private String id;
    private String type;
    private String primaryTitle;
    private String originalTitle;
    private Boolean isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;
    private String genres;
    private Rating rating;
    private Crew crew;
    private List<Cast> casts;
}
