package finalproject;

import java.util.ArrayList;
import java.util.HashSet;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.*;

public class Graph {
    // TODO level 2: Add fields that can help you implement this data type
    ArrayList<Tile> vertexList;
    ArrayList<ArrayList<Edge>> adjList;
    ArrayList<Edge> totalEdges;


    // TODO level 2: initialize and assign all variables inside the constructor
    public Graph(ArrayList<Tile> vertices) {
        this.vertexList = vertices;
        this.totalEdges = new ArrayList<Edge>();
        this.adjList = new ArrayList<ArrayList<Edge>>();

        int i = 0;
        for(Tile vert : this.vertexList){
            vert.setNodeID(i);
            this.adjList.add(i, null);
            i++;
        }
    }

    // TODO level 2: add an edge to the graph
    public void addEdge(Tile origin, Tile destination, double weight){
        if(this.adjList.get(origin.getNodeID()) == null){
            ArrayList<Edge> temp = new ArrayList<Edge>();
            temp.add(new Edge(origin, destination, weight));
            this.adjList.remove(origin.getNodeID());
            this.adjList.add(origin.getNodeID(), temp);
        }
        else {
            this.adjList.get(origin.getNodeID()).add(new Edge(origin, destination, weight));
        }
        totalEdges.add(new Edge(origin, destination, weight));
    }

    // TODO level 2: return a list of all edges in the graph
    public ArrayList<Edge> getAllEdges() {
        return totalEdges;
    }

    // TODO level 2: return list of tiles adjacent to t
    public ArrayList<Tile> getNeighbors(Tile t) {
        ArrayList<Tile> result = new ArrayList<>();
        for(Edge ti: this.adjList.get(t.nodeID)){
            if(ti.getEnd().isWalkable())result.add(ti.getEnd());
        }
        return result;
    }

    public ArrayList<Tile> travNei(Tile t) {
        ArrayList<Tile> result = new ArrayList<Tile>();
        for(Tile ti : t.neighbors){
            if(ti.isWalkable()) result.add(ti);
        }
        return result;
    }

    // TODO level 2: return total cost for the input path
    public double computePathCost(ArrayList<Tile> path) {
        double total = 0;
        for(int i=0; i<path.size()-1; i++){
            ArrayList<Edge> list = adjList.get(path.get(i).getNodeID());
            for(Edge e : list){
                if(e.getEnd().getNodeID() == path.get(i+1).getNodeID()) total += e.weight;
            }
        }
        return total;
    }


    public static class Edge{
        Tile origin;
        Tile destination;
        double weight;

        // TODO level 2: initialize appropriate fields
        public Edge(Tile s, Tile d, double cost){
            this.origin = s;
            this.destination = d;
            this.weight = cost;
        }

        // TODO level 2: getter function 1
        public Tile getStart(){
            return this.origin;
        }


        // TODO level 2: getter function 2
        public Tile getEnd() {
            return this.destination;
        }

    }

    public static void main(String[] args){
        Tile vertex1 = new DesertTile();
        Tile vertex2 = new DesertTile();
        Tile vertex3 = new PlainTile();
        Tile vertex4 = new PlainTile();

        ArrayList<Tile> vertices = new ArrayList<>();
        vertices.add(vertex1);
        vertices.add(vertex2);
        vertices.add(vertex3);
        vertices.add(vertex4);



        Graph weightedGraph = new Graph(vertices);

        weightedGraph.addEdge(vertex1,vertex2,5);
        weightedGraph.addEdge(vertex2,vertex3,5);
        weightedGraph.addEdge(vertex3,vertex4,5);
        weightedGraph.addEdge(vertex4,vertex1,5);


        for(Tile vert : vertices){
            System.out.println(vert.nodeID);
        }

//        System.out.println(weightedGraph.edges.get(0).size());

        System.out.print("Path length from tile 1 to tile 4:");
        System.out.println(weightedGraph.computePathCost(vertices));

        for(Edge edge: weightedGraph.getAllEdges())
            System.out.println("Edge linking: " + edge.origin +" and " + edge.destination
                    + " with weight "+ edge.weight);
    }

}