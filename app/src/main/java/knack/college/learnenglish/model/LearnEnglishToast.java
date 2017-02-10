package knack.college.learnenglish.model;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import knack.college.learnenglish.R;

/** Класс, который реализует отображение кастомного Toast'а */
public class LearnEnglishToast {
    /** Изображение, которое будет отображаться при показе Toast'а */
    private int image;
    /** Текстовое поле, которое будет отображать информацию о произошедшем событии */
    private String message;
    /** Активити */
    private Activity activity;
    /** ImageView, которое будет содержать изображение */
    private ImageView imageView;
    /** TextView, которое будет содержать сообщение */
    private TextView textView;
    /** View */
    private View view;
    /** Toast */
    private Toast toast;

    // Конструкторы
    public LearnEnglishToast(Activity act, String mes, int img) {
        activity = act;
        message = mes;
        image = img;

        initializeViewAndToast();
    }

    public LearnEnglishToast(Activity act) {
        activity = act;

        initializeViewAndToast();
    }

    /** Метод, который показывает Toast */
    public void show() {
        imageView.setImageResource(image);
        textView.setText(message);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /** Метод, который показывает Toast */
    public void show(String mes, int img) {
        imageView.setImageResource(img);
        textView.setText(mes);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /** Метод, который показывает Toast */
    public void show(String mes, int img, int duration) {
        imageView.setImageResource(img);
        textView.setText(mes);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    /** Метод, который показывает Toast */
    public void show(String message) {
        textView.setText(message);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /** Метод, который показывает Toast */
    public void show(Throwable ex) {
        imageView.setImageResource(R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
        textView.setText(ex.getMessage());

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    /** Метод, который иницилизирует LayoutInflater, View, ImageView, TextView, Toast
     * в конструкторе после определения Activity, изображения и сообщения */
    private void initializeViewAndToast() {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) activity.findViewById(R.id.customToastContainer));

        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.message);
        toast = new Toast(activity.getApplicationContext());
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
