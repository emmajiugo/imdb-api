/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "title_ratings")
@Getter @Setter @NoArgsConstructor
public class TitleRatings implements Serializable {

    @Id
    private String tconst;
    private Double averageRating;
    private Integer numVotes;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
    
}
