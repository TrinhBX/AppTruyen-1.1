package com.example.apptruyen.utils;

public enum URLManager {
    GET_CHAPTER_LIST_URL("https://mis58pm.000webhostapp.com/GetChapterList.php"),
    GET_STORY_LIST_URL("https://mis58pm.000webhostapp.com/GetListStory.php"),
    GET_CHAPTER_CONTENT("https://mis58pm.000webhostapp.com/GetChapterContent.php"),
    GET_STORY_DETAIL("https://mis58pm.000webhostapp.com/GetStoryDetail.php");

    private String url;

    private URLManager(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
