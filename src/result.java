import java.util.ArrayList;

// Class to store the result of generating a stack from a given set of boxes
class result{

	// Store the best stack orientation and the height
	ArrayList<orientation> bestStack;
	int bestHeight;

	// Construct the result given the stack and the height
	public result(ArrayList<orientation> bestStack,	int bestHeight){
		this.bestStack = bestStack;
		this.bestHeight = bestHeight;
	}
	
	// To string to output the result
	public String toString(){
		String output = "";
		output += "height of " + bestHeight + '\n';
		int height = 0;
		for(orientation o : bestStack){
			height += o.height;
			output += o.height + " " + o.width + " " + o.depth + " {" + height + "}\n";
		}
		return output;
	}
}