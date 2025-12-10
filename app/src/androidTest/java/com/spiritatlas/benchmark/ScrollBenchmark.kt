package com.spiritatlas.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Scroll Performance Benchmark for SpiritAtlas
 *
 * Measures frame rendering performance during scroll operations
 * Target: 60fps (16.67ms per frame) for smooth scrolling
 *
 * Key metrics:
 * - frameOverrunMs: Time beyond 16.67ms per frame
 * - frameDurationCpuMs: CPU time per frame
 * - jankyFrames: Frames taking > 16.67ms
 *
 * Screens tested:
 * - Home screen: LazyColumn with multiple complex composables
 * - Profile Library: LazyColumn with animated profile cards
 * - Compatibility Detail: Long scrolling content with animations
 *
 * Run with:
 * ./gradlew :app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.spiritatlas.benchmark.ScrollBenchmark
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ScrollBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    /**
     * Benchmark Home screen scroll performance
     * Critical screen with multiple animated components
     */
    @Test
    fun scrollHomeScreen() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        iterations = 5,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            device.waitForIdle()
        }
    ) {
        // Find the scrollable content (LazyColumn)
        val scrollable = device.findObject(
            By.scrollable(true)
        )

        // Perform scroll gestures
        repeat(3) {
            scrollable?.fling(Direction.DOWN)
            device.waitForIdle()
        }

        repeat(3) {
            scrollable?.fling(Direction.UP)
            device.waitForIdle()
        }
    }

    /**
     * Benchmark Profile Library scroll performance
     * Tests performance with animated profile cards
     */
    @Test
    fun scrollProfileLibrary() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            device.waitForIdle()

            // Navigate to Profile Library
            // Adjust selector based on your navigation implementation
            val profileButton = device.wait(
                Until.findObject(By.text("Profiles")),
                5000
            )
            profileButton?.click()
            device.waitForIdle()
        }
    ) {
        val scrollable = device.findObject(By.scrollable(true))

        // Scroll through profile list
        repeat(5) {
            scrollable?.fling(Direction.DOWN)
            device.waitForIdle()
        }

        repeat(5) {
            scrollable?.fling(Direction.UP)
            device.waitForIdle()
        }
    }

    /**
     * Benchmark Compatibility Detail screen scroll
     * Long content with multiple animated sections
     */
    @Test
    fun scrollCompatibilityDetail() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            device.waitForIdle()

            // Navigate to Compatibility screen (adjust based on your navigation)
            val compatibilityButton = device.wait(
                Until.findObject(By.text("Compatibility")),
                5000
            )
            compatibilityButton?.click()
            device.waitForIdle()

            // Wait for detail screen to load
            Thread.sleep(1000)
        }
    ) {
        val scrollable = device.findObject(By.scrollable(true))

        // Scroll through long compatibility report
        repeat(8) {
            scrollable?.fling(Direction.DOWN)
            device.waitForIdle()
        }

        repeat(8) {
            scrollable?.fling(Direction.UP)
            device.waitForIdle()
        }
    }

    /**
     * Benchmark rapid scrolling to test jank under stress
     */
    @Test
    fun stressTestRapidScroll() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        iterations = 3,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            device.waitForIdle()
        }
    ) {
        val scrollable = device.findObject(By.scrollable(true))

        // Rapid scrolling without waiting
        repeat(10) {
            scrollable?.fling(Direction.DOWN)
        }

        device.waitForIdle()

        repeat(10) {
            scrollable?.fling(Direction.UP)
        }

        device.waitForIdle()
    }

    /**
     * Benchmark smooth dragging (slower than fling)
     * Tests performance during user-controlled scroll
     */
    @Test
    fun scrollDragging() = benchmarkRule.measureRepeated(
        packageName = "com.spiritatlas.app",
        metrics = listOf(FrameTimingMetric()),
        compilationMode = CompilationMode.None(),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()
            device.waitForIdle()
        }
    ) {
        val scrollable = device.findObject(By.scrollable(true))

        // Slow, controlled dragging
        repeat(5) {
            scrollable?.drag(0.8f) // Drag 80% of screen height
            device.waitForIdle()
        }
    }
}
