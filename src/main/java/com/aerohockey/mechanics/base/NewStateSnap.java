package com.aerohockey.mechanics.base;

import com.aerohockey.mechanics.avatar.Bonus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by sergeybutorin on 15/05/2017.
 */
public class NewStateSnap {

    @NotNull List<Bonus> bonuses;

    public List<Bonus> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonus> bonuses) {
        this.bonuses = bonuses;
    }
}
