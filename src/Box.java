import java.util.ArrayList;
import java.util.Collections;
public class Box {
	
	static int id = 0;
	int boxId;
	ArrayList<orientation> orientations = new ArrayList<orientation>();

	public Box(int height, int width, int depth){
		this.boxId= ++id;
		orientations.add((width>depth) ? new orientation(height, width, depth) : new orientation(height, depth, width));
		orientations.add((height>depth)? new orientation(width, height, depth) : new orientation(width, depth, height));
		orientations.add((height>width)? new orientation(depth, height, width) : new orientation(depth, width, height));
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
