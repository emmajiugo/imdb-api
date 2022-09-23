/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunatech.imdb.services;

import com.lunatech.imdb.bean.ResponseBuilder;
import com.lunatech.imdb.dao.MovieDao;
import com.lunatech.imdb.dto.*;
import com.lunatech.imdb.dto.bfs.Graph;
import com.lunatech.imdb.dto.bfs.Node;
import com.lunatech.imdb.model.TitleBasics;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.text.WordUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.*;

@Stateless
@Log4j2
public class BaconSearchService {

    private static final String KEVIN_BACON = "Kevin Bacon";
    //private static final String KEVIN_BACON = "Frank Nagai";

    @Inject
    DataBuilder dataBuilder;

    @EJB
    private MovieDao movieDao;

    public Response findBaconPath(BaconRequest request) {
        try {

            List<TitleBasics> titleBasics = movieDao.getAllMovies();
            List<MovieCast> movies = dataBuilder.buildMovieCastModel(titleBasics);

            log.info(movies);

            //return Response.status(Response.Status.OK).entity(movies).build();

            // init graph
            Graph graph = new Graph();

            for(MovieCast movie : movies) {

                String title = movie.getTitle();
                List<String> casts = movie.getCasts();

                // make a movie node
                Node movieNode = new Node(title);

                // add the node to the graph
                graph.addNode(movieNode);

                for(String cast : casts) {
                    String actor = cast;

                    // check to confirm that no node was created for this actor
                    // ELSE create a new node for the actor
                    Node actorNode = (graph.getNode(actor) == null) ? new Node(actor) : graph.getNode(actor);

                    // add the node to the graph
                    graph.addNode(actorNode);
                    // add the node to the edges of movieNode
                    movieNode.addEdge(actorNode);

                }
            }

            //return Response.status(Response.Status.OK).entity(graph).build();


            // Breath First Search algorithm implementation
            Node start = graph.setStart(WordUtils.capitalizeFully(request.getActorName()));
            Node end = graph.setEnd(BaconSearchService.KEVIN_BACON);

            // check if actor name is in the node dataset
            if (start == null) return Response.status(Response.Status.NOT_FOUND).entity("start actor not found in the dataset").build();
            if (end == null) return Response.status(Response.Status.NOT_FOUND).entity("end actor not found in the dataset").build();

            Queue<Node> queue = new LinkedList<>();
            boolean found = false;

            start.setSearched(true); // set to true to confirm you have searched
            queue.add(start); // add to the queue

            while(queue.size() > 0) {
                Node current = queue.poll();

                System.out.println(current.getValue());

                if (current == end) {
                    System.out.println("Found "+ current.getValue());
                    found = true;
                    break;
                }

                // if not found, check the edges
                List<Node> edges = current.getEdges();
                for(Node edge : edges) {
                    Node neighbor = edge;
                    if (!neighbor.isSearched()) {
                        neighbor.setSearched(true);
                        //set parent
                        neighbor.setParent(current);
                        // push neighbor to the queue
                        queue.add(neighbor);
                    }
                }
            }

            //return Response.status(Response.Status.OK).entity("Status: " + found).build();

            // we need to find the path to Kevin Bacon
            // we need to backtrack using the parent of the node set
            List<Node> path = new ArrayList<>();
            path.add(end);

            Node nextPath = end.getParent();

            // if Kevin Bacon not found, there will be no parent
            while (nextPath != null) {
                path.add(nextPath);
                nextPath = nextPath.getParent();
            }

            String message = "";
            for (int i = path.size() - 1; i >= 0; i--) {
                Node n = path.get(i);
                //System.out.println("PATH PARENT: " + n.getValue());
                message += n.getValue() + ((i != 0) ? " --> " : "");
            }

            if (!found) {
                message = "No connection found for " + request.getActorName() + " to " + BaconSearchService.KEVIN_BACON;
            }

            int degreeOfSeparation = (path.size() - 1) / 2;

            BaconSeparation baconSeparation = new BaconSeparation(degreeOfSeparation);

            return ResponseBuilder.build(
                    baconSeparation,
                    found,
                    message,
                    Response.Status.OK,
                    this.getClass());

        } catch (Exception ex) {
            log.error(ex);
            return ResponseBuilder.build(
                    null,
                    false,
                    ex.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR,
                    this.getClass());
        }
    }

 }
