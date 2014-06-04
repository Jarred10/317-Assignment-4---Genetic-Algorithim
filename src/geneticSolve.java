import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		
		// List of all boxes read in
		ArrayList<Box> boxes = new ArrayList<Box>(); 

		// Read in all the boxes
		while(s.hasNextLine()){
			String[] line = s.nextLine().split(" ");
			boxes.add(new Box(Integer.valueOf(line[0]), Integer.valueOf(line[1]), Integer.valueOf(line[2])));
		}
		
		// Max evaluations = (3n)*(3n)
		MAX_ORGANISMS = (int) Math.pow((3 * boxes.size()), 2); 
		// Initial population is 3 times as much as boxes read in 
		POPULATION_SIZE = boxes.size() * 3; 

		PriorityQueue<organism> population = new PriorityQueue<organism>();

		// Generate the population
		for(int i = 0; i < POPULATION_SIZE; i++){
			organism org = new organism();
			// For every box
			for(Box b : boxes){ 
				// Use one orientation
				org.orientations.add(b.orientations.get(rand.nextInt(3)));
			}
			// Find the stack for that organism
			org.findBestStack();
			// Add it to the population
			population.add(org);
		}
		
		// Track the current best organism
		organism currentBest = null;
		
		// Timers that break code when 15s pass without an improvement in fitness
		long startTime = System.currentTimeMillis();
		long currentTime = 0;

		// While we haven't violated any termination conditions
		while(organisms_tested < MAX_ORGANISMS && startTime + 15000 > currentTime){

			int totalFitness = 0;

			// For the entire population
			for(organism o : population){
				// Track the total fitness of this population
				totalFitness += o.r.bestHeight;
				// Count organisms tested
				organisms_tested++;
				// Mutate 1 in 10000
				if(rand.nextDouble() < 0.0001){
					if(currentBest == null || currentBest.r.bestHeight < o.r.bestHeight) currentBest = o;
					// Mutates o
					o.mutate(boxes);
				}
			}
			
			// Most fit organism is on top of the population 
			organism fittest = population.peek();
			// If we find a new best or the first best then update the best
			if(currentBest == null || currentBest.r.bestHeight < fittest.r.bestHeight){
				currentBest = fittest;
				startTime = System.currentTimeMillis();
			}
			// Else set the current time 
			else if(currentBest.r.bestHeight >= population.peek().r.bestHeight){
				currentTime = System.currentTimeMillis();
			}
			
			// List of survivors that arent killed based on their fitness
			ArrayList<organism> survivors = new ArrayList<organism>();
			// List of elites that are the top 15% fittest organisms (or 2, if population is small enough)
			ArrayList<organism> elites = new ArrayList<organism>();

			// Put top 15% or 2, which is higher into a separate group so they don't get culled
			for(int i = 0; i < Math.max((double)POPULATION_SIZE * 0.15, 2); i++){
				organism elite = population.remove();
				elites.add(elite);
				totalFitness -=  elite.r.bestHeight;
				population.remove(i);
			}

			// For the whole populate, randomly determine if they will survive based on their fitness compared to total fitness
			for(organism o : population){
				if(rand.nextDouble() < ((double)o.r.bestHeight / totalFitness)) survivors.add(o);
			}

			// Add all the fit organisms to the survivors
			survivors.addAll(elites);
			
			// Construct the initial next population with elites
			population = new PriorityQueue<organism>(elites);

			// For all the most fit organisms
			for(int i = elites.size(); i < POPULATION_SIZE; i++){
				// Randomly breed them
				organism o = survivors.get(rand.nextInt(survivors.size())).breed(survivors.get(rand.nextInt(survivors.size())));
				// Find the child's best stack
				o.findBestStack();
				// Add the child to the population
				population.add(o);
			}

		}

		// Output the best stack we found
		System.out.println("Best stack for " + boxes.size());
		System.out.println(currentBest.r.toString());

		// Close the scanner
		s.close();

	}

}
