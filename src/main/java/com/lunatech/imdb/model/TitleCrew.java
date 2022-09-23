/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "title_crew")
@Getter @Setter @NoArgsConstructor
public class TitleCrew implements Serializable {

    @Id
    private String tconst;
    private String directors;
    private String writers;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
    
}
