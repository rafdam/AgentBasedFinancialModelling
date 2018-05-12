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
	
	public ArrayList<Integer> simulateMarket(int numberOfJumps){
		ArrayList<Integer> marketChange = new ArrayList<Integer>();
		int oneJumpMarketChange;
		for(int ii = 0; ii < numberOfJumps; ii++){
			oneJumpMarketChange = 0;
			for(Integer clast: clasters){
				double rand = Math.random();
				if(rand <= actionProbability){
					oneJumpMarketChange += clast;
				}
				else if(rand > actionProbability && rand <= 2*actionProbability){
					oneJumpMarketChange -= clast;
				}
				else{
					// do nothing, no market change, no buying, no selling
				}
			}
			marketChange.add(oneJumpMarketChange);
		}
		return marketChange;
	}
	
	public static void main(String[] args){
		NetworkGenerator netGen = new NetworkGenerator(10000, 0.5); // density must be close to 0 or whole network will be fully connected
		netGen.GenerateLinks();
		for(int i = 0; i < 10; i ++){
			String path = "output"+(i+1)+".txt";
			ArrayList<Integer> clasters = netGen.getClasters();
			MarketSimulator marSim = new MarketSimulator(0.024*(i+1), clasters);
			ArrayList<Integer> marChange = marSim.simulateMarket(1000);
			FileWriter writer;
			try {
				writer = new FileWriter(path);
				for(Integer inti : marChange){
					writer.write(inti.toString() + "\n");
				}
				writer.close();
			} catch (IOException e) {
				System.out.println("Exception error");
				e.printStackTrace();
			}
		}
		
		
		System.out.println("done");
	}

}
