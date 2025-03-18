package seedu.address.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.KeyEventUtil.getCtrlDownKeyEvent;
import static seedu.address.testutil.KeyEventUtil.getCtrlUpKeyEvent;
import static seedu.address.testutil.KeyEventUtil.getEnterKeyEvent;
import static seedu.address.testutil.KeyEventUtil.getEscapeKeyEvent;

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
    public void setSelection_ignoresInvalidIndex_success() {
        controller = new CommandHistoryMenuController(nonEmptyCommandHistory, text -> {});
        controller.setSelection(-1);
        assertEquals(Optional.empty(), controller.getCommandSelectionIndex());
    }

    @Test
    public void setSelection_validIndexSelected_success() {
        controller = new CommandHistoryMenuController(nonEmptyCommandHistory, text -> {});
        controller.setSelection(2);
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(2, controller.getCommandSelectionIndex().get());


    }

    @Test
    public void clearSelection_multipleElements_resetsToZero() {
        controller = new CommandHistoryMenuController(FXCollections.observableArrayList("test1", "test2", "test3"),
                text -> {});
        assertTrue(controller.getCommandSelectionIndex().isEmpty());
        controller.moveUp();
        assertTrue(controller.getCommandSelectionIndex().isPresent());
        assertEquals(0, controller.getCommandSelectionIndex().get());
        controller.clearSelection();
        assertTrue(controller.getCommandSelectionIndex().isEmpty());
    }




    @Test
    public void bakeEventHandler_movementDownKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isMovementDownCalled);
        // Set up stubs
        KeyEvent downKeyEventStub = getCtrlDownKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController.bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(downKeyEventStub);
        assertTrue(actionHandlerStub.isMovementDownCalled);
    }

    @Test
    public void bakeEventHandler_movementUpKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isMovementUpCalled);
        // Set up stubs
        KeyEvent upKeyEventStub = getCtrlUpKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController.bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(upKeyEventStub);
        assertTrue(actionHandlerStub.isMovementUpCalled);
    }
    @Test
    public void bakeEventHandler_enterKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isCloseActionCalled);
        // Set up stubs
        KeyEvent enterKeyEventStub = getEnterKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController
                .bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(enterKeyEventStub);
        assertTrue(actionHandlerStub.isCloseActionCalled);
    }

    @Test
    public void bakeEventHandler_escapeKeyEventHandlerCalled_success() {
        assertFalse(actionHandlerStub.isCloseActionCalled);
        // Set up stubs
        KeyEvent escapeKeyEventStub = getEscapeKeyEvent();
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController
                .bakeEventHandler(() -> actionHandlerStub);

        eventHandler.handle(escapeKeyEventStub);
        assertTrue(actionHandlerStub.isCloseActionCalled);
    }

    @Test
    public void bakeEventHandle_nonKeyEvent_handlerCalled() {
        assertFalse(actionHandlerStub.isMovementUpCalled);
        assertFalse(actionHandlerStub.isMovementDownCalled);
        assertFalse(actionHandlerStub.isCloseActionCalled);
        // Set up stubs
        EventHandler<KeyEvent> eventHandler = CommandHistoryMenuController.bakeEventHandler(() -> actionHandlerStub);
        KeyEvent unrelatedKeyEvent = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.A, false, false, false, false);

        eventHandler.handle(unrelatedKeyEvent);
        assertFalse(actionHandlerStub.isMovementUpCalled);
        assertFalse(actionHandlerStub.isMovementDownCalled);
        assertFalse(actionHandlerStub.isCloseActionCalled);
    }

    static class CommandHistoryActionHandlerStub implements CommandHistoryActionHandler {
        private boolean isMovementUpCalled = false;
        private boolean isMovementDownCalled = false;
        private boolean isCloseActionCalled = false;

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
            isCloseActionCalled = true;
        }
    }
}
