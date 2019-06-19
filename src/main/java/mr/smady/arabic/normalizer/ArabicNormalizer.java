package mr.smady.arabic.normalizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArabicNormalizer {

	private static ArabicNormalizer THIS = new ArabicNormalizer();

	public static ArabicNormalizer getInstance() {
		return THIS;
	}

	private Set<Character> allArabic;
	private Set<Character> arabicVowels;
	private Map<Character, String> arabicReplace;

	public ArabicNormalizer() {
		try {
			allArabic = new HashSet<>();
			List<String> lines = Files.readAllLines(Paths.get("arabic_all.txt"));
			for (String line : lines) {
				allArabic.add(line.charAt(0));
			}

			arabicVowels = new HashSet<>();
			lines = Files.readAllLines(Paths.get("arabic_dialects.txt"));
			for (String line : lines) {
				arabicVowels.add(line.charAt(0));
			}

			arabicReplace = new HashMap<>();
			lines = Files.readAllLines(Paths.get("arabic_replace.txt"));
			for (String line : lines) {
				String parts[] = line.split("\t");
				arabicReplace.put(parts[0].charAt(0), parts[2]);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.fillInStackTrace());
		}
	}

	public String normalize(String text) {
		StringBuilder sb = new StringBuilder();
		String last = null;
		for (char c : text.toCharArray()) {
			if (allArabic.contains(c)) {
				if (!arabicVowels.contains(c)) {
					if (arabicReplace.containsKey(c)) {
						last = arabicReplace.get(c);
						sb.append(last);
					} else {
						last = "" + c;
						sb.append(last);
					}
				}
			} else if (!" ".equals(last)) {
				last = " ";
				sb.append(last);
			}
		}
		return sb.toString().trim();
	}

}
