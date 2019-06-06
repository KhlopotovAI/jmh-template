package just.bench


import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit

@Fork(value = 2, jvmArgs = ["-Xms2G", "-Xmx2G"])
@State(Scope.Benchmark)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
open class KotlinBenchTemplate {
    @Param("1000", "10000", "100000")
    @JvmField var `array size`: Int = 0

    private lateinit var arr: Array<String>

    @Setup fun setup() {
        var counter = 0
        arr = Array(`array size`) {
            counter.apply { inc() }.toString()
        }
    }

    @Benchmark fun bench(bh: Blackhole) {
        for (s in arr) {
            bh.consume(s)
        }
    }

    companion object {
        /**
         * need package before run
         */
        @JvmStatic fun main(args: Array<String>) {
            val options = OptionsBuilder()
                    .include(KotlinBenchTemplate::class.java.simpleName)
                    .build()
            Runner(options).run()
        }
    }
}