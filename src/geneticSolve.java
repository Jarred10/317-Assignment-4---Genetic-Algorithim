import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
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

		PriorityQueue<organism> population = new PriorityQueue<organism>();

		for(int i = 0; i < POPULATION_SIZE; i++){
			organism org = new organism();
			for(Box b : boxes){ //for every box
				org.orientations.add(b.orientations.get(rand.nextInt(3)));
			}
			org.findBestStack();
			population.add(org);
		}

		//loop
		
		organism currentBest = null;
		int timesSame = 0;

		while(organisms_tested < MAX_ORGANISMS && timesSame < boxes.size() * 2){

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
			
			if(currentBest == null || currentBest.r.bestHeight < population.peek().r.bestHeight){
				if(currentBest != null) System.out.println(currentBest.r.bestHeight + ", " + population.peek().r.bestHeight);
				currentBest = population.peek();
				timesSame = 0;
			}
			else if(currentBest.r.bestHeight >= population.peek().r.bestHeight){

				timesSame++;
				System.out.println(timesSame);
			}
			

			ArrayList<organism> survivors = new ArrayList<organism>();
			ArrayList<organism> elites = new ArrayList<organism>();

			//put top 1% or 3, which is higher into elites, dont get culled
			for(int i = 0; i < Math.max((double)POPULATION_SIZE * 0.15, 2); i++){
				//add best
				organism elite = population.remove();
				
				elites.add(elite);
				totalFitness -=  elite.r.bestHeight;
				population.remove(i);
			}

			for(organism o : population){
				if(rand.nextDouble() < ((double)o.r.bestHeight / totalFitness)) survivors.add(o);
			}

			survivors.addAll(elites);

			population = new PriorityQueue<organism>(elites);

			for(int i = elites.size(); i < POPULATION_SIZE; i++){
				organism o = survivors.get(rand.nextInt(survivors.size())).breed(survivors.get(rand.nextInt(survivors.size())));
				o.findBestStack();
				population.add(o);
			}

		}

		System.out.println("Best stack for " + boxes.size());
		System.out.println(currentBest.r.toString());
		System.out.println(organisms_tested);

		s.close();

	}

}
