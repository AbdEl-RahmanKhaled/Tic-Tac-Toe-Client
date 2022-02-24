package com.iti.tictactoeclient.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Notification {
    protected String type;

    public static final String NOTIFICATION_GAME_INVITATION = "gameInvitationNotification"; // home done
    public static final String NOTIFICATION_UPDATE_STATUS = "updateStatusNotification"; // home done
    public static final String NOTIFICATION_START_GAME = "startGameNotification"; // home done
    public static final String NOTIFICATION_UPDATE_BOARD = "updateBoardNotification"; // game
    public static final String NOTIFICATION_COMPETITOR_CONNECTION_ISSUE = "competitorConnectionIssueNotification"; // game done
    public static final String NOTIFICATION_ASK_TO_PAUSE = "askToPauseNotification"; // game done
    public static final String NOTIFICATION_MESSAGE = "messageNotification"; // game j done
    public static final String NOTIFICATION_FINISH_GAME = "finishGameNotification"; // game done
    public static final String NOTIFICATION_ASK_TO_RESUME = "askToResumeNotification"; // history n done
    public static final String NOTIFICATION_RESUME_GAME = "resumeGameNotification"; // history n done
    public static final String NOTIFICATION_PAUSE_GAME = "pauseGameNotification"; // game done


    public Notification() {

    }

    public Notification(@JsonProperty("type") String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
