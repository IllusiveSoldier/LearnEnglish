package knack.college.learnenglish.model.toasts;

import android.content.Context;
import android.widget.Toast;

/** Wrapper toast'а */
public class ToastWrapper {
    private Context context;

    // Constructors

    public ToastWrapper(Context context) {
        this.context = context;
    }

    public void show(String message) {
        Toast.makeText(
                context,
                message != null
                ?
                message
                :
                "",
                Toast.LENGTH_LONG
        ).show();
    }

    public void show(Throwable throwable) {
        final String exceptionMessage = throwable.getMessage();
        Toast.makeText(
                context,
                exceptionMessage != null
                ?
                exceptionMessage
                :
                "",
                Toast.LENGTH_LONG
        ).show();
    }
}
