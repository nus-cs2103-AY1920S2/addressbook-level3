package seedu.address.commons.trie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TrieTest {

    private static final String SEARCH_STRING = "he";
    private static final String VALID_WORD_1 = "hello";
    private static final String VALID_WORD_2 = "help";
    private static final String VALID_WORD_3 = "helm";
    private static final String LONGEST_PREFIX_1_AND_2 = "hel";

    @Test
    public void constructor() {
        Trie trie = new Trie();
        assertNotNull(trie);
    }

    @Test
    public void insert_validWord() {
        Trie trie = new Trie();
        trie.insert("abcd");
    }

    @Test
    public void insert_null_errorThrown() {
        Trie trie = new Trie();
        assertThrows(NullPointerException.class, () -> trie.insert(null));
    }

    @Test
    public void listAllSimilarWords_oneWordTrie_correctLongestPrefix() {
        Trie trie = new Trie();
        trie.insert(VALID_WORD_1);

        SimilarWordsResult result = trie.listAllSimilarWords(SEARCH_STRING);
        assertEquals(VALID_WORD_1, result.longestPrefixString);
    }

    @Test
    public void listAllSimilarWords_multipleWordsTrie_correctLongestPrefix() {
        Trie trie = new Trie();
        trie.insert(VALID_WORD_1);
        trie.insert(VALID_WORD_2);
        trie.insert(VALID_WORD_3);

        SimilarWordsResult result = trie.listAllSimilarWords(SEARCH_STRING);
        assertEquals(LONGEST_PREFIX_1_AND_2, result.longestPrefixString);
    }

    @Test
    public void listAllSimilarWords_null_errorThrown() {
        Trie trie = new Trie();
        assertThrows(NullPointerException.class, () -> trie.listAllSimilarWords(null));
    }

}
