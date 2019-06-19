package mr.smady.arabic.normalizer;


public class App 
{
    public static void main( String[] args )
    {
    	String arabic = "اللُّغَة العَرَبِيّة هي أكثر اللغات تحدثاً ونطقاً ضمن مجموعة اللغات السامية، وإحدى أكثر اللغات انتشاراً في العالم، يتحدثها أكثر من 467 مليون نسمة،[4](1)";
        String normalizedArabic = ArabicNormalizer.getInstance().normalize(arabic);
        System.out.println(normalizedArabic);
    }
}
