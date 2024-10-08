package net.onedsix.ratils.switching;

import net.onedsix.ratils.poly.Trio;

import java.util.LinkedList;
import java.util.function.Function;

/** Ports Java 14 and 18 enhanced switch statements to lesser versions,
 * allowing for switches to be used with types other than numeric and string types.
 * <pre>{@code
 * import net.onedsix.ratils.SwitchBuilder
 *
 * public class SwitchTest {
 *     public static final EnhancedSwitch<MyEnum, Integer> builtSwitch =
 *             new SwitchBuilder()
 *                 .putCase(obj -> obj == MyEnum.One, obj -> {
 *                     System.out.println(obj.toString());
 *                 })
 *                 .putDefault(obj -> {
 *                     System.out.println("Default");
 *                 })
 *                 .build();
 * }
 * }</pre>
 * Unfortunately, {@link #putCase(Function, Function)} and {@link #putDefault(Function)}
 * can not use the terms {@code case} and {@code default} due to it conflicting with existing Java syntax.
 * @see #putCase(Function, Function) putCase(Function, Function) - Add a switch statement
 * @see #putDefault(Function) putDefault(Function) - Add a switch statement for when no case is valid
 * @see #run(T) run(T) - Builds and runs the switch
 * @see #build() build() - Builds the switch for later
 * @see FinalSwitch FinalSwitch - Built Subclass
 * @see EnhancedSwitch EnhancedSwitch - Common Interface
 * */
@SuppressWarnings("unused")
public class SwitchBuilder<T, R> implements EnhancedSwitch<T, R> {
    private final LinkedList<Trio<Function<T, Boolean>, Function<T, R>, Boolean>> switches = new LinkedList<>();
    private Function<T, R> defaultSwitch = null;
    
    /** Begins a {@link SwitchBuilder}.
     * Call {@link #build()} or {@link #run(T)} to use the switch.
     * @see SwitchBuilder */
    public SwitchBuilder() {}
    
    /** Adds a branch to the switch tree that checks if the first statement is true, and executes the second statement.
     * Always {@code break}s after the statement ends.
     * @see #putCase(Function, Function, Boolean) */
    public SwitchBuilder<T, R> putCase(Function<T, Boolean> check, Function<T, R> run) {
        switches.add(new Trio<>(check, run, true));
        return this;
    }
    /** Adds a branch to the switch tree that checks if the first statement is true,
     * executes the second statement, and if the third value is {@code true} it acts as a {@code break}, {@code false} it doesn't.
     * @see #putCase(Function, Function) */
    public SwitchBuilder<T, R> putCase(Function<T, Boolean> check, Function<T, R> run, Boolean breaks) {
        switches.add(new Trio<>(check, run, breaks));
        return this;
    }
    /** Sets a default branch if none of the other branches passed.
     * Running this more than once will overwrite any existing defaults. */
    public SwitchBuilder<T, R> putDefault(Function<T, R> run) {
        defaultSwitch = run;
        return this;
    }
    
    /** A shorthand for constructing and executing {@link FinalSwitch#run(T input)}. */
    @Override
    public R run(final T input) {
        return build().run(input);
    }
    /** Creates a {@link FinalSwitch} allowing storage of the switch statement for later. */
    public FinalSwitch<T, R> build() {
        // concerning
        return new FinalSwitch<>((Trio<Function<T, Boolean>, Function<T, R>, Boolean>[]) switches.toArray(), defaultSwitch);
    }
}
