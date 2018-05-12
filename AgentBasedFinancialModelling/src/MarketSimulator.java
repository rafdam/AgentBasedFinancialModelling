import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		NetworkGenerator netGen = new NetworkGenerator(15, 0.005); // density must be close to 0 or whole network will be fully connected
		netGen.GenerateLinks();
		ArrayList<Integer> clasters = netGen.getClasters();
		MarketSimulator marSim = new MarketSimulator(0.15, clasters);
		ArrayList<Integer> marChange = marSim.simulateMarket(50);
		Path out = Paths.get("output.txt");
		FileWriter writer;
		try {
			writer = new FileWriter("output.txt");
			for(Integer inti : marChange){
				writer.write(inti.toString() + "\n");
				System.out.println(inti);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Exception error");
			e.printStackTrace();
		}
		
		System.out.println("done");
	}

}
