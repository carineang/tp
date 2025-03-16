package seedu.address.testutil;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Provides utility methods for generating JavaFX input KeyEvents.
 */
public class KeyEventUtil {
    public static KeyEvent getCtrlDownKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, true, false, false);
    }

    public static KeyEvent getCtrlUpKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, true, false, false);
    }

    public static KeyEvent getEnterKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
    }

    public static KeyEvent getEscapeKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
    }
}
