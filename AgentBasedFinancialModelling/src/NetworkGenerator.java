import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NetworkGenerator {
private int agentCount; // number of agents in network
private double density; // probability that k'th 'pair' of Agents will get a link between
private boolean[][] similarityMatrix; // matrix desctribing if 2 agents are linked	
	ArrayList<Agent> agentList;
	public NetworkGenerator(int N, double c) {
		agentList = new ArrayList<Agent>();
		for(int ii = 0 ; ii < N; ii++){
			agentList.add(new Agent()); // initializing arrayList with agents
		}
		this.agentCount = N;
		this.density = c/N;
		this.similarityMatrix = new boolean[N][N];
		
	}
	
	public void GenerateLinks(){
		for(int ii = 0 ; ii < agentCount-1; ii ++){
			for(int jj = ii+1; jj < agentCount; jj ++){
				if(similarityMatrix[ii][jj] == false){
					if(Math.random() <= density){
						similarityMatrix[ii][jj] = true;
						similarityMatrix[jj][ii] = true;
						agentList.get(ii).getLinkedAgentsList().add(jj);
						agentList.get(jj).getLinkedAgentsList().add(ii);
					}
				}
			}
		}
	}
	
	public ArrayList<Integer> getClasters(){
		ArrayList<Integer> clasters = new ArrayList<Integer>(); //arrayList containing all the clasters with number of agents in it
		ArrayList<Integer> tmpAgentListToCheck = new ArrayList<Integer>(); //list of agents to check their neighbours
		boolean[] visitedAgentList = new boolean[agentCount];
		for(int ii = 0; ii < agentList.size(); ii++){
			tmpAgentListToCheck.clear();
			if(visitedAgentList[ii] == false){
				//System.out.println("agent: " + ii +" jest sprawdzany"); //for testing purposes
				visitedAgentList[ii] = true;
				tmpAgentListToCheck = new ArrayList<Integer>();
				tmpAgentListToCheck.add(ii);
				for(int jj = 0; jj < tmpAgentListToCheck.size(); jj++){
					for(int ww = 0; ww < agentList.get(tmpAgentListToCheck.get(jj)).getLinkedAgentsList().size(); ww ++){
						if(visitedAgentList[agentList.get(tmpAgentListToCheck.get(jj)).getLinkedAgentsList().get(ww)] == false){
							tmpAgentListToCheck.add(agentList.get(tmpAgentListToCheck.get(jj)).getLinkedAgentsList().get(ww));
							visitedAgentList[agentList.get(tmpAgentListToCheck.get(jj)).getLinkedAgentsList().get(ww)] = true;
							//System.out.println("dodano agenta do klastra"); //for testing purposes
						}
						else{
							//System.out.println("Agent nie ma sąsiadów"); //for testing purposes
						}
					}
				}
			}
			else{
				//System.out.println("agent: " + ii + "był już sprawdzony"); //for testing purposes
			}
			if(!tmpAgentListToCheck.isEmpty())	{
				clasters.add(tmpAgentListToCheck.size());
			}
		}
		//System.out.println("Maks klaster: " + Collections.max(clasters));
		return clasters;
		
	}
	public static void main(String[] args){
		System.out.println("Uruchomiony");
		NetworkGenerator netGen = new NetworkGenerator(15, 0.005); // density must be close to 0 or whole network will be fully connected;
		System.out.println("Sieć zdefiniowana");
		netGen.GenerateLinks();
		System.out.println("Połączenia utworzone");
		ArrayList<Integer> clasters = netGen.getClasters();
		System.out.println("KlastryUtworzone, o rozmiarach kolejno: ");
		for(Integer clast : clasters){
			System.out.println(clast);
		}
	}
}
	
