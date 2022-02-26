package com.iti.tictactoeclient.helpers.game;

import javafx.scene.control.Button;

import java.awt.*;
import java.util.Map;

public class GameEngine {

    public boolean checkWinner(String choice, Map<String, Button> buttons) {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> buttons.get("b1").getText() + buttons.get("b2").getText() + buttons.get("b3").getText();
                case 1 -> buttons.get("b4").getText() + buttons.get("b5").getText() + buttons.get("b6").getText();
                case 2 -> buttons.get("b7").getText() + buttons.get("b8").getText() + buttons.get("b9").getText();
                case 3 -> buttons.get("b1").getText() + buttons.get("b4").getText() + buttons.get("b7").getText();
                case 4 -> buttons.get("b2").getText() + buttons.get("b5").getText() + buttons.get("b8").getText();
                case 5 -> buttons.get("b3").getText() + buttons.get("b6").getText() + buttons.get("b9").getText();
                case 6 -> buttons.get("b1").getText() + buttons.get("b5").getText() + buttons.get("b9").getText();
                case 7 -> buttons.get("b3").getText() + buttons.get("b5").getText() + buttons.get("b7").getText();
                default -> null;
            };
            if (line.equals(choice + choice + choice)) {
                return true;
            }
        }
        return false;
    }

}
