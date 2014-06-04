import java.util.ArrayList;
import java.util.Collections;
public class Box {
	
	static int id = 0;
	int boxId;
	ArrayList<orientation> orientations = new ArrayList<orientation>();

	public Box(int height, int width, int depth){
		this.boxId= id;
		id++;
		orientations.add(new orientation(boxId, 0, 0, 0));
		orientations.add((width>depth) ? new orientation(boxId, height, width, depth) : new orientation(boxId, height, depth, width));
		orientations.add((height>depth)? new orientation(boxId, width, height, depth) : new orientation(boxId, width, depth, height));
		orientations.add((height>width)? new orientation(boxId, depth, height, width) : new orientation(boxId, depth, width, height));
		Collections.sort(orientations);
	}
	
	@Override
	public String toString(){
		String toOutput = "Box Id: " + Integer.toString(boxId) + ". Orientations: \n";
		for(orientation o : orientations){
			toOutput += o.toString() + "\n";
		}
		return toOutput;
	}
	
	

}
