package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class PersonDetailPanelTest extends ApplicationTest {
    private PersonDetailPanel personDetailPanel;

    @Override
    public void start(Stage stage) {
        personDetailPanel = new PersonDetailPanel();
        Scene scene = new Scene(personDetailPanel.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testPersonDetailPanelIsNotNull() {
        assertNotNull(personDetailPanel);
    }

    @Test
    public void testPersonDetailPanelRootIsNotNull() {
        assertNotNull(personDetailPanel.getRoot());
    }
}
