package burciaga.binf.fasta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadedFastaParser {
	
	 private String header, sequence;
	
	
	public static List<MultiThreadedFastaParser> parseFasta(File inFile)
			throws Exception {
		BufferedReader inReader = new BufferedReader(new FileReader(inFile));
		List <MultiThreadedFastaParser> fastaList = new ArrayList<MultiThreadedFastaParser>();
		StringBuilder build = new StringBuilder();
		String nextLine = inReader.readLine();

		// iterate through lines of file
		while (nextLine != null) {
			MultiThreadedFastaParser fastaObj = new MultiThreadedFastaParser();

			// if header line
			if (nextLine.startsWith(">")) {
				fastaObj.header = nextLine.replace("> ", "");
			}

			nextLine = inReader.readLine();

			// while sequence line
			while (nextLine != null && !nextLine.startsWith(">")) {
				build.append(nextLine);
				nextLine = inReader.readLine();
			}
			fastaObj.sequence = build.toString();
 
			build.setLength(0);
			fastaList.add(fastaObj);
		}
		inReader.close();
		return fastaList;
	}

	public static void main(String[] args) throws Exception {
		File inFile = new File("/home/bmb0205/ResumeCode/Java/FastaParsers/example.fasta");
		File outFile = new File("/home/bmb0205/ResumeCode/Java/FastaParsers/out.txt");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		parseFasta(inFile);
	}

}
