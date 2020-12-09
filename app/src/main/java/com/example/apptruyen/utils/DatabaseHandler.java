package com.example.apptruyen.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.apptruyen.entities.Story;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StoryManager.db";
    private static final int DATABASE_VERSION = 1;

    //Table story in Book case
    private static final String TABLE_NAME = "stories";
    private static final String ID_STORY = "id";
    private static final String STORY_NAME= "name";
    private static final String AUTHOR = "author";
    private static final String STATUS = "status";
    private static final String TYPES = "types";
    private static final String NUMBER_OF_CHAPTER = "numberOfChapter";
    private static final String AVATAR = "avatar";
    private static final String REVIEW = "review";
    private static final String CHAPTER_READING = "chapterReading";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_stories_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT,%s INTEGER)",
                TABLE_NAME, ID_STORY, STORY_NAME, AUTHOR, STATUS,TYPES,AVATAR,NUMBER_OF_CHAPTER,REVIEW,CHAPTER_READING);
        sqLiteDatabase.execSQL(create_stories_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.e(DatabaseHandler.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        String drop_stories_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        sqLiteDatabase.execSQL(drop_stories_table);
        onCreate(sqLiteDatabase);
    }

    public void addStory(Story story) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_STORY, story.getIdStory());
        values.put(STORY_NAME, story.getStoryName());
        values.put(AUTHOR, story.getAuthor());
        values.put(STATUS, story.getStatus());
        values.put(TYPES, story.getType());
        values.put(NUMBER_OF_CHAPTER, story.getNumberOfChapter());
        values.put(AVATAR, story.getAvatar());
        values.put(REVIEW, story.getReview());
        values.put(CHAPTER_READING, story.getIdCurrentChapter());
        sqLiteDatabase.insert(TABLE_NAME, null, values);
        sqLiteDatabase.close();
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return null;
    }
    public Cursor rawQuery(String sql, String[] selectionArgs){
        return null;
    }

    //Lay mot ban ghi theo id
    public Story getStory(int idStory) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, ID_STORY + " = ?", new String[] { String.valueOf(idStory) },null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Story story = cursorToStory(cursor);

        cursor.close();
        return story;
    }


    //Lay tat ca cac ban ghi
    public List<Story> getAllStories() {
        List<Story>  stories = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Story story = new Story(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),
                    cursor.getInt(6),cursor.getString(7),cursor.getInt(8));
            stories.add(story);
            cursor.moveToNext();
        }
        return stories;
    }

    //Update

    public void updateStory(Story story) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_STORY, story.getIdStory());
        values.put(STORY_NAME, story.getStoryName());
        values.put(AUTHOR, story.getAuthor());
        values.put(STATUS, story.getStatus());
        values.put(TYPES, story.getType());
        values.put(NUMBER_OF_CHAPTER, story.getNumberOfChapter());
        values.put(AVATAR, story.getAvatar());
        values.put(REVIEW, story.getReview());
        db.update(TABLE_NAME, values, ID_STORY + " = ?", new String[] { String.valueOf(story.getIdStory())});
        db.close();
    }
    public void updateStory(Story story,int idChapterReading) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_STORY, story.getIdStory());
        values.put(STORY_NAME, story.getStoryName());
        values.put(AUTHOR, story.getAuthor());
        values.put(STATUS, story.getStatus());
        values.put(TYPES, story.getType());
        values.put(NUMBER_OF_CHAPTER, story.getNumberOfChapter());
        values.put(AVATAR, story.getAvatar());
        values.put(REVIEW, story.getReview());
        values.put(CHAPTER_READING, idChapterReading);
        db.update(TABLE_NAME, values, ID_STORY + " = ?", new String[] { String.valueOf(story.getIdStory())});
        db.close();
    }

    //Delete a record
    public void deleteStory(int idStory) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_STORY + " = ?", new String[] { String.valueOf(idStory) });
        db.close();
    }

    public int getStoryCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public boolean exitsStory(int idStory){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, ID_STORY + " = ?", new String[] { String.valueOf(idStory) },null, null, null);
        if(cursor.getCount()==1){
            return true;
        }
        return false;
    }
    @NotNull
    private Story cursorToStory(Cursor cursor){
        Story story = new Story(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),
                cursor.getInt(6),cursor.getString(7),cursor.getInt(8));
        return story;
    }

}
