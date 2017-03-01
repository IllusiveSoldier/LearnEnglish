package knack.college.learnenglish.model.translate;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;

import static knack.college.learnenglish.model.Constant.Translator.*;
import static knack.college.learnenglish.model.translate.ApiKey.API_KEY;

/** Класс, который реализует перевод слов с помозью сервиса Яндекс Переводчик */
public class Translator {
    private static final String BASE_TRANSLATE_URL =
            "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    private static final String LANG = "&lang=";
    private static final String TEXT = "&text=";

    private OkHttpClient client = new OkHttpClient();

    /** Перевод английского слова */
    public String translateEng(String englishWord) throws Exception {
        String response = null;
        if (englishWord != null && !englishWord.isEmpty()) {
            String requestUrl = BASE_TRANSLATE_URL
                    + API_KEY
                    + LANG
                    + EN_RU
                    + TEXT
                    + URLEncoder.encode(englishWord, "UTF-8");
            response = run(requestUrl);
        }

        return response;
    }

    /** Перевод русского слова */
    public String translateRus(String russianWord) throws Exception {
        String response = null;
        if (russianWord != null && !russianWord.isEmpty()) {
            String requestUrl = BASE_TRANSLATE_URL
                    + API_KEY
                    + LANG
                    + RU_EN
                    + TEXT
                    + URLEncoder.encode(russianWord, "UTF-8");
            response = run(requestUrl);
        }

        return response;
    }

    /** Запрос к серву */
    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
