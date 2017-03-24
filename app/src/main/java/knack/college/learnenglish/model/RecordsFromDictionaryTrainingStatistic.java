package knack.college.learnenglish.model;


public class RecordsFromDictionaryTrainingStatistic {
    private int ouid;
    private String guid;
    private String wordGuid;
    private int isCorrect;
    private String type;

    public int getOuid() {
        return ouid;
    }

    public void setOuid(int ouid) {
        this.ouid = ouid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getWordGuid() {
        return wordGuid;
    }

    public void setWordGuid(String wordGuid) {
        this.wordGuid = wordGuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
}
