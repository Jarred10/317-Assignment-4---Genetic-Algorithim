import java.util.ArrayList;

class result{

	ArrayList<orientation> bestStack;
	int bestHeight;

	public result(ArrayList<orientation> bestStack,	int bestHeight){
		this.bestStack = bestStack;
		this.bestHeight = bestHeight;
	}
	
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