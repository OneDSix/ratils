package net.onedsix.ratils.poly;

import lombok.AllArgsConstructor;

/** A simple 3 object structure for holding 3 objects in one.<br>
 * @see Duo
 * @see PolyStruct */
@AllArgsConstructor
public class Trio<A, B, C> implements PolyStruct<A, B> {
    public A first;
    public B second;
    public C third;
    
    @Override
    public Object[] getValues() {
        return new Object[]{first,second,third};
    }
    
    @Override
    public int getCount() {
        return 3;
    }
    
    @Override
    public A getFirst() {
        return first;
    }
    
    @Override
    public B getSecond() {
        return second;
    }
    
    public C getThird() {
        return third;
    }
}
