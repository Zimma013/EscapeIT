package pl.hackyeah.positivedevs.escapeit.Quests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pl.hackyeah.positivedevs.escapeit.MainActivity;
import pl.hackyeah.positivedevs.escapeit.R;

public class CloseQuestionQuest extends AppCompatActivity {

    private Handler customHandler = new Handler();

    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    TextView timer;

    ImageView questImage;
    TextView questTitle;
    TextView questDescription;
    EditText answer;
    Puzzle currPuzzle;
    ArrayList<Button> buttons = new ArrayList<>();

    public void check(String selected) {
        Log.d("??", selected);
        if (currPuzzle.checkAnswer(selected)) {
            Toast.makeText(this, "You are correct!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You are wrong! :(", Toast.LENGTH_LONG).show();
        }
    }

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

    public void optASelected(View v) {
        Button selected = (Button) findViewById(R.id.button_A);

        check(selected.getText().toString());
    }

    public void optBSelected(View v) {
        Button selected = (Button) findViewById(R.id.button_B);

        check(selected.getText().toString());
    }

    public void optCSelected(View v) {
        Button selected = (Button) findViewById(R.id.button_C);

        check(selected.getText().toString());
    }

    public void optDSelected(View v) {
        Button selected = (Button) findViewById(R.id.button_D);

        check(selected.getText().toString());
    }

    public void optESelected(View v) {
        Button selected = (Button) findViewById(R.id.button_E);

        check(selected.getText().toString());
    }

    public void optFSelected(View v) {
        Button selected = (Button) findViewById(R.id.button_F);

        check(selected.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_question_quest);

        questImage = (ImageView) findViewById(R.id.quest_img);
        questTitle = (TextView) findViewById(R.id.quest_title);
        questDescription = (TextView) findViewById(R.id.quest_descripton);
        answer = (EditText) findViewById(R.id.answer);
        timer = (TextView) findViewById(R.id.close_timer_text);

        Button tmp = (Button) findViewById(R.id.button_A);
        buttons.add(tmp);
        buttons.add((Button) findViewById(R.id.button_B));
        buttons.add((Button) findViewById(R.id.button_C));
        buttons.add((Button) findViewById(R.id.button_D));
        buttons.add((Button) findViewById(R.id.button_E));
        buttons.add((Button) findViewById(R.id.button_F));

        try {
            currPuzzle = new Puzzle("test.json", this);

            //questImage.setImageBitmap(currPuzzle.getImgPath());
            questTitle.setText(currPuzzle.getTitle());
            questDescription.setText(currPuzzle.getDescription());

            ArrayList<PossibleAnswer> answers = currPuzzle.getPossibleAnswers();

            for (int i = 0; i < answers.size(); i++) {
                buttons.get(i).setVisibility(View.VISIBLE);
                buttons.get(i).setText(answers.get(i).getAnswer());
                Log.i("??", Integer.toString(buttons.get(i).getVisibility()));
                Log.i("???", answers.get(i).getAnswer());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                timesUp();
            }
        }, 10000);

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timer.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

}