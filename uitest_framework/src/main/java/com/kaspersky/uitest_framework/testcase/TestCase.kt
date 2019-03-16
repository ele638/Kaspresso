package com.kaspersky.uitest_framework.testcase

import com.kaspersky.uitest_framework.device.ScreenshotManager
import com.kaspersky.uitest_framework.configurator.Configurator
import com.kaspersky.uitest_framework.logger.UiTestLogger

/**
 *  Base class for all test cases.
 *  Extend the class with a project-wide TestCase and avoid direct inheritance from this one.
 *  Nesting TestCases is not permitted and may produce an Exception
 *  caused by re-initialization of the configurator, use [Scenario] instead
 */
abstract class TestCase(
    configBuilder: Configurator.Builder = Configurator.Builder().default()
) {
    protected val logger: UiTestLogger = Configurator.logger

    private var stepCounter = 0

    init {
        configBuilder.commit()
    }

    protected fun precondition(description: String, actions: () -> Unit) {
        logger.i(description)
        actions.invoke()
    }

    protected fun step(description: String, actions: () -> Unit) {
        logger.i("___________________________________________________________________________")
        logger.i("TEST STEP: $description")

        val screenshotTag = "${this::class.simpleName}_step_${++stepCounter}"

        try {
            actions.invoke()
            ScreenshotManager.makeScreenshotIfPossible(screenshotTag)
        } catch (e: Throwable) {
            ScreenshotManager.makeScreenshotIfPossible(
                "${screenshotTag}_failure_${e::class.simpleName}"
            )

            throw e
        }
    }

}
