package cep.main.listener;

import java.util.Date;

import cep.main.event.ETLContainer;
import cep.main.event.EqualTick;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

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