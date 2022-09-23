/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lunatech.imdb.dto.CrewMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "name_basics")
@NamedQueries({
        @NamedQuery(name = NameBasics.FIND_BY_KEY, query = "SELECT n FROM NameBasics n WHERE n.nconst = :key"),
})
@Getter @Setter @NoArgsConstructor
public class NameBasics implements Serializable {

    public static final String FIND_BY_KEY = "NameBasics.findByKey";

    @Id
    private String nconst;
    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;
    private String primaryProfession;
    private String knownForTitles;

    public CrewMember buildCrewMemberModel(){
        return CrewMember.builder()
                .name(primaryName)
                .profession(primaryProfession)
                .birthYear(birthYear)
                .deathYear(deathYear)
                .build();
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
    
}
