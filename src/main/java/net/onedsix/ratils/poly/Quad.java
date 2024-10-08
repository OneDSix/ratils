package net.onedsix.ratils.poly;

import lombok.AllArgsConstructor;

/** A simple 4 object structure for holding 4 objects in one.<br>
 * @see Duo
 * @see Trio
 * @see PolyStruct */
@AllArgsConstructor
public class Quad<A, B, C, D> implements PolyStruct<A, B> {
    
    public A first;
    public B second;
    public C third;
    public D fourth;
    
    @Override
    public Object[] getValues() {
        return new Object[]{first, second, third, fourth};
    }
    
    @Override
    public int getCount() {
        return 4;
    }
    
    @Override
    public A getFirst() {
        return first;
    }
    
    @Override
    public B getSecond() {
        return second;
    }
}
