package pl.hackyeah.positivedevs.escapeit.Quests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import pl.hackyeah.positivedevs.escapeit.R;

public class OpenQuestionQuest extends AppCompatActivity {

    ImageView questImage;
    TextView questTitle;
    TextView questDescription;
    EditText answer;
    Button submitButton;
    Puzzle currPuzzle;

    public void timesUp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Skończył Ci się czas :(");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

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
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        questImage = (ImageView) findViewById(R.id.quest_img);
        questTitle = (TextView) findViewById(R.id.quest_title);
        questDescription = (TextView) findViewById(R.id.quest_descripton);
        answer = (EditText) findViewById(R.id.answer);
        submitButton = (Button) findViewById(R.id.submitButton);

        try {
            currPuzzle = new Puzzle("test.json", this);

            if (currPuzzle.getAnswerType() == AnswerType.NUMBERS) {
                answer.setInputType(InputType.TYPE_CLASS_NUMBER);
            }

            //questImage.setImageBitmap(currPuzzle.getImgPath());
            questTitle.setText(currPuzzle.getTitle());
            questDescription.setText(currPuzzle.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                timesUp();
            }
        }, 10000);
    }

}
