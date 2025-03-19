package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Panel that displays notes
 */
public class NotePanel extends UiPart<Region> {
    private static final String FXML = "NotePanel.fxml";

    private static final String DEFAULT_TEXT = "";

    @FXML
    private Label note;

    public NotePanel() {
       super(FXML);
       note.setText(DEFAULT_TEXT);
    }

    public void changeText(String text) {
        note.setText(text);
    }
}
