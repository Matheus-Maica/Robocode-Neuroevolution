package DESASTRES;

public class GA_Source extends DESASTRE_COMPLETO {
	public NeuralNet[] createPopulation(int populationSize, int[] shape)
	{
		NeuralNet[] individuals = new NeuralNet[populationSize];
		
		for(int individual = 0; individual < individuals.length; individual++) {
			individuals[individual] = new NeuralNet(shape);
		}
		
		return individuals;
	}
	
	public NeuralNet[] selectFittest(NeuralNet[] individuals)
	{	
		return individuals;
	}
}
