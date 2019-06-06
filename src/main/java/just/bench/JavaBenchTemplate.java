package just.bench;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class JavaBenchTemplate {
    @Param({"1000", "10000", "100000"})
    private int arraySize;

    private String[] arr;

    @Setup
    public void setup() {
        arr = new String[arraySize];
        for (int i = 0; i < arraySize; i++) {
            arr[i] = String.valueOf(i);
        }
    }

    @Benchmark
    public void bench(Blackhole bh) {
        for (String s : arr) {
            bh.consume(s);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JavaBenchTemplate.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}
