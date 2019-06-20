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

	private static final String[] ARABIC_REPLACE = { "\uFEFF ف", "\u060F ع", "\u0629 ه", "\u0622 ا", "\u0623 ا",
			"\u0625 ا", "\u0649 ي", "\uFE80 ء", "\uFE81 ا", "\uFE82 ا", "\uFE83 ا", "\uFE84 ا", "\uFE85 ؤ", "\uFE86 ؤ",
			"\uFE87 ا", "\uFE88 ا", "\uFE8B ئ", "\uFE8C ئ", "\uFE8D ا", "\uFE8E ا", "\uFE8F ب", "\uFE90 ب", "\uFE91 ب",
			"\uFE92 ب", "\uFE93 ه", "\uFE94 ه", "\uFE95 ت", "\uFE96 ت", "\uFE97 ت", "\uFE98 ت", "\uFE99 ث", "\uFE9B ث",
			"\uFE9C ث", "\uFE9D ج", "\uFE9F ج", "\uFEA0 ج", "\uFEA1 ح", "\uFEA2 ح", "\uFEA3 ح", "\uFEA4 ح", "\uFEA5 خ",
			"\uFEA6 خ", "\uFEA7 خ", "\uFEA8 خ", "\uFEA9 د", "\uFEAA د", "\uFEAB ذ", "\uFEAC ذ", "\uFEAD ر", "\uFEAE ر",
			"\uFEAF ز", "\uFEB0 ز", "\uFEB1 س", "\uFEB2 س", "\uFEB3 س", "\uFEB4 س", "\uFEB5 ش", "\uFEB6 ش", "\uFEB7 ش",
			"\uFEB8 ش", "\uFEB9 ص", "\uFEBA ص", "\uFEBB ص", "\uFEBC ص", "\uFEBD ض", "\uFEBE ض", "\uFEBF ض", "\uFEC0 ض",
			"\uFEC1 ط", "\uFEC2 ط", "\uFEC3 ط", "\uFEC4 ط", "\uFEC5 ظ", "\uFEC7 ظ", "\uFEC8 ظ", "\uFEC9 ع", "\uFECA ع",
			"\uFECB ع", "\uFECC ع", "\uFECD غ", "\uFECF غ", "\uFED0 غ", "\uFED1 ف", "\uFED2 ف", "\uFED3 ف", "\uFED4 ف",
			"\uFED5 ق", "\uFED6 ق", "\uFED7 ق", "\uFED8 ق", "\uFED9 ك", "\uFEDA ك", "\uFEDB ك", "\uFEDC ك", "\uFEDD ل",
			"\uFEDE ل", "\uFEDF ل", "\uFEE0 ل", "\uFEE1 م", "\uFEE2 م", "\uFEE3 م", "\uFEE4 م", "\uFEE5 ن", "\uFEE6 ن",
			"\uFEE7 ن", "\uFEE8 ن", "\uFEE9 ه", "\uFEEA ه", "\uFEEB ه", "\uFEEC ه", "\uFEED و", "\uFEEE و", "\uFEEF ي",
			"\uFEF0 ي", "\uFEF1 ي", "\uFEF2 ي", "\uFEF3 ي", "\uFEF4 ي", "\uFEF5 لا", "\uFEF6 لا", "\uFEF7 لا",
			"\uFEF8 لا", "\uFEF9 لا", "\uFEFB لا", "\uFEFC لا", };

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
