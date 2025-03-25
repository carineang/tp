package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.person.Person;

/**
 * An UI component that displays detailed information of a {@code Person} in the right pane of
 * the window.
 */
public class PersonDetailCard extends UiPart<Region> {
    private static final String FXML = "PersonDetailCard.fxml";
    public final Person person;
    @FXML
    private Text name;
    @FXML
    private Text phone;
    @FXML
    private Text address;
    @FXML
    private Text email;
    @FXML
    private FlowPane tags;
    @FXML
    private Text note;

    /**
     * Creates a {@code PersonDetailCard} to display detailed information of a {@code Person}.
     *
     * @param person The person whose details are to be displayed.
     */
    public PersonDetailCard(Person person) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        note.setText(person.getNote().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

    }
}
