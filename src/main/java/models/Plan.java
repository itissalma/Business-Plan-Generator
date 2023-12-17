//Shady Nessim 900191322 Salma Aly 900203182
package models;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Plan implements identifiable {
    // this class is used to store the details of a business plan
    private final String username;
    private String name;
    private int id;
    private static int lastID = 0;
    private static final String[] sections = {
            "Executive Summary",
            "Company Description",
            "Products and Services",
            "Market Analysis",
            "Strategy and Implementation",
            "Organization and Management Team",
            "Financial Plan"
    };
    private static final String[] questions = {
            "What is the name of your company?",
            "Give me an overview of your business?",
            "What country is it located in?",
            "What products or services do you offer?",
            "Who are you targeting?",
            "How many employees do you have?",
            "What are your goals for the business?"
    };

    private String[] answers;
    private String[] sectionsContent = new String[7];

    public Plan(String username, String name, String[] answers) {
        this.username = username;
        this.name = name;
        this.answers = answers;
    }

    public Plan(String username, String name, String[] answers, String[] sectionsContent) {
        this.username = username;
        this.name = name;
        this.answers = answers;
        this.sectionsContent = sectionsContent;
    }

    public Plan(int id, String username, String name, String[] answers, String[] sectionsContent) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.answers = answers;
        this.sectionsContent = sectionsContent;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public static String[] getSections() {
        return sections;
    }

    public String[] getQuestions() {
        return questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String[] getSectionsContent() {
        return sectionsContent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setSectionsContent(String[] sectionsContent) {
        this.sectionsContent = sectionsContent;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id='" + id + '\'' +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", answers='" + Arrays.toString(answers) + '\'' +
                ", sectionsContent='" + Arrays.toString(sectionsContent) + '\'' +
                '}';
    }

    public String getSectionContent(String section) {
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].equals(section)) {
                return sectionsContent[i];
            }
        }
        return "";
    }

    public void setSectionContent(String section, String content) {
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].equals(section)) {
                sectionsContent[i] = content;
            }
        }
    }

    public void setSectionContent(Map<String, String> sectionContent) {
        for (int i = 0; i < sections.length; i++) {
            if (sectionContent.containsKey(sections[i])) {
                sectionsContent[i] = sectionContent.get(sections[i]);
            }
        }
    }

    public void setSectionContent(List<String> sectionContent) {
        for (int i = 0; i < sections.length; i++) {
            if (i < sectionContent.size()) {
                sectionsContent[i] = sectionContent.get(i);
            }
        }
    }

    public void setSectionContent(String[] sectionContent) {
        for (int i = 0; i < sections.length; i++) {
            if (i < sectionContent.length) {
                sectionsContent[i] = sectionContent[i];
            }
        }
    }

    public String getAnswer(String question) {
        for (int i = 0; i < questions.length; i++) {
            if (questions[i].equals(question)) {
                return answers[i];
            }
        }
        return "";
    }

    public String getAnswersString() {
        // json array
        StringBuilder answersString = new StringBuilder("[");
        for (int i = 0; i < answers.length; i++) {
            answersString.append("\"").append(answers[i]).append("\"");
            if (i < answers.length - 1) {
                answersString.append(",");
            }
        }
        answersString.append("]");
        return answersString.toString();
    }

    public String getSectionsContentString() {
        // json array
        StringBuilder sectionsContentString = new StringBuilder("[");
        for (int i = 0; i < sectionsContent.length; i++) {
            sectionsContentString.append("\"").append(sectionsContent[i]).append("\"");
            if (i < sectionsContent.length - 1) {
                sectionsContentString.append(",");
            }
        }
        sectionsContentString.append("]");
        return sectionsContentString.toString();
    }

    public String getSectionsJSON() {
        // json object with sections as keys and content as values
        StringBuilder sectionsString = new StringBuilder("{");
        for (int i = 0; i < sections.length; i++) {
            sectionsString.append("\"").append(sections[i]).append("\":\"").append(sectionsContent[i]).append("\"");
            if (i < sections.length - 1) {
                sectionsString.append(",");
            }
        }
        sectionsString.append("}");

        return sectionsString.toString();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void incrementLastId() {
        lastID++;
    }

    @Override
    public int getLastId() {
        return lastID;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public static String getSectionsString() {
        // section title 1, section title 2, section title 3, ...
        StringBuilder sectionsString = new StringBuilder();
        for (int i = 0; i < sections.length; i++) {
            sectionsString.append(sections[i]);
            if (i < sections.length - 1) {
                sectionsString.append(", ");
            }
        }
        return sectionsString.toString();
    }

    public String getQuestionsAndAnswersString() {
        // question 1 answer 1 question 2 answer 2 ...
        StringBuilder questionsAndAnswersString = new StringBuilder();
        for (int i = 0; i < questions.length; i++) {
            questionsAndAnswersString.append(questions[i]).append(" ").append(answers[i]);
            if (i < questions.length - 1) {
                questionsAndAnswersString.append(" ");
            }
        }
        return questionsAndAnswersString.toString();
    }

    public static void setLastID(int lastID) {
        Plan.lastID = lastID;
    }
}
