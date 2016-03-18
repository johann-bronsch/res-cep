package cep.main;

import java.util.List;
import java.util.Map;

import parser.csv.Parser;
import cep.main.event.Lion;
import cep.main.eventrunner.SendEventRunner;
import cep.main.listener.CEPListener;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class CEPMain {

	public static void main(String[] args) {
		System.out.println("Start!");
		CEPMain exm = new CEPMain();
		exm.runTest();
		System.out.println("Stop!");
	}

	public void runTest(UpdateListener listener) {
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("LionTick", Lion.class.getName());

		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();
		EPAdministrator cepAdm = cep.getEPAdministrator();

		EPStatement cepStatement = cepAdm.createEPL(compareTwoLionsStatement("#1940316", "#2772339"));
		cepStatement.addListener(listener);
		Parser parser = new Parser(",");
		Map<String, List<Lion>> lionMap = parser.parseCSV();

		for (List<Lion> lionList : lionMap.values()) {
			SendEventRunner ser = new SendEventRunner(cepRT, lionList);
			ser.start();
		}
	}

	public void runTest() {
		runTest(new CEPListener());
	}

	private String compareTwoLionsStatement(String tagOne, String tagTwo) {
		return "SELECT a.tagLocalIdentifier as LocalIdA, a.timestamp as TimestampA, b.tagLocalIdentifier as LocalIdB, b.timestamp as TimestampB, "
				+ "b.locLat as LatitudeB, b.locLong as LongitudeB, a.locLat as LatitudeA, a.locLong as LongitudeA " + "FROM LionTick(tagLocalIdentifier = '"
				+ tagOne + "').win:time(60 sec) as a, LionTick(tagLocalIdentifier = '" + tagTwo + "').win:time(60 sec) as b "
				+ "WHERE a.locLong = b.locLong AND a.locLat = b.locLat AND a.timestamp.between(b.timestamp.minus(24 hours), b.timestamp.plus(24 hours))";

	}

}