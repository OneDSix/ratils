package net.onedsix.ratils.poly;

/** An interface all poly-structs implement. */
public interface PolyStruct<A, B> {
    /** Returns all the objects this struct contains. */
    Object[] getValues();
    /** Returns the count of values that this struct can contain. */
    int getCount();
    /** All poly-structs contain at least 2 values, this returns the first.
     * @see PolyStruct#getSecond() */
    A getFirst();
    /** All poly-structs contain at least 2 values, this returns the second.
     * @see PolyStruct#getFirst()  */
    B getSecond();
}
