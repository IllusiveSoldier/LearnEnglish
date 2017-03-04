package knack.college.learnenglish;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Nullable;

import knack.college.learnenglish.model.toasts.Toast;

public class BackupActivity
        extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener {

    private Toast toast;
    private static final int REQUEST_CODE_CREATOR = 2;
    private static final int REQUEST_CODE_RESOLUTION = 3;
    private static final String CURRENT_DATABASE_PATH =
            "//data/knack.college.learnenglish/databases/learn_english.db";

    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toast = new Toast(this);
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        googleApiClient.connect();
    }

    private void saveFileToDrive() {
        final File db = getDatabaseFile();
        Drive.DriveApi.newDriveContents(googleApiClient)
                .setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
                    @Override
                    public void onResult(DriveApi.DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            return;
                        }
                        OutputStream outputStream = result.getDriveContents().getOutputStream();
                        try {
                            outputStream.write(Files.toByteArray(db));
                        } catch (IOException e1) {
                            toast.show(e1);
                        }
                        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                                .setMimeType("application/x-sqlite3")
                                .setTitle(
                                        "LearnEnglishDatabase"
                                                + new SimpleDateFormat("_yyyy_MM_dd_hh_mm_")
                                                .format(Calendar.getInstance().getTime()) + ".db"
                                ).build();
                        IntentSender intentSender = Drive.DriveApi
                                .newCreateFileActivityBuilder()
                                .setInitialMetadata(metadataChangeSet)
                                .setInitialDriveContents(result.getDriveContents())
                                .build(googleApiClient);
                        try {
                            startIntentSenderForResult(
                                    intentSender,
                                    REQUEST_CODE_CREATOR,
                                    null,
                                    0,
                                    0,
                                    0
                            );
                        } catch (IntentSender.SendIntentException e) {
                            toast.show(e);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CREATOR:
                if (resultCode == RESULT_OK) {
                    toast.show("Файл успешно загружен");
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }

        super.onPause();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, result.getErrorCode(), 0).show();
            return;
        }
        try {
            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
        } catch (IntentSender.SendIntentException e) {
            toast.show(e);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        saveFileToDrive();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        toast.show("Подключение успешно!");
    }

    private File getDatabaseFile() {
        File databaseFile = null;

        try {
            File data = Environment.getDataDirectory();
            databaseFile = new File(data, CURRENT_DATABASE_PATH);
        } catch (Exception ex) {
            toast.show(ex);
        }

        return databaseFile;
    }
}
