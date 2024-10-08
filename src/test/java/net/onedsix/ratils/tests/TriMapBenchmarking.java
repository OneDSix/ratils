package net.onedsix.ratils.tests;

import net.onedsix.ratils.trimap.*;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.UUID;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/** Speed tests for {@link TriMap} and all inheritors. Work in progress. */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class TriMapBenchmarking {
    
    public static Random rand = new Random(42069L); // i am very mature, static seed for testing
    
    @Test
    public void benchmark() {
        Options opt = new OptionsBuilder()
                              .include(TriMapBenchmarking.class.getSimpleName())
                              .forks(1)
                              .build();
    }
    
    @Setup
    public void setup() {
        HashTriMap<Integer,Integer,UUID> htm = new HashTriMap<>(0);
        LinkedTriList<Integer,Integer,UUID> ltl = new LinkedTriList<>(0);
        TriArray<Integer,Integer,UUID> atl = new TriArray<>(0, 1028); // overshoot just in case
    }
    
    @Benchmark
    public void benchmarkPuts(TriMap<Integer,Integer,UUID> map) {
        int iterations = 1000;
        while (iterations != 0) {
            map.put(iterations, iterations+10, UUID.randomUUID());
            iterations--;
        }
    }
    
    @Benchmark
    public void benchmarkGets(TriMap<Integer,Integer,UUID> map) {
    
    }
    
}
