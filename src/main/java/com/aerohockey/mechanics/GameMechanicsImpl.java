package com.aerohockey.mechanics;

import com.aerohockey.mechanics.base.ClientSnap;
import com.aerohockey.mechanics.internal.ClientSnapService;
import com.aerohockey.mechanics.internal.GameSessionService;
import com.aerohockey.mechanics.internal.ServerSnapService;
import com.aerohockey.model.UserProfile;
import com.aerohockey.services.AccountService;
import com.aerohockey.websocket.RemotePointService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by sergeybutorin on 14.04.17.
 */
public class GameMechanicsImpl implements GameMechanics {
    private static final @NotNull Logger LOGGER = LoggerFactory.getLogger(GameMechanicsImpl.class);

    private @NotNull AccountService accountService;

    private @NotNull ClientSnapService clientSnapService;

    private @NotNull ServerSnapService serverSnapService;

    private @NotNull RemotePointService remotePointService;

    private @NotNull GameSessionService gameSessionService;

    private @NotNull Set<Long> playingUsers = new HashSet<>();

    private @NotNull ConcurrentLinkedQueue<Long> waiters = new ConcurrentLinkedQueue<>();

    private final @NotNull Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @SuppressWarnings("LongLine")
    public GameMechanicsImpl(@NotNull AccountService accountService, @NotNull ClientSnapService clientSnapService,
                             @NotNull RemotePointService remotePointService,
                             @NotNull GameSessionService gameSessionService) {
        this.accountService = accountService;
        this.serverSnapService = serverSnapService;
        this.remotePointService = remotePointService;
        this.gameSessionService = gameSessionService;
        this.clientSnapService = new ClientSnapService();
    }

    @Override
    public void addClientSnapshot(@NotNull Long userId, @NotNull ClientSnap clientSnap) {
        tasks.add(() -> clientSnapService.pushClientSnap(userId, clientSnap));
    }

    @Override
    public void addUser(@NotNull Long user) {
        if (gameSessionService.isPlaying(user)) {
            return;
        }
        waiters.add(user);
    }

    private void tryStartGames() {
        final Set<UserProfile> matchedPlayers = new LinkedHashSet<>();

        while (waiters.size() >= 2 || waiters.size() >= 1 && matchedPlayers.size() >= 1) {
            final Long candidateId = waiters.poll();
            if (!insureCandidate(candidateId)) {
                continue;
            }
            matchedPlayers.add(accountService.getUserById(candidateId));
            if(matchedPlayers.size() == 2) {
                final Iterator<UserProfile> iterator = matchedPlayers.iterator();
                gameSessionService.startGame(iterator.next(), iterator.next());
                matchedPlayers.clear();
            }
        }
        matchedPlayers.stream().map(UserProfile::getId).forEach(waiters::add);
    }

    private boolean insureCandidate(@NotNull Long candidateId) {
        return remotePointService.isConnected(candidateId) &&
                accountService.getUserById(candidateId) != null;
    }

    @Override
    public void gmStep(long frameTime) {
        while (!tasks.isEmpty()) {
            final Runnable nextTask = tasks.poll();
            if (nextTask != null) {
                try {
                    nextTask.run();
                } catch (RuntimeException ex) {
                    LOGGER.error("Cant handle game task", ex);
                }
            }
        }

        for (GameSession session : gameSessionService.getSessions()) {
            clientSnapService.processSnapshotsFor(session);
        }

        final Iterator<GameSession> iterator = gameSessionService.getSessions().iterator();
        final List<GameSession> sessionsToTerminate = new ArrayList<>();
        while (iterator.hasNext()) {
            final GameSession session = iterator.next();
            try {
                serverSnapService.sendSnapshotsFor(session, frameTime);
            } catch (RuntimeException ex) {
                LOGGER.error("Failed send snapshots, terminating the session", ex);
                sessionsToTerminate.add(session);
            }
        }
        sessionsToTerminate.forEach(gameSessionService::notifyGameIsOver);

        tryStartGames();
        clientSnapService.clear();
    }

    @Override
    public void reset() {

    }
}
