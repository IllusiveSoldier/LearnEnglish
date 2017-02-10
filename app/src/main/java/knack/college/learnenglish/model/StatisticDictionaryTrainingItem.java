package knack.college.learnenglish.model;

public class StatisticDictionaryTrainingItem {
    private String ouid;
    private String guid;
    private String countWordsInDictionary;
    private String countCorrectAnswer;
    private String countWrongAnswer;

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCountWordsInDictionary() {
        return countWordsInDictionary;
    }

    public void setCountWordsInDictionary(String countWordsInDictionary) {
        this.countWordsInDictionary = countWordsInDictionary;
    }

    public String getCountCorrectAnswer() {
        return countCorrectAnswer;
    }

    public void setCountCorrectAnswer(String countCorrectAnswer) {
        this.countCorrectAnswer = countCorrectAnswer;
    }

    public String getCountWrongAnswer() {
        return countWrongAnswer;
    }

    public void setCountWrongAnswer(String countWrongAnswer) {
        this.countWrongAnswer = countWrongAnswer;
    }
}
