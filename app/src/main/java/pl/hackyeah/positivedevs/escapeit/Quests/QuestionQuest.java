package pl.hackyeah.positivedevs.escapeit.Quests;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import pl.hackyeah.positivedevs.escapeit.R;

public class QuestionQuest extends AppCompatActivity {

    ImageView questImage;
    TextView questTitle;
    TextView questDescription;
    EditText answer;
    Button submitButton;
    Puzzle currPuzzle;

    public void submitAnswer(View v) {
        String myAnswer = answer.getText().toString();

        if (currPuzzle.checkAnswer(myAnswer)) {
            Toast.makeText(this, "You are correct", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You are wrong", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questImage = (ImageView) findViewById(R.id.quest_img);
        questTitle = (TextView) findViewById(R.id.quest_title);
        questDescription = (TextView) findViewById(R.id.quest_descripton);
        answer = (EditText) findViewById(R.id.answer);
        submitButton = (Button) findViewById(R.id.submitButton);

        try {
            currPuzzle = new Puzzle("test.json", this);

            //questImage.setImageBitmap(currPuzzle.getImgPath());
            questTitle.setText(currPuzzle.getTitle());
            questDescription.setText(currPuzzle.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
