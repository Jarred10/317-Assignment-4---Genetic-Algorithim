import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class geneticSolve {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner s = new Scanner(new File(args[0]));
		HashMap<Integer, Box> boxes = new HashMap<Integer, Box>();
		String line[];

		while(s.hasNextLine()){
			line = s.nextLine().split(" ");
			Box b = new Box(Integer.valueOf(line[0]), Integer.valueOf(line[1]), Integer.valueOf(line[2]));
			boxes.put(b.boxId, b);
		}

		ArrayList<orientation> oris = new ArrayList<orientation>();

		for(int i : boxes.keySet()){
			Box b = boxes.get(i);
			oris.addAll(b.orientations);
		}

		Object[] result = findBestStack(oris);

		System.out.println(((ArrayList<orientation>) result[0]).toString());

		s.close();

	}

	public static Object[] findBestStack(ArrayList<orientation> oris){
		Collections.sort(oris);
		ArrayList<orientation> bestStack = null;
		int bestHeight = 0;
		for(int i = 0; i < oris.size(); i++){ //for all orientations in set passed in
			ArrayList<orientation> stack = new ArrayList<orientation>();
			stack.add(oris.get(i)); //add the ith box, sorted so first is biggest
			int height = oris.get(i).height;
			for(int j = i + 1; j < oris.size(); j++){ //for the all rest orientations
				orientation nextBox = oris.get(j);
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
		return new Object[]{bestStack, bestHeight};
	}

}
