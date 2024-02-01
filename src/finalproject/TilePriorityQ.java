package finalproject;

import java.util.ArrayList;


import finalproject.system.Tile;

public class TilePriorityQ {
	// TODO level 3: Add fields that can help you implement this data type
	ArrayList<Tile> PriorityQ;
	// TODO level 3: implement the constructor for the priority queue
	public void swap(int i1, int i2){
		Tile temp1 = this.PriorityQ.get(i1);
		Tile temp2 = this.PriorityQ.get(i2);
		this.PriorityQ.remove(i1);
		this.PriorityQ.add(i1, temp2);
		this.PriorityQ.remove(i2);
		this.PriorityQ.add(i2, temp1);
	}

	public void upHeap(int i){
		while(i > 1 && this.PriorityQ.get(i).costEstimate < this.PriorityQ.get(i/2).costEstimate){
			swap(i, i/2);
			i = i/2;
		}
	}

	public void downHeap(int startIndex, int maxIndex){

		int i = startIndex;
		while(2*i <= maxIndex){
			int child = 2*i;
			if(child < maxIndex){
				if(this.PriorityQ.get(child+1).costEstimate < this.PriorityQ.get(child).costEstimate) child += 1;
			}
			if(this.PriorityQ.get(child).costEstimate < this.PriorityQ.get(i).costEstimate){
				swap(child, i);
				i = child;
			}
			else break;
		}
	}

	public TilePriorityQ (ArrayList<Tile> vertices) {
		this.PriorityQ = new ArrayList<Tile>();
		this.PriorityQ.add(0, null);
		if(vertices.get(0) != null) {
			this.PriorityQ.add(1, vertices.get(0));
		}
		for(int i=1; i<vertices.size(); i++) {
			this.PriorityQ.add(i+1,vertices.get(i));
		}
		for(int k=(this.PriorityQ.size()-1)/2; k>= 1; k--){
			downHeap(k, this.PriorityQ.size()-1);
		}
	}

	// TODO level 3: implement remove min as seen in class
	public Tile removeMin() {
		Tile result = this.PriorityQ.get(1);
		swap(1, this.PriorityQ.size()-1);
		this.PriorityQ.remove(PriorityQ.size()-1);
		downHeap(1, PriorityQ.size()-1);
		return result;
	}

	// TODO level 3: implement updateKeys as described in the pdf
	public void updateKeys(Tile t, Tile newPred, double newEstimate) {
		if(this.PriorityQ.contains(t)){
			t.predecessor = newPred;
			t.costEstimate = newEstimate;
			upHeap(this.PriorityQ.indexOf(t));
			downHeap(this.PriorityQ.indexOf(t), this.PriorityQ.size()-1);
		}
	}
}