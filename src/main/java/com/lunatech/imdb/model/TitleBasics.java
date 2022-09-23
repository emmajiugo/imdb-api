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
import java.util.List;

@Entity
@Table(name = "title_basics")
@NamedQueries({
        @NamedQuery(name = TitleBasics.FIND_ALL, query = "SELECT t FROM TitleBasics t ORDER BY t.tconst ASC"),
        @NamedQuery(name = TitleBasics.FIND_BY_KEY, query = "SELECT t FROM TitleBasics t WHERE t.tconst = :key"),
        @NamedQuery(name = TitleBasics.FIND_BY_TITLE, query = "SELECT t FROM TitleBasics t WHERE LOWER(t.primaryTitle) LIKE CONCAT('%', :title, '%') OR LOWER(t.originalTitle) LIKE CONCAT('%', :title, '%') ORDER BY t.tconst ASC"),
        @NamedQuery(name = TitleBasics.FIND_TOP_RATED_MOVIES, query = "SELECT t FROM TitleBasics t JOIN TitleRatings r ON r.tconst = t.tconst WHERE LOWER(t.genres) LIKE CONCAT('%', :genre, '%') ORDER BY r.averageRating DESC, r.numVotes DESC"),
})
@Getter @Setter @NoArgsConstructor
public class TitleBasics implements Serializable {

    public static final String FIND_ALL = "TitleBasics.findAll";
    public static final String FIND_BY_KEY = "TitleBasics.findByKey";
    public static final String FIND_BY_TITLE = "TitleBasics.findByTitle";
    public static final String FIND_TOP_RATED_MOVIES = "TitleBasics.findTopRatedMovies";

    @Id
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private Boolean isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer runtimeMinutes;
    private String genres;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tconst", referencedColumnName = "tconst", insertable=false, updatable=false)
    private TitleRatings titleRatings;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="tconst", referencedColumnName = "tconst", insertable=false, updatable=false)
    private List<TitlePrincipals> titlePrincipals;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tconst", referencedColumnName = "tconst", insertable=false, updatable=false)
    private TitleCrew titleCrew;

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
    
}
