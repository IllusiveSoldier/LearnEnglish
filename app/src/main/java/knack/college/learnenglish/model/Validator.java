package knack.college.learnenglish.model;

import static knack.college.learnenglish.model.Constant.Number.MAX_WORD_LENGTH;

public class Validator {

    public Validator() {}

    /** Метод, который проверяет, что слово не больше 255 символов */
    public boolean isWordMoreMaxSymbols(String word) {

        boolean isValidate = false;

        if (word != null) {
            if (!word.isEmpty()) {
                isValidate = word.length() > MAX_WORD_LENGTH;
            }
        }


        return isValidate;
    }


    /** Метод, который проверяет, что в слове только латинские буквы */
    public boolean isEnglishCharactersInWord(String word) {

        boolean isValidate = false;

        if (word != null) {
            if (!word.isEmpty()) {
                isValidate = word.matches("^[a-zA-Z]+$");
            }
        }


        return isValidate;
    }


    /** Метод, который проверяет, что в слове только русские символы */
    public boolean isRussianCharactersInWord(String word) {

        boolean isValidate = false;

        if (word != null)  {
            if (!word.isEmpty()) {
                isValidate = word.matches("^[а-яА-Я]+$");
            }
        }


        return isValidate;
    }
}
