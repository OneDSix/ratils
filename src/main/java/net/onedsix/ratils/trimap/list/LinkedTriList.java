package net.onedsix.ratils.trimap.list;

import net.onedsix.ratils.trimap.AbstractTriList;

import java.util.LinkedList;

public class LinkedTriList<D,K,V> extends AbstractTriList<D,K,V> {
    public LinkedTriList(D preferredDivider) {
        super(new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), preferredDivider);
    }
}
