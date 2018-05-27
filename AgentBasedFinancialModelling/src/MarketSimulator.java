import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MarketSimulator {
	
private double actionProbability; // probability that agent will buy or sell
private ArrayList<Integer> clasters;
	public MarketSimulator(double prob, ArrayList<Integer> clasts) {
		this.actionProbability = prob;
		this.clasters = clasts;
	}
	
	public MarketSimulator(ArrayList<Integer> clasts){
		this.actionProbability = 0.25;
		this.clasters = clasts;
		
	}
	
	public ArrayList<Integer> simulateMarket(int numberOfJumps, int initMarketChange, double offSetValue){
		ArrayList<Integer> marketChange = new ArrayList<Integer>();
		double positiveOffset = 0;
		double negativeOffset = 0;
		int oneJumpMarketChange;
		double sumOfClasts = 0;
		for(Integer clast: clasters){
			sumOfClasts += clast;
		}
		oneJumpMarketChange = initMarketChange;
		for(int ii = 0; ii < numberOfJumps; ii++){
			for(Integer clast: clasters){
				double rand = Math.random();
				if(rand <= actionProbability + positiveOffset){
					oneJumpMarketChange += clast;
				}
				else if(rand > actionProbability + positiveOffset && rand <= (2*actionProbability) + negativeOffset){
					oneJumpMarketChange -= clast;
				}
				else{
					// do nothing, no market change, no buying, no selling
				}
			}
			if(oneJumpMarketChange > 0){
				positiveOffset += offSetValue * ((double)oneJumpMarketChange / sumOfClasts);
				//negativeOffset -= offSetValue * ((double)oneJumpMarketChange / sumOfClasts);
			}
			else if(oneJumpMarketChange < 0){
				//positiveOffset += offSetValue * ((double)oneJumpMarketChange / sumOfClasts);
				negativeOffset -= offSetValue * ((double)oneJumpMarketChange / sumOfClasts);
			}
			marketChange.add(oneJumpMarketChange);
			oneJumpMarketChange = 0;
		}
		System.out.println("Positive: " + positiveOffset + " Negative: " + negativeOffset);
		return marketChange;
	}
	
	public static void main(String[] args) throws IOException{
		double[] probs = {0.001, 0.005, 0.01, 0.05, 0.1, 0.2, 0.5};
		double[] constC = {0.7, 0.8, 0.9, 0.94, 0.97, 0.99};
		for(int ww = 4; ww < 5; ww++){
			for(int zz = 0; zz < 7; zz++){
				ArrayList<Integer> writingList = new ArrayList<Integer>();
				for(int ii = 0; ii < 100; ii++){
					NetworkGenerator netGen = new NetworkGenerator(1000, constC[ww]);
					netGen.GenerateLinks();
					ArrayList<Integer> clasters = netGen.getClasters();
					MarketSimulator marSim = new MarketSimulator(probs[zz], clasters);
					ArrayList<Integer> marChange = marSim.simulateMarket(1000, 0, 0.003);
					for(Integer inti : marChange){
						writingList.add(inti);
					}
				}
				String path = "c"+constC[ww]+"p"+probs[zz]+".txt";
				FileWriter writer;
				writer = new FileWriter(path);
				for(Integer asd : writingList){
					writer.write( asd + "\n");
				}
				writer.close();
				System.out.println("file: " + path + " .Done");
			}
		}
	}

}
