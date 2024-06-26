package io.cordova.ifb.module;

public class FeedBackModel {
    String question,answer,Q_OptionId;
    private boolean isSelected = false;

    public FeedBackModel(String question, String answer,String Q_OptionId) {
        this.question = question;
        this.answer = answer;
        this.Q_OptionId=Q_OptionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getQ_OptionId() {
        return Q_OptionId;
    }

    public void setQ_OptionId(String q_OptionId) {
        Q_OptionId = q_OptionId;
    }
}
