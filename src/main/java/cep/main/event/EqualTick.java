package cep.main.event;

import java.util.Date;

public class EqualTick {

	private Date timestampA;

	private Date timestampB;

	private double locLatA;

	private double locLongA;

	private double locLatB;

	private double locLongB;

	private String localIdA;

	private String localIdB;

	public EqualTick() {
		this(null, null, 0, 0, 0, 0, null, null);
	}

	public EqualTick(Date timestampA, Date timestampB, double locLatA, double locLongA, double locLatB, double locLongB, String localIdA, String localIdB) {
		this.timestampA = timestampA;
		this.timestampB = timestampB;
		this.locLatA = locLatA;
		this.locLongA = locLongA;
		this.locLatB = locLatB;
		this.locLongB = locLongB;
		this.localIdA = localIdA;
		this.localIdB = localIdB;
	}

	public Date getTimestampA() {
		return timestampA;
	}

	public void setTimestampA(Date timestampA) {
		this.timestampA = timestampA;
	}

	public Date getTimestampB() {
		return timestampB;
	}

	public void setTimestampB(Date timestampB) {
		this.timestampB = timestampB;
	}

	public double getLocLatA() {
		return locLatA;
	}

	public void setLocLatA(double locLatA) {
		this.locLatA = locLatA;
	}

	public double getLocLongA() {
		return locLongA;
	}

	public void setLocLongA(double locLongA) {
		this.locLongA = locLongA;
	}

	public double getLocLatB() {
		return locLatB;
	}

	public void setLocLatB(double locLatB) {
		this.locLatB = locLatB;
	}

	public double getLocLongB() {
		return locLongB;
	}

	public void setLocLongB(double locLongB) {
		this.locLongB = locLongB;
	}

	public String getLocalIdA() {
		return localIdA;
	}

	public void setLocalIdA(String localIdA) {
		this.localIdA = localIdA;
	}

	public String getLocalIdB() {
		return localIdB;
	}

	public void setLocalIdB(String localIdB) {
		this.localIdB = localIdB;
	}

	@Override
	public String toString() {
		return "TagID A: " + localIdA + " / TagID B: " + localIdB + " / Date: " + Lion.DATE_FORMAT_OUT.format(timestampA) + " / Lat/Long: " + locLatA + "/" + locLongA
				+ "\n========================================================================================================";
	}

}
