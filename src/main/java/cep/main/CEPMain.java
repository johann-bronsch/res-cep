package cep.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.csv.Parser;
import cep.main.event.ETLContainer;
import cep.main.event.EqualTick;
import cep.main.event.Lion;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class CEPMain {

	public class SendEventRunner extends Thread {

		private EPRuntime cepRT;

		private List<Lion> lionList;

		public SendEventRunner(EPRuntime cepRT, List<Lion> lionList) {
			this.cepRT = cepRT;
			this.lionList = lionList;
		}

		@Override
		public void run() {
			for (Lion lion : lionList) {
				cepRT.sendEvent(lion);
			}
		}

	}

	public class CEPListener implements UpdateListener {

		@Override
		public void update(EventBean[] newData, EventBean[] arg1) {
			EqualTick et = new EqualTick(((Date) newData[0].get("TimestampA")), ((Date) newData[0].get("TimestampB")), (double) newData[0].get("LatitudeA"),
					(double) newData[0].get("LongitudeA"), (double) newData[0].get("LatitudeB"), (double) newData[0].get("LongitudeB"),
					(String) newData[0].get("LocalIdA"), (String) newData[0].get("LocalIdB"));
			ETLContainer.getInstance().addEqualTick(et);
			System.out.println(et);
		}
	}

	public static void main(String[] args) {
		System.out.println("Start!");
		CEPMain exm = new CEPMain();
		exm.runTest();
		System.out.println("Stop!");
	}

	public void runTest(UpdateListener listener) {
		// The Configuration is meant only as an initialization-time object.
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("LionTick", Lion.class.getName());

		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();
		EPAdministrator cepAdm = cep.getEPAdministrator();

		EPStatement cepStatement = cepAdm
				.createEPL("select a.tagLocalIdentifier as LocalIdA, a.timestamp as TimestampA, b.tagLocalIdentifier as LocalIdB, b.timestamp as TimestampB, "
						+ "b.locLat as LatitudeB, b.locLong as LongitudeB, a.locLat as LatitudeA, a.locLong as LongitudeA "
						+ "from LionTick(tagLocalIdentifier = '#1940316').win:time(60 sec) as a, LionTick(tagLocalIdentifier = '#2772339').win:time(60 sec) as b "
						+ "where a.locLong = b.locLong and a.locLat = b.locLat and a.timestamp.between(b.timestamp.minus(24 hours), b.timestamp.plus(24 hours))");

		cepStatement.addListener(listener);
		Map<String, List<Lion>> lionMap = parseCSV();

		for (List<Lion> lionList : lionMap.values()) {
			SendEventRunner ser = new SendEventRunner(cepRT, lionList);
			ser.start();
		}
	}

	public void runTest() {
		runTest(new CEPListener());
	}

	private Map<String, List<Lion>> parseCSV() {
		Parser parser = new Parser(",");
		ClassLoader classLoader = getClass().getClassLoader();
		List<Lion> lionList = parser.parseLion(classLoader.getResource("Tsavo_Lion_Study.csv").getFile());
		Map<String, List<Lion>> lionMap = splitLions(lionList);
		for (String lionKey : lionMap.keySet()) {
			List<Lion> llist = lionMap.get(lionKey);
			Collections.sort(llist);
			lionMap.replace(lionKey, llist);
		}
		return lionMap;
	}

	private Map<String, List<Lion>> splitLions(List<Lion> lionList) {
		Map<String, List<Lion>> lionMap = new HashMap<>();
		for (Lion lion : lionList) {
			if (lionMap.containsKey(lion.getTagLocalIdentifier())) {
				List<Lion> llist = lionMap.get(lion.getTagLocalIdentifier());
				llist.add(lion);
				lionMap.replace(lion.getTagLocalIdentifier(), llist);
			} else {
				List<Lion> llist = new ArrayList<>();
				llist.add(lion);
				lionMap.put(lion.getTagLocalIdentifier(), llist);
			}
		}
		return lionMap;
	}

}