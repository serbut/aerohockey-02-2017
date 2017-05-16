package com.aerohockey.mechanics.base;

import com.aerohockey.mechanics.avatar.Bonus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sergeybutorin on 15/05/2017.
 */
@SuppressWarnings("NullableProblems")
public class ServerDetailSnap extends ServerSnap {
    @NotNull List<BonusSnap> bonuses;

    @SuppressWarnings("unused")
    public @NotNull List<BonusSnap> getBonuses() {
        return bonuses;
    }

    public void setBonuses(@NotNull List<Bonus> bonuses) {
        this.bonuses = bonuses.stream().map(Bonus::getSnap).collect(Collectors.toList());
    }
}
