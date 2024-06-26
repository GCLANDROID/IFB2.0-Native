package io.cordova.ifb.module;

public class QusetionModel {
    String questionId,question,hints;
    private boolean isSelected = false;

    public QusetionModel(String questionId, String question, String hints) {
        this.questionId = questionId;
        this.question = question;
        this.hints = hints;
    }


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
