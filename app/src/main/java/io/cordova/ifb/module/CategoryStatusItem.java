package io.cordova.ifb.module;

public class CategoryStatusItem {



        private String categoryId;
        private String categoryName;
        private int isCompleted;

        public CategoryStatusItem(String categoryId, String categoryName, int isCompleted) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.isCompleted = isCompleted;
        }

        public String getCategoryId() { return categoryId; }
        public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

        public int getIsCompleted() { return isCompleted; }
        public void setIsCompleted(int isCompleted) { this.isCompleted = isCompleted; }

        public boolean isCompleted() { return isCompleted == 1; }
    }

