package net.onedsix.ratils.result;

import net.onedsix.ratils.poly.Duo;

import java.util.LinkedList;
import java.util.function.Function;

public class SwitchBuilder<T, R> {
    //
    private final LinkedList<Duo<Function<T, Boolean>, Function<T, R>>>
            switches = new LinkedList<>();
    //
    private final T input;
    
    //
    public SwitchBuilder(T input) {
        this.input = input;
    }
    
    //
    public SwitchBuilder<T, R> put(Function<T, Boolean> check, Function<T, R> run) {
        switches.add(new Duo<>(check, run));
        return this;
    }
    
    //
    public R run() {
        R returns = null;
        for (Duo<Function<T, Boolean>, Function<T, R>> duo : switches) {
            if (duo.key.apply(this.input)) {
                returns = duo.value.apply(this.input);
                break;
            }
        }
        return returns;
    }
    
    /** @see #run() */
    public R build() {
        return run();
    }
}
