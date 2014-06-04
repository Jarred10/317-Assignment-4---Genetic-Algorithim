import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class geneticSolve {

	public static void main(String[] args) throws FileNotFoundException {

		final int POPULATION_SIZE;
		final int MAX_ORGANISMS;
		int organisms_tested = 0;

		Random rand = new Random();

		Scanner s = new Scanner(new File(args[0]));
		ArrayList<Box> boxes = new ArrayList<Box>(); //list of all boxes read in

		while(s.hasNextLine()){
			String[] line = s.nextLine().split(" ");
			boxes.add(new Box(Integer.valueOf(line[0]), Integer.valueOf(line[1]), Integer.valueOf(line[2])));
		}
		
		MAX_ORGANISMS = (int) Math.pow((3 * boxes.size()), 2); //max evaluations = (3n)*(3n)
		POPULATION_SIZE = boxes.size() * 3; //initial population is 3 times as much as boxes read in 

		ArrayList<organism> population = new ArrayList<organism>();

		for(int i = 0; i < POPULATION_SIZE; i++){
			organism org = new organism();
			for(Box b : boxes){ //for every box
				org.orientations.add(b.orientations.get(rand.nextInt(3)));
			}
			population.add(org);
			org.findBestStack();
		}
		
		Collections.sort(population);

		//loop
		
		organism currentBest = null;

		while(organisms_tested < MAX_ORGANISMS){

			int totalFitness = 0;

			for(organism o : population){
				o.findBestStack();
				totalFitness += o.r.bestHeight;
				organisms_tested++;
				if(rand.nextDouble() < 0.0001){
					if(currentBest == null || currentBest.r.bestHeight < o.r.bestHeight) currentBest = o;
					o.mutate(boxes);
				}
			}

			Collections.sort(population);
			
			if(currentBest == null || currentBest.r.bestHeight < population.get(0).r.bestHeight){
				currentBest = population.get(0);
			}


			ArrayList<organism> survivors = new ArrayList<organism>();
			ArrayList<organism> elites = new ArrayList<organism>();

			//put top 1% or 3, which is higher into elites, dont get culled
			for(int i = 0; i < Math.max((double)POPULATION_SIZE * 0.25, 2); i++){
				//add best
				elites.add(population.get(i));
				totalFitness -=  population.get(i).r.bestHeight;
				population.remove(i);
			}

			for(organism o : population){
				if(rand.nextDouble() < ((double)o.r.bestHeight / totalFitness)) survivors.add(o);
			}

			survivors.addAll(elites);

			population = new ArrayList<>(elites);

			for(int i = elites.size(); i < POPULATION_SIZE; i++){
				population.add(survivors.get(rand.nextInt(survivors.size())).breed(survivors.get(rand.nextInt(survivors.size()))));
			}

		}

		System.out.println("Best stack for " + boxes.size());
		System.out.println(currentBest.r.toString());

		s.close();

	}

}
