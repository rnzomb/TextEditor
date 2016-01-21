package zzzomb.texteditor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextActivity extends Activity {

    private TextView tv;
    private static final String FILENAME = "text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        tv = (TextView) findViewById(R.id.showTextView);
        initText();
    }

    private void initText() {
        String text = this.readFromFile(FILENAME);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(text);
    }

    public void goToEditScreen(View v) {
        Intent i = new Intent(this, EditActivity.class);
        startActivity(i);
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
