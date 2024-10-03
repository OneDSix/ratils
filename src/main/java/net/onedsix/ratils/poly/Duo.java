package net.onedsix.ratils.poly;

import lombok.AllArgsConstructor;

/** A simple pair structure for storing 2 values at once in the same object
 * @see Trio
 * @see PolyStruct */
@AllArgsConstructor
public class Duo<A, B> implements PolyStruct<A, B> {
    public A key;
    public B value;
    
    @Override
    public Object[] getValues() {
        return new Object[]{key, value};
    }
    
    @Override
    public int getCount() {
        return 2;
    }
    
    @Override
    public A getFirst() {
        return key;
    }
    
    @Override
    public B getSecond() {
        return value;
    }
}
