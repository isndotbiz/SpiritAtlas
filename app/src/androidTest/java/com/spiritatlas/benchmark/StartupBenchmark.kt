package com.spiritatlas.benchmark

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Startup Benchmark for SpiritAtlas
 *
 * Measures app startup performance in different scenarios:
 * - Cold start: App process not running, requires most work
 * - Warm start: Process alive but activity destroyed
 * - Hot start: Process and activity alive
 *
 * Target metrics:
 * - Cold start: < 1.5s (excellent), < 2s (good)
 * - Warm start: < 800ms
 * - Hot start: < 400ms
 *
 * Run with:
 * ./gradlew :app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.spiritatlas.benchmark.StartupBenchmark
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class StartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    /**
     * Cold startup benchmark - Most important for user experience
     * Tests first app launch from completely stopped state
     */
    @Test
    fun startupColdCompilationNone() = startup(
        compilationMode = CompilationMode.None(),
        startupMode = StartupMode.COLD
    )

    /**
     * Cold startup with baseline profile
     * Measures improvement from baseline profile optimization
     */
    @Test
    fun startupColdCompilationBaselineProfile() = startup(
        compilationMode = CompilationMode.Partial(
            baselineProfileMode = BaselineProfileMode.Require
        ),
        startupMode = StartupMode.COLD
    )

    /**
     * Warm startup benchmark
     * Process alive, activity recreated (e.g., after pressing back)
     */
    @Test
    fun startupWarmCompilationNone() = startup(
        compilationMode = CompilationMode.None(),
        startupMode = StartupMode.WARM
    )

    /**
     * Hot startup benchmark
     * Activity brought to foreground (e.g., from recent apps)
     */
    @Test
    fun startupHotCompilationNone() = startup(
        compilationMode = CompilationMode.None(),
        startupMode = StartupMode.HOT
    )

    private fun startup(
        compilationMode: CompilationMode,
        startupMode: StartupMode
    ) = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(StartupTimingMetric()),
        compilationMode = compilationMode,
        iterations = 10,
        startupMode = startupMode,
        setupBlock = {
            pressHome()
        }
    ) {
        startActivityAndWait()

        // Wait for key UI elements to be visible
        device.waitForIdle()
    }
}
