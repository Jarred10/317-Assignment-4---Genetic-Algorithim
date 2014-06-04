Jarred Green 1186602 James Ingerson 1180446

Genetic Algorithm Explained

	- Boxes are a list of orientations (each box also has a 0,0,0 orientation so it can be skipped)
	- Orientations are one way a box can be rotated
	- Organisms are a list of orientations, one from each box
	
	- Read in all the boxes
	- Make our initial population 
	- Check fitness of population (make best possible box stack)
	- While we haven't hit the organisms tested limit or time limit (15 seconds without change)
		- Mutate with a low chance by rotating one of the boxes
		- Test if we found a new best stack
		- Keep the top 15% of the population for the next generation
		- Weighted randomly cull the rest of the population (kill based on fitness, fittest more likely to live)
		- Refill the population by breeding
	
	Breeding:
	 	- Randomly choose two organisms
	 	- Copy one parent entirely
	 	- Randomly replace orientations with the partners respective orientation
	
	Test for a new best stack:
		- Fitness function is the highest possible stack in a row