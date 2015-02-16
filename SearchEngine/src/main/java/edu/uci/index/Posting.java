package edu.uci.index;

import java.io.Serializable;
import java.util.List;

/**
 * Created by swanand on 2/14/2015.
 */
public class Posting implements Serializable {
    private final String docId;
    private final List<Integer> positions;

    public Posting(String docId, List<Integer> positions) {
        this.docId = docId;
        this.positions = positions;
    }
}
