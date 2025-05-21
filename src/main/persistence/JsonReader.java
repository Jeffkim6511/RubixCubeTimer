package persistence;

import model.Trial;
import model.TrialList;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// A reader that reads TrialList from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TrialList from file and returns it;
    //          throws IOException if an error occurs reading data from file
    public TrialList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrialList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TrialList from JSON object and returns it
    private TrialList parseTrialList(JSONObject jsonObject) {
        TrialList tl = new TrialList();
        addTrials(tl, jsonObject);
        return tl;
    }

    // MODIFIES: tl
    // EFFECTS: parses trials from JSON object and adds them to TrialList
    private void addTrials(TrialList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("trials");
        for (Object json : jsonArray) {
            JSONObject nextTrial = (JSONObject) json;
            addTrial(tl, nextTrial);
        }
    }

    // MODIFIES: tl
    // EFFECTS: parses one trial from JSON object and adds it to trials
    private void addTrial(TrialList tl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String ml = jsonObject.getString("Mixing Formula");
        long time = jsonObject.getLong("time");

        Trial trial = new Trial(name);
        trial.setTimer(time);
        trial.setMixingFormula(ml);
        tl.addTrial(trial);
    }
}

