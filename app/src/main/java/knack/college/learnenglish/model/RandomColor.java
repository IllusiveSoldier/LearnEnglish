package knack.college.learnenglish.model;

import java.util.ArrayList;
import java.util.Random;

import static knack.college.learnenglish.model.RandomColor.Colors.*;

/** Класс, который реализует методы по возвращению значения с псевдослучайным цветом */
public class RandomColor {
    /** Список цветов */
    private ArrayList<String> colors = new ArrayList<>();
    private Random randomColorItemId = new Random();

    // Конструкторы
    public RandomColor(ArrayList<String> colorsList) {
        colors = colorsList;
    }

    public RandomColor() {
        initializeColorList();
    }

    /** Метод, который иницилизирует коллекцию с цветом дефлотными значениями */
    private void initializeColorList() {
        colors.add(GREEN);
        colors.add(CYAN);
        colors.add(BRIGHT_CYAN);
        colors.add(BRIGHT_PURPLE);
        colors.add(BRIGHT_YELLOW);
        colors.add(BRIGHT_RED);
        colors.add(BRIGHT_PINK);
    }

    /** Метод, который возвращает из коллекции с цветами псевдослучаный цвет */
    public String getRandomColor() {
        return colors != null && colors.size() > 0
                ?
                colors.get(randomColorItemId.nextInt(colors.size() - 1))
                :
                BRIGHT_CYAN;
    }

    /** Класс-storage цветов */
    final class Colors {
        final static String GREEN = "#00e676";
        final static String CYAN = "#00B8D4";
        final static String BRIGHT_CYAN = "#18FFFF";
        final static String BRIGHT_PURPLE = "#D500F9";
        final static String BRIGHT_YELLOW = "#FFFF00";
        final static String BRIGHT_RED = "#FF1744";
        final static String BRIGHT_PINK = "#F50057";
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
}
