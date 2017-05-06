package com.aerohockey.mechanics.internal;

import com.aerohockey.mechanics.GameSession;
import com.aerohockey.mechanics.avatar.Ball;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergeybutorin on 27.04.17.
 */
@Service
public class BallMovementService {
    private final Map<Ball, GameSession> ballsToMove = new HashMap<>();

    public void registerBallToMove(@NotNull Ball ball, @NotNull GameSession gameSession) {
        ballsToMove.put(ball, gameSession);
    }

    public void executeMoves(long frameTime) {
        for (Map.Entry<Ball, GameSession> entry : ballsToMove.entrySet()) {
            final Ball ball = entry.getKey();
            final GameSession gameSession = entry.getValue();
            ball.move(gameSession, frameTime);
        }
    }

    public void clear() {
        ballsToMove.clear();
    }
}