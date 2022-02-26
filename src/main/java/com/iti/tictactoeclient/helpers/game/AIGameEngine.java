package com.iti.tictactoeclient.helpers.game;

import com.iti.tictactoeclient.models.Match;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AIGameEngine extends GameEngine {
    private Map<String, Button> board;
    public String computerMove;

    public List<String> getAvailableCells() {
        List<String> avCells = new ArrayList<>();
        for (String b : board.keySet()) {
            if (board.get(b).getText().equals("")) {
                avCells.add(b);
            }
        }
        return avCells;
    }

    private void placeMove(String position, char turn) {
        board.get(position).setText(String.valueOf(turn));
    }

    public int minMax(int depth, char turn) {
        if (checkWinner(String.valueOf(Match.CHOICE_X), board)) {
            return -1;
        }

        if (checkWinner(String.valueOf(Match.CHOICE_O), board)) {
            return 1;
        }
        List<String> avCells = getAvailableCells();
        if (avCells.isEmpty()) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < avCells.size(); i++) {
            String position = avCells.get(i);

            if (turn == Match.CHOICE_O) {
                placeMove(position, Match.CHOICE_O);
                int currentScore = minMax(depth + 1, Match.CHOICE_X);
                max = Math.max(currentScore, max);

                if (currentScore >= 0) {
                    if (depth == 0) {
                        computerMove = position;
                    }
                }
                if (currentScore == 1) {
                    board.get(position).setText("");
                    break;
                }

                if (i == avCells.size() - 1 && max < 0) {
                    if (depth == 0) {
                        computerMove = position;
                    }
                }

            } else if (turn == Match.CHOICE_X) {
                placeMove(position, Match.CHOICE_X);
                int currentScore = minMax(depth + 1, Match.CHOICE_O);
                min = Math.min(currentScore, min);

                if (min == -1) {
                    board.get(position).setText("");
                    break;
                }
            }
            board.get(position).setText("");
        }
        return turn == Match.CHOICE_O ? max : min;
    }

    public void easy() {
        List<String> positions = getAvailableCells();
        if (positions.size() > 0) {
            computerMove = getAvailableCells().get(new Random().nextInt(positions.size()));
        }
    }

    public void setBoard(Map<String, Button> board) {
        this.board = board;
    }
}
