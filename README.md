# Arabic-Normalizer
Normalize any text that contains Arabic by eliminating the dialects, any other languages and normalize the Arabic characters such as (أ، ا).

## How to use?

```java

String arabic = "اللُّغَة العَرَبِيّة هي أكثر اللغات تحدثاً ونطقاً ضمن مجموعة اللغات السامية، وإحدى أكثر"
    			+ " اللغات انتشاراً في العالم، يتحدثها أكثر من 467 مليون نسمة،[4](1)";
        String normalizedArabic = ArabicNormalizer
        		.getInstance()
        		.normalize(arabic);
        System.out.println(normalizedArabic);
        
```

### Output

```
اللغه العربيه هي اكثر اللغات تحدثا ونطقا ضمن مجموعه اللغات الساميه واحدي اكثر اللغات انتشارا في العالم يتحدثها اكثر من مليون نسمه

```
