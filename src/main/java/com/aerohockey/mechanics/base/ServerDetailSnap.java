package com.aerohockey.mechanics.base;

import com.aerohockey.mechanics.avatar.Bonus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sergeybutorin on 15/05/2017.
 */
@SuppressWarnings("NullableProblems")
public class ServerDetailSnap extends ServerSnap {
    @NotNull Map<Bonus.Types, Coords> bonuses;

    public Map<Bonus.Types, Coords> getBonuses() {
        return bonuses;
    }

    public void setBonuses(@NotNull List<Bonus> bonuses) {
        this.bonuses = bonuses.stream().collect(Collectors.toMap(Bonus::getType, Bonus::getCoords));
    }
}
