package parser.csv.test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import parser.csv.Parser;
import cep.main.event.Lion;

public class ParserTest {

	@Test
	public void parserTest() {
		Parser parser = new Parser(",");
		ClassLoader classLoader = getClass().getClassLoader();
		List<Lion> lionList = parser.parseLion(classLoader.getResource("Tsavo_Lion_Study.csv").getFile());
		Map<String, List<Lion>> lionMap = splitLions(lionList);
		for (String lionKey : lionMap.keySet()) {
			List<Lion> llist = lionMap.get(lionKey);
			Collections.sort(llist);
			lionMap.replace(lionKey, llist);
		}
		writeFile(lionMap);
		// for (String lionKey : lionMap.keySet()) {
		// System.out.println(lionKey);
		// for (Lion lion : lionMap.get(lionKey)) {
		// System.out.println(lion);
		// }
		// }
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

	private void writeFile(Map<String, List<Lion>> lionMap) {
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			PrintWriter writer = new PrintWriter(classLoader.getResource("LionList.txt").getFile(), "UTF-8");
			for (String lionKey : lionMap.keySet()) {
				for (Lion lion : lionMap.get(lionKey)) {
					writer.println(lion.toString());
				}
			}
			writer.close();
		} catch (Exception e) {
		}
	}
}
