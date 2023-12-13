package models;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Plan {
    // this class is used to store the details of a business plan
    // each plan has a name, a description, and a list of sections
    private final String username;
    private String name;
    private final int id;
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

    private String[] answers = new String[7];
    private String[] sectionsContent = new String[7];

    public Plan(String username, String name, int id) {
        this.username = username;
        this.name = name;
        this.id = id;
    }

    public Plan(String username, String name, int id, String[] answers) {
        this.username = username;
        this.name = name;
        this.id = id;
        this.answers = answers;
    }

    public Plan(String username, String name, int id, String[] answers, String[] sectionsContent) {
        this.username = username;
        this.name = name;
        this.id = id;
        this.answers = answers;
        this.sectionsContent = sectionsContent;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String[] getSections() {
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

    public int getId() {
        return id;
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
}
