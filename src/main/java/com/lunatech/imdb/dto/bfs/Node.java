package com.lunatech.imdb.dto.bfs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node implements Serializable {
    private String value;
    private List<Node> edges = new ArrayList<>();
    private boolean searched = false;
    private Node parent = null;

    public Node(String value) {
        this.value = value;
    }

    public void addEdge(Node neighbor) {
        this.edges.add(neighbor);
        neighbor.edges.add(this);
        //return this;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
