package cep.main.event;

import java.util.ArrayList;
import java.util.List;


public class ETLContainer {

	private List<EqualTick> etl;
    
	private ETLContainer() {
		etl = new ArrayList<>();
    }
	
	public void addEqualTick(EqualTick et){
		etl.add(et);
	}
	
	public List<EqualTick> getETL(){
		return etl;
	}

    private static ETLContainer instance = null;

    public static synchronized ETLContainer getInstance() {
        if (instance == null) {
            instance = new ETLContainer();
        }
        return instance;
    }
	
}
