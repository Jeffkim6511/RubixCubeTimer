package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;
import java.util.Objects;

//Represents a list of all Trials performed
public class TrialList implements Writable {
    private final ArrayList<Trial> trials;

    //EFFECTS: Constructs a list of Trials that contain trials and times.
    public TrialList() {
        trials = new ArrayList<>();
    }


    //REQUIRES: num > 1
    //EFFECTS: Retrieves Trial at location num in array (starting with 1)
    public Trial getTrial(int num) {
        return trials.get((num - 1));
    }


    //EFFECTS: Retrieves Trial with the same string as id
    public Trial getSpecificTrial(String id) {
        Trial result = new Trial("Not Found");
        for (Trial trial : trials) {
            if (Objects.equals(id, trial.getId())) {
                result = trial;
            }
        }
        return result;
    }

    //EFFECTS: Returns the trial with the best time
    public Trial findBestTime() {
        Trial bestTrial = trials.get(0);
        for (Trial current :trials) {
            if (current.getTotalTimeInMilliSeconds() < bestTrial.getTotalTimeInMilliSeconds()) {
                bestTrial = current;
            }
        }
        return bestTrial;
    }

    //EFFECTS: Returns the trial with the worst time
    public Trial findWorstTime() {
        Trial worstTrial = trials.get(0);
        for (Trial current :trials) {
            if (current.getTotalTimeInMilliSeconds() > worstTrial.getTotalTimeInMilliSeconds()) {
                worstTrial = current;
            }
        }
        return worstTrial;
    }


    //EFFECTS: Adds a trial
    public void addTrial(Trial current) {
        trials.add(current);
    }

    //EFFECTS: returns size of a trial
    public int trialSize() {
        return trials.size();
    }



    //EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("trials", trialsToJson());
        return json;
    }

    // EFFECTS: returns trials in this TrialList as a JSON array
    private JSONArray trialsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Trial t : trials) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

