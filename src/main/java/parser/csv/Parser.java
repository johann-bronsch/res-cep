package parser.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cep.main.event.Lion;

public class Parser {

	private String csvSplitBy;

	public Parser() {
		this(";");
	}

	public Parser(String csvSplitBy) {
		this.csvSplitBy = csvSplitBy;
	}

	public List<Lion> parseLion(String file) {
		List<Lion> lionList = new ArrayList<>();
		BufferedReader br = null;
		String line = "";
		try {

			br = new BufferedReader(new FileReader(file));
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] lion = line.split(csvSplitBy);
				lionList.add(newLion(lion));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
//		System.out.println("Done");
		return lionList;
	}

	private Lion newLion(String[] lionArray) {
		Lion lion = new Lion();
		lionArray = clearArray(lionArray);
		lion.setEventID(lionArray[0]);
		lion.setVisible(lionArray[1]);
		lion.setTimestamp(lionArray[2]);
		lion.setLocLong(lionArray[3]);
		lion.setLocLat(lionArray[4]);
		lion.setComments(lionArray[5]);
		lion.setEobsTemperature(lionArray[6]);
		lion.setSensorType(lionArray[7]);
		lion.setIndividualTaxonCanonicalName(lionArray[8]);
		lion.setTagLocalIdentifier(lionArray[9]);
		lion.setIndividualLocalIdentifier(lionArray[10]);
		lion.setStudyName(lionArray[11]);
		lion.setUtmEasting(lionArray[12]);
		lion.setUtmNorthing(lionArray[13]);
		lion.setUtmZone(lionArray[14]);
		lion.setStudyTimezone(lionArray[15]);
		lion.setStudyLocalTimestamp(lionArray[16]);
		return lion;
	}

	private String[] clearArray(String[] lionArray) {
		for (int i = 0; i < lionArray.length; i++) 
			if(lionArray[i].contains("\""))
				lionArray[i] = lionArray[i].substring(1, lionArray[i].length()-1);
		return lionArray;
	}

}
