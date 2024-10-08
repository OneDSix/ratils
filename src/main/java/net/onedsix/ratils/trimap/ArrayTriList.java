package net.onedsix.ratils.trimap;

import java.util.ArrayList;

public class ArrayTriList<D, K, V> extends AbstractTriList<D, K, V> {
    public ArrayTriList(D preferredDivider) {
        super(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), preferredDivider);
    }
}
