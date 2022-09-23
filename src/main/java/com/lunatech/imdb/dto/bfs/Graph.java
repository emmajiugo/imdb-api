package com.lunatech.imdb.dto.bfs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph implements Serializable {
    private List<Node> nodes = new ArrayList<>();
    private Hashtable<String, Node> graph = new Hashtable<>();
    private Node start = null;
    private Node end = null;

    public void addNode(Node n) {
        // putting node into an array
        this.nodes.add(n);
        // node into the hash table
        String title = n.getValue();
        this.graph.put(title, n);
    }

    public Node getNode(String actor) {
        Node n = this.graph.getOrDefault(actor, null);
        return n;
    }

    public Node setStart(String start) {
        this.start = this.graph.getOrDefault(start, null);
        return this.start;
    }

    public Node setEnd(String end) {
        this.end = this.graph.getOrDefault(end, null);
        return this.end;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
