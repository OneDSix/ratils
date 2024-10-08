package net.onedsix.ratils.trimap;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashTriMap<D, K, V> extends AbstractTriMap<D, K, V> {
    protected ConcurrentHashTriMap(D preferredDivider) {
        super(new ConcurrentHashMap<>(), preferredDivider);
    }
}
