package net.onedsix.ratils.switching;

/** A common interface for {@link SwitchBuilder} and {@link FinalSwitch}
 * @param <T> The input to the switch, given to all the cases and their checks
 * @param <R> The return type of {@link #run(T input)}
 * @see SwitchBuilder
 * @see FinalSwitch*/
public interface EnhancedSwitch<T, R> {
    /** Runs the switch and returns {@link R} */
    R run(final T input);
}
