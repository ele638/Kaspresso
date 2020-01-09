package com.kaspersky.components.uiautomatordsl.intercepting.delegate

import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.kaspersky.components.uiautomatordsl.UiAutomatorDslConfigurator
import com.kaspersky.components.uiautomatordsl.dsl.common.builders.UiViewSelector
import com.kaspersky.components.uiautomatordsl.dsl.screen.UiScreen
import com.kaspersky.components.uiautomatordsl.intercepting.interaction.UiObjectInteraction
import com.kaspersky.components.uiautomatordsl.intercepting.intercept.UiInterceptor
import com.kaspersky.components.uiautomatordsl.intercepting.operation.UiObjectAction
import com.kaspersky.components.uiautomatordsl.intercepting.operation.UiObjectAssertion
import com.kaspersky.components.uiautomatordsl.intercepting.operation.UiOperationBaseImpl
import com.kaspersky.components.uiautomatordsl.intercepting.operation.UiOperationType

/**
 * Delegation class for [androidx.test.uiautomator.UiObject2].
 * Wraps all available public calls and intercepts into [check] and [perform].
 *
 * @see UiDelegate
 * @see UiInterceptor
 */
class UiObjectDelegate(
    device: UiDevice,
    selector: UiViewSelector,
    elementClassName: String
) : UiDelegate<UiObjectInteraction, UiObjectAssertion, UiObjectAction> {

    override val interaction: UiObjectInteraction = UiObjectInteraction(device, selector, elementClassName)
    override var interceptor: UiInterceptor<UiObjectInteraction, UiObjectAssertion, UiObjectAction>? = null

    fun loadView() {
        interaction.tryToFindUiObject()
    }

    fun check(
        type: UiOperationType,
        description: String? = null,
        assert: UiObject2.() -> Unit
    ) {
        val uiAssertion = getUiActionBase(type, description, assert)
        check(uiAssertion)
    }

    fun perform(
        type: UiOperationType,
        description: String? = null,
        action: UiObject2.() -> Unit
    ) {
        val uiAction = getUiActionBase(type, description, action)
        perform(uiAction)
    }

    private fun getUiActionBase(
        type: UiOperationType,
        description: String? = null,
        action: UiObject2.() -> Unit
    ) = UiOperationBaseImpl(type, description, action)

    fun check(uiAssertion: UiObjectAssertion) {
        if (!interceptCheck(uiAssertion)) interaction.check(uiAssertion)
    }

    fun perform(uiAction: UiObjectAction) {
        if (!interceptPerform(uiAction)) interaction.perform(uiAction)
    }

    override fun screenInterceptors(): Iterable<UiInterceptor<UiObjectInteraction, UiObjectAssertion, UiObjectAction>> =
        UiScreen.UI_OBJECT_INTERCEPTORS

    override fun globalInterceptor(): UiInterceptor<UiObjectInteraction, UiObjectAssertion, UiObjectAction>? =
        UiAutomatorDslConfigurator.uiObjectInterceptor
}