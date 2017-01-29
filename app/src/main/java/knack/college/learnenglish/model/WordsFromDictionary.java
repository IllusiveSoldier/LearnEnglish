package knack.college.learnenglish.model;

/** Класс, описывающий слова со словаря */
public class WordsFromDictionary {

    public WordsFromDictionary() {}


    private String englishWord;
    private String translateWord;

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }
}
