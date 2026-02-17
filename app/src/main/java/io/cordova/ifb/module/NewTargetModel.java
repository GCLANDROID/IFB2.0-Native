package io.cordova.ifb.module;

public class NewTargetModel {
    String Category;
    String Target;
    String Achievement;
    String ToBeAchievement;

    public NewTargetModel(String category, String target, String achievement, String toBeAchievement) {
        Category = category;
        Target = target;
        Achievement = achievement;
        ToBeAchievement = toBeAchievement;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getAchievement() {
        return Achievement;
    }

    public void setAchievement(String achievement) {
        Achievement = achievement;
    }

    public String getToBeAchievement() {
        return ToBeAchievement;
    }

    public void setToBeAchievement(String toBeAchievement) {
        ToBeAchievement = toBeAchievement;
    }
}
