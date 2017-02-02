package knack.college.learnenglish.model;

/** Класс, описывающий слова со словаря */
public class WordFromDictionary {

    private String ouid;
    private String guid;
    private String englishWord;
    private String translateWord;

    public String getEnglishWord() {
        return englishWord;
    }

    public String getOuid() {
        return ouid;
    }

    void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public String getGuid() {
        return guid;
    }

    void setGuid(String guid) {
        this.guid = guid;
    }

    void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    void setTranslateWord(String translateWord) {
        this.translateWord = translateWord;
    }
}
