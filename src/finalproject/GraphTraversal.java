package finalproject;

import finalproject.system.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphTraversal
{

	//TODO level 1: implement BFS traversal starting from s
	public static ArrayList<Tile> BFS(Tile s) {
		LinkedList<Tile> toVisit = new LinkedList<Tile>();
		HashSet<Tile> visited = new HashSet<Tile>();
		ArrayList<Tile> result = new ArrayList<Tile>();
		toVisit.add(s);
		while(!toVisit.isEmpty()){
			Tile cur = toVisit.remove();
			if(cur.isWalkable() && !visited.contains(cur)) {
				result.add(cur);
				toVisit.addAll(cur.neighbors);
				visited.add(cur);
			}
		}
		return result;
	}


	//TODO level 1: implement DFS traversal starting from s
	public static ArrayList<Tile> DFS(Tile s) {
		LinkedList<Tile> toVisit = new LinkedList<Tile>();
		HashSet<Tile> visited = new HashSet<Tile>();
		ArrayList<Tile> result = new ArrayList<Tile>();
		toVisit.add(s);
		while(!toVisit.isEmpty()){
			Tile cur = toVisit.removeLast();
			if(cur.isWalkable() && !visited.contains(cur)){
				result.add(cur);
				toVisit.addAll(cur.neighbors);
				visited.add(cur);
			}
		}
		return result;
	}

}  