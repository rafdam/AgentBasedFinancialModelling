import java.util.ArrayList;

public class Agent {
	ArrayList<Integer> linkedAgents;
	
	public Agent() {
		linkedAgents = new ArrayList<Integer>();
	}

	public ArrayList<Integer> getLinkedAgentsList(){
		return linkedAgents;
	}
}
