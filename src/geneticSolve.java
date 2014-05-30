import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class geneticSolve {

	public static void main(String[] args) throws FileNotFoundException {

		final int POPULATION_SIZE;
		final int MAX_ORGANISMS;
		int organisms_tested = 0;

		Random rand = new Random();

		Scanner s = new Scanner(new File(args[0]));
		HashMap<Integer, Box> boxes = new HashMap<Integer, Box>();
		String line[];

		while(s.hasNextLine()){
			line = s.nextLine().split(" ");
			Box b = new Box(Integer.valueOf(line[0]), Integer.valueOf(line[1]), Integer.valueOf(line[2]));
			boxes.put(b.boxId, b);
		}

		POPULATION_SIZE = boxes.size() * 4;
		MAX_ORGANISMS = 3 * ((int)Math.pow(boxes.size(), 2));

		ArrayList<organism> population = new ArrayList<organism>();

		for(int i = 0; i < POPULATION_SIZE; i++){
			organism org = new organism();
			for(int j : boxes.keySet()){ //for every box
				org.orientations.add(boxes.get(j).orientations.get(rand.nextInt(3)));
			}
			population.add(org);
		}

		//loop
		
		organism currentBest = null;

		while(organisms_tested < MAX_ORGANISMS){

			int totalFitness = 0;

			for(organism o : population){
				o.findBestStack();
				totalFitness += o.r.bestHeight;
				organisms_tested++;
			}
			if(currentBest == null || currentBest.r.bestHeight < population.get(0).r.bestHeight){
				currentBest = population.get(0);
			}


			ArrayList<organism> survivors = new ArrayList<organism>();
			ArrayList<organism> elites = new ArrayList<organism>();

			Collections.sort(population);
			//put top 1% or 3, which is higher into elites, dont get culled
			for(int i = 0; i < Math.max(POPULATION_SIZE * 0.01, 3); i++){
				//add best
				elites.add(population.get(i));
				totalFitness -=  population.get(i).r.bestHeight;
				population.remove(i);
			}

			for(organism o : population){
				if(rand.nextDouble() < o.r.bestHeight / totalFitness) survivors.add(o);
			}

			survivors.addAll(elites);

			population = new ArrayList<>(elites);

			for(int i = elites.size(); i < POPULATION_SIZE; i++){
				population.add(survivors.get(rand.nextInt(survivors.size())).breed(survivors.get(rand.nextInt(survivors.size()))));
			}

		}

		System.out.println(organisms_tested);
		System.out.println(currentBest.r.bestHeight);

		s.close();

	}

}
