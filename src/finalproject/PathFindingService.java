package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class PathFindingService {
    Tile source;
    Graph g;

    public PathFindingService(Tile start) {
        this.source = start;
    }

    public abstract void generateGraph();

    public void relax(Graph.Edge e, TilePriorityQ q){
        Tile v = e.getEnd();
        Tile u = e.getStart();
        double w = e.weight;
        if(v.costEstimate > u.costEstimate +w){
            q.updateKeys(v, u, u.costEstimate + w);
        }
    }

    public void init_single_source(ArrayList<Tile> vertices, Tile s){
        for(Tile t : vertices){
            t.costEstimate = Integer.MAX_VALUE;
            t.predecessor = null;
        }
        s.costEstimate = 0;
    }

    public HashMap<Boolean,Tile> dijkstra(Graph myG, Tile s){
        HashMap<Boolean,Tile> result = new HashMap<Boolean, Tile>();
        init_single_source(myG.vertexList, s);
        TilePriorityQ myQ = new TilePriorityQ(myG.vertexList);
        while(myQ.PriorityQ.size()-1 != 0){
            Tile temp = myQ.removeMin();
            result.put(temp.isDestination, temp);
            for(Graph.Edge e : myG.adjList.get(temp.getNodeID())){
                relax(e, myQ);
            }
        }
        return result;
    }

    //TODO level 4: Implement basic dijkstra's algorithm to find a path to the final unknown destination
    public ArrayList<Tile> findPath(Tile startNode) {
        HashMap<Boolean, Tile> res = dijkstra(this.g, startNode);
        LinkedList<Tile> myL = new LinkedList<Tile>();
        Tile temp = res.get(true);
        while(temp.predecessor != null){
            myL.addFirst(temp);
            temp = temp.predecessor;
        }
        myL.addFirst(temp);
        return new ArrayList<Tile>(myL);
    }
    //TODO level 5: Implement basic dijkstra's algorithm to path find to a known destination
    public ArrayList<Tile> findPath(Tile start, Tile end) {
        dijkstra(this.g, start);
        LinkedList<Tile> myL = new LinkedList<Tile>();
        Tile temp = end;
        while(temp.predecessor != null){
            myL.addFirst(temp);
            temp = temp.predecessor;
        }
        myL.addFirst(temp);
        return new ArrayList<Tile>(myL);
    }

    //TODO level 5: Implement basic dijkstra's algorithm to path find to the final destination passing through given waypoints
    public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints){
        ArrayList<Tile> result = new ArrayList<Tile>();
        LinkedList<Tile> myL = new LinkedList<Tile>();
        if(waypoints.size() == 0) {
            result.addAll(findPath(start));
            return result;
        }
        result.addAll(findPath(start, waypoints.get(0)));
        result.remove(result.size()-1);
        if(waypoints.size() > 1) {
            for (int i = 0; i < waypoints.size() - 1; i++) {
                result.addAll(findPath(waypoints.get(i), waypoints.get(i + 1)));
                result.remove(result.size()-1);
            }
        }
        result.addAll(findPath(waypoints.get(waypoints.size()-1)));
        return result;
    }

}
