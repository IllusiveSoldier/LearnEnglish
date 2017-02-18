package knack.college.learnenglish.model;

import static knack.college.learnenglish.model.Constant.Number.MAX_WORD_LENGTH;

public class Validator {

    /** Метод, который проверяет, что слово не больше 255 символов */
    public boolean isWordMoreMaxSymbols(String word) {
        return word != null && !word.isEmpty() && word.length() > MAX_WORD_LENGTH;
    }

    /** Метод, который проверяет, что в слове только латинские буквы */
    public boolean isEnglishCharactersInWord(String word) {
        return word != null && !word.isEmpty() && word.matches("^[a-zA-Z ']+$");
    }

    /** Метод, который проверяет, что в слове только русские символы */
    public boolean isRussianCharactersInWord(String word) {
        return word != null && !word.isEmpty() && word.matches("^[а-яА-Я ]+$");
    }

    /** Метод, который сравнивает, что перевод английского слова правильный */
    public boolean isTranslation(String correctTranslationWord, String userTranslationWord) {
        return correctTranslationWord != null && userTranslationWord != null
                && !correctTranslationWord.isEmpty() && !userTranslationWord.isEmpty()
                && correctTranslationWord.equalsIgnoreCase(userTranslationWord);
    }
}
