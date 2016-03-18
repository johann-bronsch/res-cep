package cep.main.eventrunner;

import java.util.List;

import cep.main.event.Lion;

import com.espertech.esper.client.EPRuntime;

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