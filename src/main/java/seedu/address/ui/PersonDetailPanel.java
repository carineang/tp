package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * A UI component representing an empty state for the person detail section.
 */
public class PersonDetailPanel extends UiPart<Region> {
    private static final String FXML = "PersonDetailPanel.fxml";

    /**
     * Creates a {@code PersonDetailPanel} that acts as a fallback or empty state for the person detail section.
     */
    public PersonDetailPanel() {
        super(FXML);
    }
}
