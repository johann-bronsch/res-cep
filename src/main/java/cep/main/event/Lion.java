package cep.main.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lion implements Comparable<Lion> {
	
	private int eventID;
	
	private boolean visible;
	
	private Date timestamp;
	
	private double locLong;
	
	private double locLat;
	
	private String comments;
	
	private String eobsTemperature;
	
	private String sensorType;
	
	private String individualTaxonCanonicalName;
	
	private String tagLocalIdentifier;
	
	private String individualLocalIdentifier;
	
	private String studyName;
	
	private double utmEasting;
	
	private double utmNorthing;
	
	private String utmZone;
	
	private String studyTimezone;
	
	private Date studyLocalTimestamp;
	
	private static final DateFormat DATE_FORMAT_IN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static final DateFormat DATE_FORMAT_OUT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	
	public void setEventID(String eventID) {
		this.eventID = Integer.parseInt(eventID);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setVisible(String visible) {
		this.visible = Boolean.getBoolean(visible);
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		try {
			this.timestamp = DATE_FORMAT_IN.parse(timestamp);
		} catch (ParseException e) {}
	}

	public double getLocLong() {
		return locLong;
	}

	public void setLocLong(double locLong) {
		this.locLong = locLong;
	}
	
	public void setLocLong(String locLong) {
		this.locLong = Double.parseDouble(locLong);
	}

	public double getLocLat() {
		return locLat;
	}

	public void setLocLat(double locLat) {
		this.locLat = locLat;
	}
	
	public void setLocLat(String locLat) {
		this.locLat = Double.parseDouble(locLat);
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEobsTemperature() {
		return eobsTemperature;
	}

	public void setEobsTemperature(String eobsTemperature) {
		this.eobsTemperature = eobsTemperature;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getIndividualTaxonCanonicalName() {
		return individualTaxonCanonicalName;
	}

	public void setIndividualTaxonCanonicalName(String individualTaxonCanonicalName) {
		this.individualTaxonCanonicalName = individualTaxonCanonicalName;
	}

	public String getTagLocalIdentifier() {
		return tagLocalIdentifier;
	}

	public void setTagLocalIdentifier(String tagLocalIdentifier) {
		this.tagLocalIdentifier = tagLocalIdentifier;
	}

	public String getIndividualLocalIdentifier() {
		return individualLocalIdentifier;
	}

	public void setIndividualLocalIdentifier(String individualLocalIdentifier) {
		this.individualLocalIdentifier = individualLocalIdentifier;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public double getUtmEasting() {
		return utmEasting;
	}

	public void setUtmEasting(double utmEasting) {
		this.utmEasting = utmEasting;
	}
	
	public void setUtmEasting(String utmEasting) {
		this.utmEasting = Double.parseDouble(utmEasting);
	}

	public double getUtmNorthing() {
		return utmNorthing;
	}

	public void setUtmNorthing(double utmNorthing) {
		this.utmNorthing = utmNorthing;
	}
	
	public void setUtmNorthing(String utmNorthing) {
		this.utmNorthing = Double.parseDouble(utmNorthing);
	}

	public String getUtmZone() {
		return utmZone;
	}

	public void setUtmZone(String utmZone) {
		this.utmZone = utmZone;
	}

	public String getStudyTimezone() {
		return studyTimezone;
	}

	public void setStudyTimezone(String studyTimezone) {
		this.studyTimezone = studyTimezone;
	}

	public Date getStudyLocalTimestamp() {
		return studyLocalTimestamp;
	}

	public void setStudyLocalTimestamp(Date studyLocalTimestamp) {
		this.studyLocalTimestamp = studyLocalTimestamp;
	}
	
	public void setStudyLocalTimestamp(String studyLocalTimestamp) {
		try {
			this.studyLocalTimestamp = DATE_FORMAT_IN.parse(studyLocalTimestamp);
		} catch (ParseException e) {}
	}
	
//	@Override
//	public String toString(){
//		return "Name: " + tagLocalIdentifier + " / Lat: " + locLat + " / Long: " + locLong + " Date: " + DATE_FORMAT_OUT.format(timestamp); 
//	}
	
	@Override
	public String toString(){
		return "LionTick={tli=" + tagLocalIdentifier + ", locLat=" + locLat + ", locLong=" + locLong + ", timestamp=" + DATE_FORMAT_IN.format(timestamp) + "}"; 
	}
	
//	@Override
//	public String toString(){
//		return "Name: " + tagLocalIdentifier + " / Date: " + DATE_FORMAT_OUT.format(timestamp); 
//	}

	/* 
	 * @return -1, 0, or 1, if this timestamp is smaller, same size, or greater than
	 *         the given objects timestamp, respectively.
	 */
	@Override
	public int compareTo(Lion otherLion) {
		return this.timestamp.compareTo(otherLion.timestamp);
	}

}
