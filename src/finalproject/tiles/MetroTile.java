package finalproject.tiles;

import finalproject.system.Tile;
import finalproject.system.TileType;

public class MetroTile extends Tile {
    public double metroTimeCost = 100;
    public double metroDistanceCost = 100;
    public double metroCommuteFactor = 0.2;

    //TODO level 0: finish constructor
    public MetroTile() {
        super(1, 1, 2);
        this.type = TileType.Metro;
    }

    // TODO level 7: updates the distance and time cost differently between metro tiles
    public void fixMetro(Tile node) {
        if(node.getTileType() == TileType.Metro){
            this.metroTimeCost = dist(this, node)*this.metroCommuteFactor;
            this.metroDistanceCost = dist(this, node)/this.metroCommuteFactor;
        }
    }

    public int dist(Tile t1, Tile t2){
        return Math.abs(t1.xCoord - t2.xCoord) + Math.abs(t1.yCoord - t2.yCoord);
    }

}