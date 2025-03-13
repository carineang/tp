package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void test_nameMatches_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of("Alice"), true, false, false, false, false);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_phoneMatches_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of("12345"), false, true, false, false, false);
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_emailMatches_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of("example"), false, false, true, false, false);
        assertTrue(predicate.test(new PersonBuilder().withEmail("example@email.com").build()));
    }

    @Test
    public void test_addressMatches_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of("Street"), false, false, false, true, false);
        assertTrue(predicate.test(new PersonBuilder().withAddress("123 Street Name").build()));
    }

    @Test
    public void test_tagsMatch_returnsTrue() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of("friend"), false, false, false, false, true);
        assertTrue(predicate.test(new PersonBuilder().withTags("friend").build()));
    }

    @Test
    public void test_noMatchingKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(List.of("Charlie"), true, false, false, false, false);
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(List.of("Alice"), true, false, false, false, false);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(List.of("Alice"), true, false, false, false, false);
        assertTrue(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(List.of("Alice"), true, false, false, false, false);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(List.of("Bob"), true, false, false, false, false);
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void equals_differentSearchFields_returnsFalse() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(List.of("Alice"), true, false, false, false, false);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(List.of("Alice"), false, true, false, false, false);
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
