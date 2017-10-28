package pl.hackyeah.positivedevs.escapeit.Quests;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Krzysiek on 2017-10-28.
 */

public class Puzzle {
    private int id;
    private String title;
    private String description;
    private String imgPath;
    private String correctAnswer;
    private AnswerType answerType;
    private int numberOfAttempts;
    private ArrayList<PossibleAnswer> possibleAnswers;

    private boolean answered = false;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public AnswerType getAnswerType() { return answerType; }

    public int getNumberOfAttempts() { return numberOfAttempts; }

    public ArrayList<PossibleAnswer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public boolean isAnswered(){
        return answered;
    }

    public Puzzle(String jsonFile, Context mContext) throws JSONException {
        String json = loadJSONFromAsset(jsonFile, mContext);
        JSONObject reader = new JSONObject(json);
        JSONObject main = reader.getJSONObject("main");
        id = main.getInt("id");
        title = main.getString("title");
        description = main.getString("description");
        imgPath = main.getString("imgPath");
        correctAnswer = main.getString("correctAnswer");
        answerType = AnswerType.valueOf(main.getString("answerType"));
        if(answerType == AnswerType.ABCD){
            possibleAnswers = new ArrayList<PossibleAnswer>();
            JSONArray answers = main.getJSONArray("possibleAnwers");
            for(int i = 0;i<answers.length();i++){
                JSONObject answer = answers.getJSONObject(i);
                possibleAnswers.add(new PossibleAnswer(answer.getString("name"),answer.getString("answer")));
            }
        }
        numberOfAttempts = main.getInt("numberOfAttempts");

    }

    public String loadJSONFromAsset(String jsonFile, Context mContext) {
        String json = null;
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream is = assetManager.open(jsonFile);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public boolean checkAnswer(String answer) {
        if (answer.equals(correctAnswer)) {
            answered = true;
            return true;
        }
        else
            return false;
    }

}
