package models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanFileHandler extends FileHandler<Plan>{
    private static final String FILE_PATH = "src/main/java/models/plans.json";
    public PlanFileHandler() {
        super(FILE_PATH);
        Plan.setLastID(getObjects().size());
    }

    @Override
    protected String convertObjectToJson(Plan plan) {
        return String.format(
                "{\"username\":\"%s\"," +
                        "\"name\":\"%s\"," +
                        "\"id\":\"%s\"," +
                        "\"answers\":%s," +
                        "\"sectionsContent\":%s}",
                plan.getUsername(), plan.getName(), plan.getId(),
                plan.getAnswersString(), plan.getSectionsContentString());
    }

    @Override
    protected Plan convertJsonToObject(String json) {
        // parse json with regex
        // {"username":"salma","name":"plan1","id":"1","answers":["answer 1","answer 2","answer 3","answer 4","answer 5","answer 6","answer 7"],"sectionsContent":["section 1","section 2","section 3","section 4","section 5","section 6","section 7"]}
        // Define regex patterns for extracting values
        Pattern usernamePattern = Pattern.compile("\"username\":\"([^\"]*)\"");
        Pattern namePattern = Pattern.compile("\"name\":\"([^\"]*)\"");
        Pattern idPattern = Pattern.compile("\"id\":\"([^\"]*)\"");
        Pattern answersPattern = Pattern.compile("\"answers\":\\[([^\\]]*)\\]");
        Pattern sectionsContentPattern = Pattern.compile("\"sectionsContent\":\\[([^\\]]*)\\]");

        // Match patterns against the JSON string
        Matcher usernameMatcher = usernamePattern.matcher(json);
        Matcher nameMatcher = namePattern.matcher(json);
        Matcher idMatcher = idPattern.matcher(json);
        Matcher answersMatcher = answersPattern.matcher(json);
        Matcher sectionsContentMatcher = sectionsContentPattern.matcher(json);

        String username = "";
        if (usernameMatcher.find()) {
            username = usernameMatcher.group(1);
        }

        String name = "";
        if (nameMatcher.find()) {
            name = nameMatcher.group(1);
        }

        int id = 0;
        if (idMatcher.find()) {
            id = Integer.parseInt(idMatcher.group(1));
        }

        String[] answers = new String[7];
        if (answersMatcher.find()) {
            String answersString = answersMatcher.group(1);
            String[] answersArray = answersString.split("\",\"");
            // remove quotes from each answer
            for (int i = 0; i < answersArray.length; i++) {
                answers[i] = answersArray[i].replace("\"", "");
            }
        }

        String[] sectionsContent = new String[7];
        if (sectionsContentMatcher.find()) {
            String sectionsContentString = sectionsContentMatcher.group(1);
            String[] sectionsContentArray = sectionsContentString.split("\",\"");
            // remove quotes from each section content
            for (int i = 0; i < sectionsContentArray.length; i++) {
                sectionsContent[i] = sectionsContentArray[i].replace("\"", "");
            }
        }

        return new Plan(id, username, name, answers, sectionsContent);
    }

}
