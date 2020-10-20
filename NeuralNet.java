package DESASTRES;

public class NeuralNet extends DESASTRE_COMPLETO implements Cloneable {
	static double[][] weightsInputHidden1, weightsHidden1Hidden2, weightsHidden2Output;
	static double[] input, output, hidden1, hidden2, biases = new double[3];
	
	public NeuralNet(int[] shape)
	{
		weightsInputHidden1 = new double[shape[2]][shape[0]];
		weightsHidden1Hidden2 = new double[shape[2]][shape[2]];
		weightsHidden2Output = new double[shape[1]][shape[2]];
		output = new double[shape[1]];
		input = new double[shape[0]];
		hidden1 = new double[shape[2]];
		hidden2 = new double[shape[2]];
		
		for(int i = 0; i < weightsInputHidden1.length; i++) {
			for(int j = 0; j < weightsInputHidden1[i].length; j++) {
				weightsInputHidden1[i][j] = Math.random() * 2 - 1;
			}
		}
		
		for(int i = 0; i < weightsHidden1Hidden2.length; i++) {
			for(int j = 0; j < weightsHidden1Hidden2[i].length; j++) {
				weightsHidden1Hidden2[i][j] = Math.random() * 2 - 1;
			}
		}
		
		for(int i = 0; i < weightsHidden2Output.length; i++) {
			for(int j = 0; j < weightsHidden2Output[i].length; j++) {
				weightsHidden2Output[i][j] = Math.random() * 2 - 1;
			}
		}
		
		for(int i = 0; i < biases.length; i++) {
			biases[i] = Math.random() * 2 - 1;
		}
	}
	
	private static double relu(double value)
	{
		return Math.max(0, value);
	}
	
	private static double softmax(double neuronValue) {
	    double sum = 0;
	    for(int i = 0; i < output.length; i++) {
	    	sum += Math.exp(output[i]);
	    }
	    return Math.exp(neuronValue) / sum;
	}
	
	public double[] predict(double[] inputDataset, double[][] defaults)
	{
		input = normalize(inputDataset, defaults);
		
		for(int i = 0; i < hidden1.length; i++) {
			double sum = 0;
			for(int j = 0; j < input.length; j++) {
				sum += weightsInputHidden1[i][j]*input[j];
			}
			hidden1[i] = relu(sum+biases[0]);
		}
		
		for(int i = 0; i < hidden2.length; i++) {
			double sum = 0;
			for(int j = 0; j < hidden1.length; j++) {
				sum += weightsHidden1Hidden2[i][j]*hidden1[j];
			}
			hidden2[i] = relu(sum+biases[1]);
		}
		
		for(int i = 0; i < output.length; i++) {
			double sum = 0;
			for(int j = 0; j < hidden2.length; j++) {
				sum += weightsHidden2Output[i][j]*hidden2[j];
			}
			output[i] = softmax(sum+biases[2]);
		}
		
		return output;
	}
	
	private static double[] normalize(double[] inputDataset, double[][] defaults)
	{
		double[] resultArray = new double[defaults.length];
		for(int i = 0; i < resultArray.length; i++) {
			resultArray[i] = (inputDataset[i] - defaults[i][0])/(defaults[i][1] - defaults[i][0]);
		}
		
		return resultArray;
	}
	
	public NeuralNet copy() throws CloneNotSupportedException
	{
		NeuralNet net = (NeuralNet)super.clone();
		return net;
	}
	
	public void mutate(double rate)
	{
		for(int i = 0; i < weightsInputHidden1.length; i++) {
			for(int j = 0; j < weightsInputHidden1[i].length; j++) {
				if(Math.random() < rate) {
					weightsInputHidden1[i][j] = Math.random() * 2 - 1;	
				}
			}
		}
		
		for(int i = 0; i < weightsHidden1Hidden2.length; i++) {
			for(int j = 0; j < weightsHidden1Hidden2[i].length; j++) {
				if(Math.random() < rate) {
					weightsHidden1Hidden2[i][j] = Math.random() * 2 - 1;	
				}
			}
		}
		
		for(int i = 0; i < weightsHidden2Output.length; i++) {
			for(int j = 0; j < weightsHidden2Output[i].length; j++) {
				if(Math.random() < rate) {
					weightsHidden2Output[i][j] = Math.random() * 2 - 1;	
				}
			}
		}
		
		for(int i = 0; i < biases.length; i++) {
			if(Math.random() < rate) {
				biases[i] = Math.random() * 2 - 1;	
			}
		}
	}
}
