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

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ankitsuda.rebound.ui.MainActivity
import io.qameta.allure.AllureId
import io.qameta.allure.junit4.DisplayName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    @AllureId("TEST-1")
    @DisplayName("Open Workout Tab")
    fun testButton() {
        // Your UI test code goes here
        // Perform UI interactions and assertions
        composeTestRule.onNodeWithTag("WorkoutNavBar").performClick()
        // Add assertions to verify the expected behavior
        composeTestRule.onNodeWithTag("title")
            .assertExists()
            .assertTextEquals("Workout")
    }
}
