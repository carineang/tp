package seedu.address.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CommandHistoryMenuControllerTest {
    private CommandHistoryMenuController controller;
    private CommandHistoryActionHandlerStub actionHandlerStub;
    private final ObservableList<String> nonEmptyCommandHistory = FXCollections
            .unmodifiableObservableList(FXCollections.observableArrayList("test1", "test2", "test3"));

    @BeforeEach
    public void setUp() {
        actionHandlerStub = new CommandHistoryActionHandlerStub();
        controller = new CommandHistoryMenuController(FXCollections.observableArrayList(), text -> {});
    }

    @Test
    public void moveUp_emptyList_noSelection() {
        controller.moveUp();
        assertEquals(Optional.empty(), controller.getCommandSelectionIndex());
    }

    @Test
    public void moveUp_multipleElementsMoveUp_selectsDefaultIndex() {
        controller = new CommandHistoryMenuController(
                nonEmptyCommandHistory, text -> assertEquals(nonEmptyCommandHistory.get(0), text));
        controller.moveUp();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(0, controller.getCommandSelectionIndex().get());
    }

    @Test
    public void moveUp_multipleElementsLastSelected_wrapsAround() {
        controller = new CommandHistoryMenuController(
                nonEmptyCommandHistory, text -> {});
        assertTrue(controller.getCommandSelectionIndex().isEmpty());

        controller.moveUp();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(0, controller.getCommandSelectionIndex().get());

        controller.moveUp();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(2, controller.getCommandSelectionIndex().get());

        controller.moveUp();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(1, controller.getCommandSelectionIndex().get());
    }

    @Test
    public void moveDown_emptyList_noSelection() {
        controller.moveDown();
        assertEquals(Optional.empty(), controller.getCommandSelectionIndex());
    }

    @Test
    public void moveDown_multipleElements_decrementsCorrectly() {
        controller = new CommandHistoryMenuController(nonEmptyCommandHistory, text -> {});
        assertEquals(Optional.empty(), controller.getCommandSelectionIndex());

        controller.moveDown();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(0, controller.getCommandSelectionIndex().get());

        controller.moveDown();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(1, controller.getCommandSelectionIndex().get());

        controller.moveDown();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(2, controller.getCommandSelectionIndex().get());
    }

    @Test
    public void resetSelection_multipleElements_resetsToZero() {
        controller = new CommandHistoryMenuController(FXCollections.observableArrayList("test1", "test2", "test3"),
                text -> {});
        assertTrue(controller.getCommandSelectionIndex().isEmpty());
        controller.moveUp();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(0, controller.getCommandSelectionIndex().get());
        controller.clearSelection();
        assertTrue(controller.getCommandSelectionIndex().isEmpty());
    }

    private KeyEvent getMovementDownKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.DOWN, false, true, false, false);
    }

    private KeyEvent getMovementUpKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.UP, false, true, false, false);
    }

    private KeyEvent getEnterKeyEvent() {
        return new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
    }

    @Test
    public void bakeEventHandler_movementDownKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isMovementDownCalled);
        // Set up stubs
        KeyEvent downKeyEventStub = getMovementDownKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController.bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(downKeyEventStub);
        assertTrue(actionHandlerStub.isMovementDownCalled);
    }

    @Test
    public void bakeEventHandler_movementUpKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isMovementUpCalled);
        // Set up stubs
        KeyEvent upKeyEventStub = getMovementUpKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController.bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(upKeyEventStub);
        assertTrue(actionHandlerStub.isMovementUpCalled);
    }
    @Test
    public void bakeEventHandler_enterKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isEnterPressedCalled);
        // Set up stubs
        KeyEvent enterKeyEventStub = getEnterKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController
                .bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(enterKeyEventStub);
        assertTrue(actionHandlerStub.isEnterPressedCalled);
    }
    @Test
    public void bakeEventHandle_nonKeyEvent_handlerCalled() {
        assertFalse(actionHandlerStub.isMovementUpCalled);
        assertFalse(actionHandlerStub.isMovementDownCalled);
        assertFalse(actionHandlerStub.isEnterPressedCalled);
        // Set up stubs
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController.bakeEventHandler(() -> actionHandlerStub);
        KeyEvent unrelatedKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);

        eventHandler.handle(unrelatedKeyEvent);
        assertFalse(actionHandlerStub.isMovementUpCalled);
        assertFalse(actionHandlerStub.isMovementDownCalled);
        assertFalse(actionHandlerStub.isEnterPressedCalled);
    }

    static class CommandHistoryActionHandlerStub implements CommandHistoryActionHandler {
        private boolean isMovementUpCalled = false;
        private boolean isMovementDownCalled = false;
        private boolean isEnterPressedCalled = false;

        @Override
        public void handleMovementUp() {
            isMovementUpCalled = true;
        }

        @Override
        public void handleMovementDown() {
            isMovementDownCalled = true;
        }

        @Override
        public void handleCloseAction() {
            isEnterPressedCalled = true;
        }
    }
}
