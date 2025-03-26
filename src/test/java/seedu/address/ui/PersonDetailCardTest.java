package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

public class PersonDetailCardTest extends ApplicationTest {
    private PersonDetailCard personDetailCard;
    private Person person;

    @Override
    public void start(javafx.stage.Stage stage) {
        person = new Person(
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johndoe@example.com"),
                new Address("123, Jurong West St 42, #08-04"),
                Set.of(new Tag("friend"), new Tag("VIP")),
                new Note("Important client")
        );

        personDetailCard = new PersonDetailCard(person);
        stage.setScene(new javafx.scene.Scene(personDetailCard.getRoot()));
        stage.show();
    }


    @Test
    public void testNameDisplayedCorrectly() {
        Text nameText = lookup("#name").query(); // assuming #name is a Text node
        assertEquals("John Doe", nameText.getText());
    }

    @Test
    public void testPhoneDisplayedCorrectly() {
        Text phoneText = lookup("#phone").query();
        assertEquals("98765432", phoneText.getText());
    }

    @Test
    public void testEmailDisplayedCorrectly() {
        Text emailText = lookup("#email").query();
        assertEquals("johndoe@example.com", emailText.getText());
    }

    @Test
    public void testAddressDisplayedCorrectly() {
        Text addressText = lookup("#address").query();
        assertEquals("123, Jurong West St 42, #08-04", addressText.getText());
    }

    @Test
    public void testNoteDisplayedCorrectly() {
        Text noteText = lookup("#note").query();
        assertEquals("Important client", noteText.getText());
    }

    @Test
    public void testTagsDisplayedCorrectly() {
        FlowPane tagsPane = lookup("#tags").query();
        assertEquals(2, tagsPane.getChildren().size());

        Label tag1 = (Label) tagsPane.getChildren().get(0);
        Label tag2 = (Label) tagsPane.getChildren().get(1);

        assertEquals("VIP", tag1.getText()); // Sorted alphabetically
        assertEquals("friend", tag2.getText());
    }
}
