import java.util.ArrayList;
import java.util.Collections;
// Class that represents a box which is all possible orientations
public class Box {
	
	//global class level id to count boxes
	static int id = 0;
	//unique box id
	int boxId;
	ArrayList<orientation> orientations = new ArrayList<orientation>();

	// Construct all the orientations of the box given its dimensions
	public Box(int height, int width, int depth){
		this.boxId= id;
		id++;
		// Blank orientation so that you can choose to not use the box
		orientations.add(new orientation(boxId, 0, 0, 0));
		// Make all the orientations where width is greater than depth
		orientations.add((width>depth) ? new orientation(boxId, height, width, depth) : new orientation(boxId, height, depth, width));
		orientations.add((height>depth)? new orientation(boxId, width, height, depth) : new orientation(boxId, width, depth, height));
		orientations.add((height>width)? new orientation(boxId, depth, height, width) : new orientation(boxId, depth, width, height));
		// Sort the orientations
		Collections.sort(orientations);
	}
	
	// To string to print the box
	@Override
	public String toString(){
		String toOutput = "Box Id: " + Integer.toString(boxId) + ". Orientations: \n";
		for(orientation o : orientations){
			toOutput += o.toString() + "\n";
		}
		return toOutput;
	}

}
