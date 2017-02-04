package knack.college.learnenglish;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.WordFromDictionary;

import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;

public class DictionaryTrainingActivity extends AppCompatActivity {

    // Контролы
    TextView dictionaryTrainingEnglishWordTextView;
    EditText dictionaryTrainingTranslateWordEditText;
    Button checkAnswerButton;
    ImageView trueOrFalseIndicator;

    // Список слов
    List<WordFromDictionary> wordFromDictionaries = new ArrayList<>();
    // Случайный индекс для поиска слов
    int randomIndex = 0;

    Random random = new Random();
    Validator validator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_training);

        dictionaryTrainingEnglishWordTextView = (TextView)
                findViewById(R.id.dictionaryTrainingEnglishWordTextView);
        dictionaryTrainingTranslateWordEditText = (EditText)
                findViewById(R.id.dictionaryTrainingTranslateWordEditText);
        trueOrFalseIndicator = (ImageView) findViewById(R.id.trueOrFalseIndicator);

        try {
            // При загрузке Activity получаем коллекцию слов
            wordFromDictionaries = new Dictionary(getApplicationContext()).getAllWordsList();
            // Генерируем псевдослучайный индекс для коллекции слов,
            // по которому получим случайное слово
            if (wordFromDictionaries.size() > 0) {
                randomIndex = random.nextInt(wordFromDictionaries.size());
            }
            // Выводим на контрол слово
            dictionaryTrainingEnglishWordTextView.setText(
                    wordFromDictionaries.get(randomIndex).getEnglishWord()
            );
        } catch (Exception ex) {
            Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
        }

        checkAnswerButton = (Button) findViewById(R.id.checkAnswerButton);
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkAnswer();
                } catch (Exception ex) {
                    Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
                }
            }
        });
    }

    private void checkAnswer() throws Exception {
        if (validator.isTranslation(wordFromDictionaries.get(randomIndex)
                .getTranslateWord(), dictionaryTrainingTranslateWordEditText.getText()
                .toString())) {
            trueOrFalseIndicator.setBackgroundColor(
                    ContextCompat.getColor(getApplicationContext(), R.color.green)
            );
            // Очищаем поле ввода
            dictionaryTrainingTranslateWordEditText.setText("");
            // Удаляем слово из коллекции
            wordFromDictionaries.remove(randomIndex);
            // Если, коллекция не пустая - выводим нооые слово
            if (wordFromDictionaries.size() > 0) {
                randomIndex = random.nextInt(wordFromDictionaries.size());
                // Выводим на контрол слово
                dictionaryTrainingEnglishWordTextView.setText(
                        wordFromDictionaries.get(randomIndex).getEnglishWord());
            } else if (wordFromDictionaries.size() == 0) {
                dictionaryTrainingEnglishWordTextView.setText(
                        R.string.title_wordsIsEmpty
                );
                dictionaryTrainingTranslateWordEditText.setText("");
            }
        } else {
            trueOrFalseIndicator.setBackgroundColor(
                    ContextCompat.getColor(getApplicationContext(), R.color.red)
            );
        }
    }
}
