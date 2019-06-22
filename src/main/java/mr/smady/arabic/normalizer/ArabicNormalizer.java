package mr.smady.arabic.normalizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArabicNormalizer {

	private static final char[] ALL_ARABIC = { '\uFEFF', '\u060B', '\u060F', '\u0615', '\u0621', '\u0622', '\u0623',
			'\u0624', '\u0625', '\u0626', '\u0627', '\u0628', '\u0629', '\u062A', '\u062B', '\u062C', '\u062D',
			'\u062E', '\u062F', '\u0630', '\u0631', '\u0632', '\u0633', '\u0634', '\u0635', '\u0636', '\u0637',
			'\u0638', '\u0639', '\u063A', '\u0640', '\u0641', '\u0642', '\u0643', '\u0644', '\u0645', '\u0646',
			'\u0647', '\u0648', '\u0649', '\u064A', '\u064B', '\u064C', '\u064D', '\u064E', '\u064F', '\u0650',
			'\u0651', '\u0652', '\u0653', '\u0655', '\u0656', '\u0657', '\u0658', '\u0659', '\u065A', '\u066E',
			'\u0670', '\u0671', '\u0672', '\u0673', '\u0674', '\u06D7', '\u06D8', '\u06D9', '\u06DA', '\u06DB',
			'\u06DC', '\u06E0', '\u06E1', '\u06E2', '\u06E3', '\u06E4', '\u06E7', '\u06E8', '\u06EA', '\u06EB',
			'\u06EC', '\uFBAA', '\uFBAB', '\uFBAC', '\uFBFC', '\uFBFD', '\uFBFE', '\uFE76', '\uFE77', '\uFE78',
			'\uFE7A', '\uFE7B', '\uFE7C', '\uFE7E', '\uFE80', '\uFE81', '\uFE82', '\uFE83', '\uFE84', '\uFE85',
			'\uFE86', '\uFE87', '\uFE88', '\uFE8B', '\uFE8C', '\uFE8D', '\uFE8E', '\uFE8F', '\uFE90', '\uFE91',
			'\uFE92', '\uFE93', '\uFE94', '\uFE95', '\uFE96', '\uFE97', '\uFE98', '\uFE99', '\uFE9B', '\uFE9C',
			'\uFE9D', '\uFE9F', '\uFEA0', '\uFEA1', '\uFEA2', '\uFEA3', '\uFEA4', '\uFEA5', '\uFEA6', '\uFEA7',
			'\uFEA8', '\uFEA9', '\uFEAA', '\uFEAB', '\uFEAC', '\uFEAD', '\uFEAE', '\uFEAF', '\uFEB0', '\uFEB1',
			'\uFEB2', '\uFEB3', '\uFEB4', '\uFEB5', '\uFEB6', '\uFEB7', '\uFEB8', '\uFEB9', '\uFEBA', '\uFEBB',
			'\uFEBC', '\uFEBD', '\uFEBE', '\uFEBF', '\uFEC0', '\uFEC1', '\uFEC2', '\uFEC3', '\uFEC4', '\uFEC5',
			'\uFEC7', '\uFEC8', '\uFEC9', '\uFECA', '\uFECB', '\uFECC', '\uFECD', '\uFECF', '\uFED0', '\uFED1',
			'\uFED2', '\uFED3', '\uFED4', '\uFED5', '\uFED6', '\uFED7', '\uFED8', '\uFED9', '\uFEDA', '\uFEDB',
			'\uFEDC', '\uFEDD', '\uFEDE', '\uFEDF', '\uFEE0', '\uFEE1', '\uFEE2', '\uFEE3', '\uFEE4', '\uFEE5',
			'\uFEE6', '\uFEE7', '\uFEE8', '\uFEE9', '\uFEEA', '\uFEEB', '\uFEEC', '\uFEED', '\uFEEE', '\uFEEF',
			'\uFEF0', '\uFEF1', '\uFEF2', '\uFEF3', '\uFEF4', '\uFEF5', '\uFEF6', '\uFEF7', '\uFEF8', '\uFEF9',
			'\uFEFB', '\uFEFC', };

	private static final char[] ALL_DIALECTS = { '\uFEFF', '\u0615', '\u0640', '\u064B', '\u064C', '\u064D', '\u064E',
			'\u064F', '\u0650', '\u0651', '\u0652', '\u0653', '\u0655', '\u0656', '\u0657', '\u0658', '\u0659',
			'\u065A', '\u0670', '\u06D7', '\u06D8', '\u06D9', '\u06DA', '\u06DB', '\u06DC', '\u06E0', '\u06E1',
			'\u06E2', '\u06E3', '\u06E4', '\u06E7', '\u06E8', '\u06EA', '\u06EB', '\u06EC', '\uFE76', '\uFE77',
			'\uFE78', '\uFE7A', '\uFE7B', '\uFE7C', '\uFE7E', };

	private static final String[] ARABIC_REPLACE = { "\uFEFF \u0641", "\u060F \u0639", "\u0629 \u0647", "\u0622 \u0627",
			"\u0623 \u0627", "\u0625 \u0627", "\u0649 \u064A", "\uFE80 \u0621", "\uFE81 \u0627", "\uFE82 \u0627",
			"\uFE83 \u0627", "\uFE84 \u0627", "\uFE85 \u0624", "\uFE86 \u0624", "\uFE87 \u0627", "\uFE88 \u0627",
			"\uFE8B \u0626", "\uFE8C \u0626", "\uFE8D \u0627", "\uFE8E \u0627", "\uFE8F \u0628", "\uFE90 \u0628",
			"\uFE91 \u0628", "\uFE92 \u0628", "\uFE93 \u0647", "\uFE94 \u0647", "\uFE95 \u062A", "\uFE96 \u062A",
			"\uFE97 \u062A", "\uFE98 \u062A", "\uFE99 \u062B", "\uFE9B \u062B", "\uFE9C \u062B", "\uFE9D \u062C",
			"\uFE9F \u062C", "\uFEA0 \u062C", "\uFEA1 \u062D", "\uFEA2 \u062D", "\uFEA3 \u062D", "\uFEA4 \u062D",
			"\uFEA5 \u062E", "\uFEA6 \u062E", "\uFEA7 \u062E", "\uFEA8 \u062E", "\uFEA9 \u062F", "\uFEAA \u062F",
			"\uFEAB \u0630", "\uFEAC \u0630", "\uFEAD \u0631", "\uFEAE \u0631", "\uFEAF \u0632", "\uFEB0 \u0632",
			"\uFEB1 \u0633", "\uFEB2 \u0633", "\uFEB3 \u0633", "\uFEB4 \u0633", "\uFEB5 \u0634", "\uFEB6 \u0634",
			"\uFEB7 \u0634", "\uFEB8 \u0634", "\uFEB9 \u0635", "\uFEBA \u0635", "\uFEBB \u0635", "\uFEBC \u0635",
			"\uFEBD \u0636", "\uFEBE \u0636", "\uFEBF \u0636", "\uFEC0 \u0636", "\uFEC1 \u0637", "\uFEC2 \u0637",
			"\uFEC3 \u0637", "\uFEC4 \u0637", "\uFEC5 \u0638", "\uFEC7 \u0638", "\uFEC8 \u0638", "\uFEC9 \u0639",
			"\uFECA \u0639", "\uFECB \u0639", "\uFECC \u0639", "\uFECD \u063A", "\uFECF \u063A", "\uFED0 \u063A",
			"\uFED1 \u0641", "\uFED2 \u0641", "\uFED3 \u0641", "\uFED4 \u0641", "\uFED5 \u0642", "\uFED6 \u0642",
			"\uFED7 \u0642", "\uFED8 \u0642", "\uFED9 \u0643", "\uFEDA \u0643", "\uFEDB \u0643", "\uFEDC \u0643",
			"\uFEDD \u0644", "\uFEDE \u0644", "\uFEDF \u0644", "\uFEE0 \u0644", "\uFEE1 \u0645", "\uFEE2 \u0645",
			"\uFEE3 \u0645", "\uFEE4 \u0645", "\uFEE5 \u0646", "\uFEE6 \u0646", "\uFEE7 \u0646", "\uFEE8 \u0646",
			"\uFEE9 \u0647", "\uFEEA \u0647", "\uFEEB \u0647", "\uFEEC \u0647", "\uFEED \u0648", "\uFEEE \u0648",
			"\uFEEF \u064A", "\uFEF0 \u064A", "\uFEF1 \u064A", "\uFEF2 \u064A", "\uFEF3 \u064A", "\uFEF4 \u064A",
			"\uFEF5 \u0644\u0627", "\uFEF6 \u0644\u0627", "\uFEF7 \u0644\u0627", "\uFEF8 \u0644\u0627",
			"\uFEF9 \u0644\u0627", "\uFEFB \u0644\u0627", "\uFEFC \u0644\u0627", };

	private static ArabicNormalizer THIS;

	public static ArabicNormalizer getInstance() {
		if (THIS == null) {
			THIS = new ArabicNormalizer();
		}
		return THIS;
	}

	private Set<Character> allArabic;
	private Set<Character> arabicDialects;
	private Map<Character, String> arabicReplace;

	public ArabicNormalizer() {
		allArabic = new HashSet<>();
		for (char c : ALL_ARABIC) {
			allArabic.add(c);
		}

		arabicDialects = new HashSet<>();
		for (char c : ALL_DIALECTS) {
			arabicDialects.add(c);
		}

		arabicReplace = new HashMap<>();
		for (String line : ARABIC_REPLACE) {
			String parts[] = line.split("\u0020");
			arabicReplace.put(parts[0].charAt(0), parts[1]);
		}
	}

	public String normalize(String text) {
		StringBuilder sb = new StringBuilder();
		String last = null;
		for (char c : text.toCharArray()) {
			if (allArabic.contains(c)) {
				if (!arabicDialects.contains(c)) {
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
