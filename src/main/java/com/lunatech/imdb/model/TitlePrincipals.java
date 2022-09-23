/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lunatech.imdb.dto.Cast;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "title_principals")
@NamedQueries({
        @NamedQuery(name = TitlePrincipals.FIND_CAST_ACTOR, query = "SELECT t FROM TitlePrincipals t WHERE t.nconst = :key"),
})
@Getter @Setter @NoArgsConstructor
public class TitlePrincipals implements Serializable {

    public static final String FIND_CAST_ACTOR = "TitleBasics.findCastActor";

    @Id
    private String tconst;
    @Id
    private int ordering;
    @Id
    private String nconst;
    private String category;
    private String job;
    private String characters;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nconst", referencedColumnName = "nconst")
    private NameBasics nameBasics;

    public Cast buildCastModel() {
        return Cast.builder()
                .id(nconst)
                .name(nameBasics.getPrimaryName())
                .category(category)
                .build();
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
    
}
