package pl.hackyeah.positivedevs.escapeit.Quests;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Krzysiek on 2017-10-28.
 */

public class Puzzle {
    private int id;
    private String title;
    private String description;
    private String imgPath;
    private String correctAnswer;


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

    public Puzzle(String jsonFile, Context mContext) throws JSONException {
        String json = loadJSONFromAsset(jsonFile, mContext);
        JSONObject reader = new JSONObject(json);
        JSONObject main = reader.getJSONObject("main");
        id = main.getInt("id");
        title = main.getString("title");
        description = main.getString("description");
        imgPath = main.getString("imgPath");
        correctAnswer = main.getString("correctAnswer");
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

    boolean checkAnswer(String answer) {
        if (answer.equals(correctAnswer))
            return true;
        else
            return false;
    }
}
