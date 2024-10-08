package net.onedsix.ratils.switching;

import net.onedsix.ratils.poly.Trio;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/** Immutable version of {@link SwitchBuilder} for running the switch efficently. */
public final class FinalSwitch<T, R> implements EnhancedSwitch<T, R> {
    private final Trio<Function<T, Boolean>, Function<T, R>, Boolean>[] switches;
    private final Function<T, R> defaultSwitch;
    
    FinalSwitch(Trio<Function<T, Boolean>, Function<T, R>, Boolean>[] switches, @Nullable Function<T, R> defaultSwitch) {
        this.switches = switches;
        this.defaultSwitch = defaultSwitch;
    }
    
    @Override
    public R run(final T input) {
        R returns = null;
        boolean broken = false;
        
        // Main branches
        for (Trio<Function<T, Boolean>, Function<T, R>, Boolean> witch : this.switches) {
            if (witch.first.apply(input)) {
                broken = true;
                returns = witch.second.apply(input);
                if (witch.third) break;
            }
        }
        // Default branch
        if (!broken && defaultSwitch != null)
            returns = defaultSwitch.apply(input);
        
        return returns;
    }
}

