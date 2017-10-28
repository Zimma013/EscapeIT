package pl.hackyeah.positivedevs.escapeit.Quests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

import pl.hackyeah.positivedevs.escapeit.R;

public class CloseQuestionQuest extends AppCompatActivity {

    ImageView questImage;
    TextView questTitle;
    TextView questDescription;
    EditText answer;
    Puzzle currPuzzle;
    LinearLayout buttons;

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
        setContentView(R.layout.activity_open_question_quest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        questImage = (ImageView) findViewById(R.id.quest_img);
        questTitle = (TextView) findViewById(R.id.quest_title);
        questDescription = (TextView) findViewById(R.id.quest_descripton);
        answer = (EditText) findViewById(R.id.answer);
        buttons = (LinearLayout) findViewById(R.id.buttons_list);

        try {
            currPuzzle = new Puzzle("test.json", this);

            //questImage.setImageBitmap(currPuzzle.getImgPath());
            questTitle.setText(currPuzzle.getTitle());
            questDescription.setText(currPuzzle.getDescription());

            LinkedList<String> answers = new LinkedList<>();

            answers.add("A");
            answers.add("B");

            for (int i = 0; i < answers.size(); i++) {
                
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}