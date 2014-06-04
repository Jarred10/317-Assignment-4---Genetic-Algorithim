import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

// Class to represent an organism which is a set of orientations (one from each box)
public class organism implements Comparable<organism>{

	ArrayList<orientation> orientations = new ArrayList<orientation>();
	result r;
	static Random rand = new Random();

	public organism(){}

	// Find an orientation using id
	public orientation findOrientationById(int id){
		for(orientation o : orientations){
			if (o.id == id) return o;
		}
		return null;
	}

	// Generate the stack for this organism
	public void findBestStack(){
		// Sort the orientations
		Collections.sort(orientations);
		ArrayList<orientation> stack = new ArrayList<orientation>();
		int height = 0;
		// For all orientations in set passed in
		for(int i = 0; i < orientations.size(); i++){ 
			
			// Get the orientation at that position
			orientation o = orientations.get(i);

			// If that stack size is 0
			if(stack.size() == 0) {
				// Add it to the stack
				height += o.height;
				stack.add(o); 

			}
			else // If the next box fits on the last added box, add it
				if(o.fitsOn(stack.get(stack.size() - 1))){
					stack.add(o);
					height += o.height;
				}
		}
		r = new result(stack, height);
	}

	// Breeds the organism by making a child using another organism
	public organism breed(organism partner){
		organism child = new organism();
		// Add all your orientations to the child
		for(orientation o : orientations){
			child.orientations.add(new orientation(o.id, o.height, o.width, o.depth));
		}
		// For a random number of iterations
		int iterations = rand.nextInt(orientations.size());
		for(int i = 0; i < iterations; i++){
			int index = rand.nextInt(child.orientations.size());
			// Swap the orientation to the partners orientation
			child.orientations.set(index, partner.findOrientationById(child.orientations.get(index).id));
		}
		return child;
	}

	// Compare organisms based on best stack height
	@Override
	public int compareTo(organism o) {
		return o.r.bestHeight - r.bestHeight;
	}

	// To string to print the organism
	@Override
	public String toString(){
		return this.hashCode() + ": " + r.bestHeight;
	}

	// Mutate the organism by rotating one of its orientations
	public void mutate(ArrayList<Box> boxes) {
		// Pick a random orientation
		int index = rand.nextInt(orientations.size());
		// Grab the orientation we want to mutate
		orientation toMutate = orientations.get(index);
		// Go find the box that matches that orientation
		for(int i = 0; i < boxes.size(); i++){
			if(boxes.get(i).boxId == toMutate.id){
				// Mutate by rotating that orientation (grab the next orientation of that box)
				toMutate = boxes.get(i).orientations.get(boxes.indexOf(toMutate) + 1 % 3);
				return;
			}
		}
	}

}
