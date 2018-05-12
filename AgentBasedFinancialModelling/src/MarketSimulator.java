
public class MarketSimulator {
	
private double actionProbability; // probability that agent will buy or sell

	public MarketSimulator(double prob) {
		this.actionProbability = prob;
	}
	
	public MarketSimulator(){
		this.actionProbability = 0.25;
	}

}
