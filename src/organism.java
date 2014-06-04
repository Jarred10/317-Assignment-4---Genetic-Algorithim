import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class organism implements Comparable<organism>{
	
	ArrayList<orientation> orientations = new ArrayList<orientation>();
	result r;
	static Random rand = new Random();

	public organism(){}
	
	public orientation findOrientationById(int id){
		for(orientation o : orientations){
			if (o.id == id) return o;
		}
		return null;
	}
	
	public void findBestStack(){
		Collections.sort(orientations);
		ArrayList<orientation> bestStack = null;
		int bestHeight = 0;
		for(int i = 0; i < orientations.size(); i++){ //for all orientations in set passed in
			ArrayList<orientation> stack = new ArrayList<orientation>();
			stack.add(orientations.get(i)); //add the ith box, sorted so first is biggest
			int height = orientations.get(i).height;
			for(int j = i + 1; j < orientations.size(); j++){ //for the all rest orientations
				orientation nextBox = orientations.get(j);
				//if the next box fits on the last added box, add it
				if(nextBox.fitsOn(stack.get(stack.size() - 1))){
					stack.add(nextBox);
					height+= nextBox.height;
				}
			}
			if(height > bestHeight){
				bestHeight = height;
				bestStack = stack;
			}
		}
		r = new result(bestStack, bestHeight);
	}
	
	public organism breed(organism partner){
		organism child = new organism();
		for(orientation o : orientations){
			child.orientations.add(new orientation(o.id, o.height, o.width, o.depth));
		}
		int iterations = rand.nextInt(orientations.size());
		for(int i = 0; i < iterations; i++){
			int index = rand.nextInt(child.orientations.size());
			child.orientations.set(index, partner.findOrientationById(child.orientations.get(index).id));
		}
		return child;
	}

	@Override
	public int compareTo(organism o) {
		return o.r.bestHeight - r.bestHeight;
	}
	
	@Override
	public String toString(){
		return this.hashCode() + ": " + r.bestHeight;
	}

	public void mutate(ArrayList<Box> boxes) {
		int index = rand.nextInt(orientations.size());
		orientation toMutate = orientations.get(index);
		for(int i = 0; i < boxes.size(); i++){
			if(boxes.get(i).boxId == toMutate.id){
				toMutate = boxes.get(i).orientations.get(boxes.indexOf(toMutate) + 1 % 3);
				return;
			}
		}
	}

}
