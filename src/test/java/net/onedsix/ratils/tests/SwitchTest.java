package net.onedsix.ratils.tests;

import net.onedsix.ratils.Result;
import net.onedsix.ratils.switching.SwitchBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchTest {
    
    private static final Logger log = LoggerFactory.getLogger(SwitchTest.class);
    
    @Test
    public void testSwitch() {
    
        Result<Boolean, Exception> test = new Result<>(false, new RuntimeException());
        
        int switched = new SwitchBuilder<Result<Boolean, Exception>, Integer>()
                .putCase(Result::errored, run -> {
                        log.error("Test!", run.error);
                        return 1;
                })
                .putCase(check -> !check.errored(), run -> {
                        log.info("Did not error!");
                        return 0;
                })
                .run(test);
        
        
        log.info(String.valueOf(switched));
    }
    
}
