package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents whether a Person is pinned in the address book.
 * Guarantees: immutable
 */
public class Pin {

    public static final String PINNED_SYMBOL = "[PINNED]";

    public static final String NOT_PINNED_SYMBOL = "";

    public final boolean isPinned;

    public final String value;

    /**
     * Pins a {@code Person}.
     *
     * @param isPinned whether to pin the person.
     */
    public Pin(Boolean isPinned) {
        requireNonNull(isPinned);
        this.isPinned = isPinned;
        this.value = isPinned ? PINNED_SYMBOL : NOT_PINNED_SYMBOL;
    }

    /**
     * Pins a {@code Person}
     */
    public Pin() {
        this.isPinned = true;
        this.value = PINNED_SYMBOL;
    }

    /**
     * Pins a {@code Person}
     * @param pin String format of whether to pin the person.
     */
    public Pin(String pin) {
        requireNonNull(pin);
        if (pin.isEmpty()) {
            this.isPinned = false;
            this.value = NOT_PINNED_SYMBOL;
        } else if (pin.equals(PINNED_SYMBOL)) {
            this.isPinned = true;
            this.value = PINNED_SYMBOL;
        } else {
            throw new IllegalArgumentException("Cannot resolve pin symbol: " + pin);
        }
    }

    /**
     * Returns true if the contact is pinned.
     */
    public boolean isPinned() {
        return isPinned;
    }

    @Override
    public String toString() {
        // 'this' is used here to make it clear its calling on this note.
        if (this.isPinned) {
            return PINNED_SYMBOL;
        }

        return NOT_PINNED_SYMBOL;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Pin otherPin)) {
            return false;
        }

        return isPinned == otherPin.isPinned();
    }

}
