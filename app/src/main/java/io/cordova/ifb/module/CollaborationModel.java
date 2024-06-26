package io.cordova.ifb.module;

public class CollaborationModel {
    String interactionDate,entryDate,question,answer;

    public CollaborationModel(String interactionDate, String entryDate, String question, String answer) {
        this.interactionDate = interactionDate;
        this.entryDate = entryDate;
        this.question = question;
        this.answer = answer;
    }

    public String getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(String interactionDate) {
        this.interactionDate = interactionDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
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
}
