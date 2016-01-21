package zzzomb.texteditor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class EditActivity extends Activity {

    private TextView editText;
    private static final String FILENAME = "text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = (TextView) findViewById(R.id.viewEditText);
        // MAKE THIS TEXVIEW EDITABLE VIEW
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.requestFocus();
        insertText();
    }


    private void insertText() {
        String text = this.readFromFile(FILENAME);
        editText.setMovementMethod(new ScrollingMovementMethod());
        editText.setText(text);
    }

    public void saveAndExit(View v) {
        String data = editText.getText().toString();
        writeToFile(data, EditActivity.FILENAME);

        Intent i = new Intent(this, TextActivity.class);
        startActivity(i);
    }

    private void writeToFile(String data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new
                    OutputStreamWriter(openFileOutput(fileName, Context.MODE_PRIVATE));

            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
        }
    }

    private String readFromFile(String fileName) {
        String fileContent = "";
        try {
            InputStream inputStream = openFileInput(fileName);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                inputStream.close();
                fileContent = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e(null, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(null, "Can not read file: " + e.toString());
        }
        return fileContent;
    }
}
