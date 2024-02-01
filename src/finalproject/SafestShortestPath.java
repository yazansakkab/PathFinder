package finalproject;


import java.util.ArrayList;
import java.util.LinkedList;

import finalproject.system.Tile;
import finalproject.system.TileType;
import finalproject.tiles.MetroTile;

public class SafestShortestPath extends ShortestPath {
	public int health;
	public Graph costGraph;
	public Graph damageGraph;
	public Graph aggregatedGraph;

	//TODO level 8: finish class for finding the safest shortest path with given health constraint
	public SafestShortestPath(Tile start, int health) {
		super(start);
		this.health = health;
	}


	public void generateGraph() {
		this.costGraph = new Graph(GraphTraversal.BFS(super.source));
		this.damageGraph = new Graph(GraphTraversal.BFS(super.source));
		this.aggregatedGraph = new Graph(GraphTraversal.BFS(super.source));

		for(Tile t : this.costGraph.vertexList){
			for(Tile ti : this.costGraph.travNei(t)){
				if(t.type == TileType.Metro && ti.type == TileType.Metro) {
					((MetroTile) ti).fixMetro(t);
					this.costGraph.addEdge(t, ti, ((MetroTile) ti).metroDistanceCost);
				}
				else this.costGraph.addEdge(t, ti, ti.distanceCost);
			}
		}

		for(Tile t : this.damageGraph.vertexList){
			for(Tile ti : this.damageGraph.travNei(t)){
				this.damageGraph.addEdge(t, ti, ti.damageCost);
			}
		}

		for(Tile t : this.aggregatedGraph.vertexList){
			for(Tile ti : this.aggregatedGraph.travNei(t)){
				this.aggregatedGraph.addEdge(t, ti, ti.damageCost);
			}
		}
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<Tile> findPath(Tile start, LinkedList<Tile> waypoints) {
		super.g = this.costGraph;
		ArrayList<Tile> Pc = super.findPath(start, waypoints);
		double pc_dam = 0;
		double pc_dist = 0;
		for(Tile t : Pc){
			pc_dam += t.damageCost;
			pc_dist += t.distanceCost;
		}
		if(pc_dam < this.health) return Pc;

		super.g = this.damageGraph;
		ArrayList<Tile> Pd = super.findPath(start, waypoints);
		double pd_dam = 0;
		double pd_dist = 0;
		for(Tile t : Pd){
			pd_dam += t.damageCost;
			pd_dist += t.distanceCost;
		}
		if(pd_dam > this.health) return null;

		ArrayList<Tile> Pr = new ArrayList<>();

		while(true) {

			double cost_dam = 0;
			double cost_dist = 0;
			for(Tile t : Pc){
				cost_dam += t.damageCost;
				cost_dist += t.distanceCost;
			}

			double damage_dam = 0;
			double damage_dist = 0;
			for(Tile t : Pd){
				damage_dam += t.damageCost;
				damage_dist += t.distanceCost;
			}

			double lambda = Math.abs(cost_dist-damage_dist)/Math.abs(damage_dam-cost_dam);

			for(Graph.Edge e : this.aggregatedGraph.getAllEdges()){
				e.weight = e.getEnd().distanceCost + (lambda * e.getEnd().damageCost);
			}

			super.g = this.aggregatedGraph;
			if(Pr.size() == 0) Pr = super.findPath(start, waypoints);
			double agg_dist = 0;
			double agg_dam = 0;
			for(Tile t : Pr){
				agg_dam += t.damageCost;
				agg_dist += t.distanceCost;
			}

			double total_agg_cost_Pr = agg_dist + (lambda * agg_dam);
			double total_agg_cost_Pc = cost_dist + (lambda * cost_dam);

			if(Math.abs(total_agg_cost_Pc) == Math.abs(total_agg_cost_Pr)
					|| Math.abs(total_agg_cost_Pc - total_agg_cost_Pr) < 0.00001) return Pd;
			else if(agg_dam <= this.health) Pr = Pd;
			else Pr = Pc;
		}
	}

}