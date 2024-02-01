package finalproject;


import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class ShortestPath extends PathFindingService {
    //TODO level 4: find distance prioritized path
    public ShortestPath(Tile start) {
        super(start);
        this.generateGraph();

    }

    @Override
    public void generateGraph() {
        super.g = new Graph(GraphTraversal.BFS(super.source));
        for(Tile t : super.g.vertexList){
            for(Tile ti : super.g.travNei(t)){
                if(t.type == TileType.Metro && ti.type == TileType.Metro) {
                    ((MetroTile) ti).fixMetro(t);
                    super.g.addEdge(t, ti, ((MetroTile) ti).metroDistanceCost);
                }
                else super.g.addEdge(t, ti, ti.distanceCost);
            }
        }
        // TODO Auto-generated method stub
    }

}