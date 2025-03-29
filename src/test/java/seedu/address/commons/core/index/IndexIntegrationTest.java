package seedu.address.commons.core.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests for Index class with other data structures.
 */
public class IndexIntegrationTest {
    private Set<Index> expectedSetIndexes;

    @BeforeEach
    public void setUp() {
        expectedSetIndexes = new HashSet<>();
        expectedSetIndexes.add(Index.fromOneBased(1));
        expectedSetIndexes.add(Index.fromOneBased(2));
        expectedSetIndexes.add(Index.fromOneBased(3));
        expectedSetIndexes.add(Index.fromOneBased(4));
        expectedSetIndexes.add(Index.fromOneBased(5));
    }

    @Test
    public void addToIndexSet_duplicateIndexes_ignoresDuplicate() {
        Set<Index> indexSet = new HashSet<>();
        // duplicate indexes should be ignored
        indexSet.add(Index.fromOneBased(1));
        indexSet.add(Index.fromOneBased(1));
        indexSet.add(Index.fromOneBased(1));

        indexSet.add(Index.fromOneBased(2));

        indexSet.add(Index.fromOneBased(3));

        indexSet.add(Index.fromZeroBased(3));
        indexSet.add(Index.fromOneBased(4));

        indexSet.add(Index.fromOneBased(5));

        assertEquals(expectedSetIndexes, indexSet);
    }
}
