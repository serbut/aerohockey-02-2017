package com.aerohockey.mechanics.base;

/**
 * Created by sergeybutorin on 01.05.17.
 */
@SuppressWarnings("unused")
public class GameOverSnap {
    long id;
    int changeRating;

    public void setId(long id) {
        this.id = id;
    }

    public void setChangeRating(int changeRating) {
        this.changeRating = changeRating;
    }

    public long getId() {
        return id;
    }

    public int getChangeRating() {
        return changeRating;
    }
}
