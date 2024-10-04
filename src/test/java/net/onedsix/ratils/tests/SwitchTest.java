package net.onedsix.ratils.tests;

import lombok.extern.slf4j.Slf4j;
import net.onedsix.ratils.result.Result;
import net.onedsix.ratils.result.SwitchBuilder;
import org.junit.jupiter.api.Test;

@Slf4j
public class SwitchTest {
    
    @Test
    public void testSwitch() {
    
        Result<Boolean, Exception> test = new Result<>(false, new RuntimeException());
        
        int switched = new SwitchBuilder<Result<Boolean, Exception>, Integer>(test)
                .put(Result::errored, run -> {
                        log.error("Test!", run.error);
                        return 1;
                })
                .put(check -> !check.errored(), run -> {
                        log.info("Did not error!");
                        return 0;
                })
                .run();
        
        log.info(String.valueOf(switched));
    }
    
}
