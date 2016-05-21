package burciaga.binf.fasta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class FastaSequence {
	
	private String header, sequence;
	private int id;
				
	public List<FastaSequence> parseFastaFile(File inFile) throws Exception {
		BufferedReader inReader = new BufferedReader(new FileReader(inFile));
		List <FastaSequence> fastaList = new ArrayList<FastaSequence>();
		StringBuilder build = new StringBuilder();
		String nextLine = inReader.readLine();

		// iterate through lines of file
		while (nextLine != null) {
			FastaSequence fastaObj = new FastaSequence();

			// if header line
			if (nextLine.startsWith(">")) {
				String fastaHeader = nextLine.replace("> ", "");
				fastaObj.header = fastaHeader;
				System.out.println(fastaHeader.split("|")[1]);
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
	}

