package pl.hackyeah.positivedevs.escapeit.Quests;

/**
 * Created by Krzysiek on 2017-10-28.
 */

public class PossibleAnswer {
    private String name;
    private String answer;

    public String getName() {
        return name;
    }

    public String getAnswer() {
        return answer;
    }

    public PossibleAnswer(String name, String answer) {
        this.name = name;
        this.answer = answer;
    }
}
