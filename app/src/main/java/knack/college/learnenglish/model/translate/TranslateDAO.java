package knack.college.learnenglish.model.translate;

import java.util.ArrayList;

public class TranslateDAO {
    private String code;
    private String lang;
    private ArrayList<String> text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}
