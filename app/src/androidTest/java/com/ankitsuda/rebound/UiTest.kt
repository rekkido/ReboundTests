/*
 * Copyright (c) 2024 Ankit Suda.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.ankitsuda.rebound

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.ankitsuda.rebound.ui.MainActivity
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import io.qameta.allure.kotlin.Allure.step
import io.qameta.allure.kotlin.AllureId
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Copyright (c) 2024 Ankit Suda.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

@RunWith(AllureAndroidJUnit4::class)
class UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    @AllureId("TEST-1")
    @DisplayName("Open workout tab")
    @Description("Open app, then tap and check")
    fun myTest() {
        step("Check that app is open and Home title is shown") {
            composeTestRule.onNodeWithTag("title")
                .assertIsDisplayed()
                .assert(hasText("Home"))
        }
        step("Tap Workout Tab in Navbar") {
            composeTestRule.onNodeWithTag("WorkoutNavBar").performClick()
        }
        step("Check that Workout title is shown") {
            composeTestRule.onNodeWithTag("title")
            .assertIsDisplayed()
            .assert(hasText("Workout"))
        }
    }
}
