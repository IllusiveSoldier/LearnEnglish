package knack.college.learnenglish.model.translate;

import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static knack.college.learnenglish.model.Constant.Translator.BASE_TRANSLATE_URL;
import static knack.college.learnenglish.model.Constant.Translator.EN_RU;
import static knack.college.learnenglish.model.Constant.Translator.LANG;
import static knack.college.learnenglish.model.Constant.Translator.RU_EN;
import static knack.college.learnenglish.model.Constant.Translator.TEXT;
import static knack.college.learnenglish.model.translate.ApiKey.API_KEY;

/** Класс, который реализует перевод слов с помозью сервиса Яндекс Переводчик */
public class Translator {
    private OkHttpClient client = new OkHttpClient();

    public String translateEng(String englishWord) throws Exception {
        String response = null;
        if (englishWord != null && !englishWord.isEmpty()) {
            String requestUrl = BASE_TRANSLATE_URL +
                    API_KEY +
                    LANG +
                    EN_RU +
                    TEXT +
                    URLEncoder.encode(englishWord, "UTF-8");
            response = run(requestUrl);
        }

        return response;
    }

    public String translateRus(String russianWord) throws Exception {
        String response = null;
        if (russianWord != null && !russianWord.isEmpty()) {
            String requestUrl = BASE_TRANSLATE_URL +
                    API_KEY +
                    LANG +
                    RU_EN +
                    TEXT +
                    URLEncoder.encode(russianWord, "UTF-8");
            response = run(requestUrl);
        }

        return response;
    }

    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
