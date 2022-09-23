package com.lunatech.imdb.dao;

import com.lunatech.imdb.Global;
import com.lunatech.imdb.dto.Pagination;
import com.lunatech.imdb.model.NameBasics;
import com.lunatech.imdb.model.TitleBasics;
import com.lunatech.imdb.model.TitlePrincipals;
import org.jboss.ejb3.annotation.TransactionTimeout;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Stateless
public class MovieDao {

    @PersistenceContext(unitName = Global.PERSISTENT_NAME)
    EntityManager em;

    @TransactionTimeout(value = 10, unit = TimeUnit.MINUTES)
    public List<TitleBasics> getAllMovies() {
        return em.createNamedQuery(TitleBasics.FIND_ALL, TitleBasics.class)
                .setMaxResults(10000)
                .getResultList();
    }

    public Optional<TitleBasics> getMovieById(String id) {
        List<TitleBasics> basics = em.createNamedQuery(TitleBasics.FIND_BY_KEY, TitleBasics.class)
                .setParameter("key", id)
                .getResultList();

        return basics.isEmpty() ? Optional.empty() : Optional.of(basics.get(0));
    }

    public List<TitleBasics> getMoviesByTitle(String title, Pagination paginate) {
        return em.createNamedQuery(TitleBasics.FIND_BY_TITLE, TitleBasics.class)
                .setParameter("title", title.toLowerCase())
                .setFirstResult(paginate.getStart())
                .setMaxResults(paginate.getPerPage())
                .getResultList();
    }

    public Optional<NameBasics> getMemberById(String id) {
        List<NameBasics> names = em.createNamedQuery(NameBasics.FIND_BY_KEY, NameBasics.class)
                .setParameter("key", id)
                .getResultList();

        return names.isEmpty() ? Optional.empty() : Optional.of(names.get(0));
    }

    public Optional<TitlePrincipals> getCastActorById(String id) {
        List<TitlePrincipals> actors = em.createNamedQuery(TitlePrincipals.FIND_CAST_ACTOR, TitlePrincipals.class)
                .setParameter("key", id)
                .getResultList();

        return actors.isEmpty() ? Optional.empty() : Optional.of(actors.get(0));
    }

    public List<TitleBasics> getTopRatedMoviesByGenre(String genre, Pagination paginate) {
        return em.createNamedQuery(TitleBasics.FIND_TOP_RATED_MOVIES, TitleBasics.class)
                .setParameter("genre", genre)
                .setFirstResult(paginate.getStart())
                .setMaxResults(paginate.getPerPage())
                .getResultList();
    }
}
