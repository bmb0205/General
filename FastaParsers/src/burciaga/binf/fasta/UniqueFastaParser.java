package burciaga.binf.fasta;

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Class UniqueFastaParser takes a FASTA formatted file, writes unique sequences to output,
 * and counts the number of times each sequences was seen.
 */
public class UniqueFastaParser {

	private String header, sequence;
	private Integer count;
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 7 * this.sequence.hashCode();
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		UniqueFastaParser other = (UniqueFastaParser) object;
		return other.sequence == this.sequence;
	}
	
	// writes count and sequence to outfile in ascending order
	public static void writeUnique(File inFile, File outFile) throws Exception {
		BufferedWriter outWriter = new BufferedWriter(new FileWriter(outFile));
		
		List<UniqueFastaParser> fastaList = readFastaFile(inFile);
		
		List<String> seqList = getSeqList(fastaList);		
		Set<String> seqSet = new HashSet<String>();
		
		Map<String, Integer> unsortedMap = new HashMap<String, Integer>();
		
		// creates unsorted hashmap of seqence and count
		for (String seq : seqList) {
			unsortedMap.put(seq, Collections.frequency(seqList, seq));
		}
				
		// adds count attribute to UniqueFastaParser objects in fastaList
		for (UniqueFastaParser fs : fastaList) {
			int count = unsortedMap.get(fs.sequence);
			fs.count = count;
		}
		
		// compare objects by count
		Comparator<UniqueFastaParser> fastaComparator = new Comparator<UniqueFastaParser>() {
			public int compare(UniqueFastaParser f1, UniqueFastaParser f2) {
				return Double.compare(f2.count, f1.count);
			}
		};
		
		Collections.sort(fastaList, fastaComparator);
		Set<UniqueFastaParser> fastaSet = new HashSet<UniqueFastaParser>(fastaList);

		// create fastaSet from fastaList, continue if not already there
		for (UniqueFastaParser fs : fastaList) {
			if (! fastaSet.contains(fs.sequence)) {
				fastaSet.add(fs);
			}
			else {
				continue;
			}
			
			for (UniqueFastaParser fasta : fastaSet) {
				// if the sequence isn't in the empty set, add it and write it to outfile
				if ( ! seqSet.contains(fasta.sequence)) {
					seqSet.add(fasta.sequence);
					outWriter.write(">" + fasta.count + "\n" + fasta.sequence + "\n");
				}
			}
		}
		outWriter.close();
	}
	
	// returns list of sequence strings
	public static List<String> getSeqList(List<UniqueFastaParser> fastaList) {
		List<String> seqList = new ArrayList<String>();
		for (UniqueFastaParser fs : fastaList) {
			seqList.add(fs.sequence);
		}
		return seqList;
	}

	// method returns list of UniqueFastaParser objects containing header and sequence attributes
	public static List<UniqueFastaParser> readFastaFile(File inFile)
			throws Exception {
		BufferedReader inReader = new BufferedReader(new FileReader(inFile));
		List<UniqueFastaParser> fastaList = new ArrayList<UniqueFastaParser>();
		StringBuilder build = new StringBuilder();
		String nextLine = inReader.readLine();

		// iterate through lines of file
		while (nextLine != null) {
			UniqueFastaParser fastaObj = new UniqueFastaParser();

			// if header line
			if (nextLine.startsWith(">")) {
				fastaObj.header = nextLine.replace("> ", "");
			}

			// if sequence line
			nextLine = inReader.readLine();

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
		writeUnique(inFile, outFile);
	}
}