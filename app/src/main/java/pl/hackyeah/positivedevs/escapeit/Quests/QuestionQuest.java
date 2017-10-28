package pl.hackyeah.positivedevs.escapeit.Quests;

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

import pl.hackyeah.positivedevs.escapeit.R;

public class QuestionQuest extends AppCompatActivity {

    ImageView questImage;
    TextView questTitle;
    TextView questDescription;
    EditText answer;
    Button submitButton;

    public void submitAnswer(View v) {
        String myAnswer = answer.getText().toString();


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
        submitButton = (Button) findViewById(R.id.button);


        questImage.setImageBitmap();
        questTitle.setText();
        questDescription.setText();


    }

}
