package seedu.address.commons.trie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class NodeTest {

    private static final char VALID_CHAR_1 = 'a';
    private static final char VALID_CHAR_2 = 'b';

    @Test
    public void hasSingleChild_oneChild_correctOutput() {
        Node root = new Node();

        Node node1 = new Node(VALID_CHAR_1, root);

        root.addChild(VALID_CHAR_1, node1);

        assertEquals(true, root.hasSingleChild());
    }

    @Test
    public void hasSingleChild_multipleChilds_correctOutput() {
        Node root = new Node();

        Node node1 = new Node(VALID_CHAR_1, root);
        Node node2 = new Node(VALID_CHAR_2, node1);

        root.addChild(VALID_CHAR_1, node1);
        root.addChild(VALID_CHAR_2, node2);

        assertEquals(false, root.hasSingleChild());
    }

    @Test
    public void getSingleChild_oneChild_correctOutput() {
        Node root = new Node();

        Node node1 = new Node(VALID_CHAR_1, root);

        root.addChild(VALID_CHAR_1, node1);

        assertEquals(node1, root.getSingleChild());
    }

    @Test
    public void getSingleChild_multipleChilds_errorThrown() {
        Node root = new Node();

        Node node1 = new Node(VALID_CHAR_1, root);
        Node node2 = new Node(VALID_CHAR_2, node1);

        root.addChild(VALID_CHAR_1, node1);
        root.addChild(VALID_CHAR_2, node2);

        assertThrows(AssertionError.class, () -> root.getSingleChild());
    }

}
