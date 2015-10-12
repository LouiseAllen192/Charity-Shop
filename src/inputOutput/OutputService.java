package inputOutput;

public class OutputService {
	
	public void outputData(String data){
		
		System.out.print(data);
	}
	
	public void outputErrorMsg(String data){
		
		System.out.print("Error - " + data);
	}
}
