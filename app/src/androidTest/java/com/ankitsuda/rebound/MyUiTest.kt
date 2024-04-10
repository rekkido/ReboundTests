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
import com.ankitsuda.rebound.ui.components.MoreItemCard
import io.qameta.allure.Allure.step
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
    fun testOpenWorkoutTab() {
        step("Open app and navigate to Workout Tab")
        composeTestRule.onNodeWithTag("WorkoutNavBar").performClick()
        step("Check title is Workout")
        composeTestRule.onNodeWithTag("title")
            .assertExists()
            .assertTextEquals("Workout")
    }

    @Test
    @AllureId("TEST-2")
    @DisplayName("Add weight")
    fun testAddWeight() {
        step("Open app and navigate to More Tab")
        composeTestRule.onNodeWithTag("MoreNavBar").performClick()
        step("Check title is More")
        composeTestRule.onNodeWithTag("title")
            .assertExists()
            .assertTextEquals("More")
        step("Tap Measure")
        composeTestRule.onNodeWithContentDescription("Measure").performClick()
        step("Tap Weight")
        composeTestRule.onNodeWithTag("Weight", useUnmergedTree = true).performClick()
        step("Tap Plus")
        composeTestRule.onNodeWithContentDescription("Add measurement").performClick()
        step("Enter weight")
        composeTestRule.onRoot().printToLog("HERE")
        composeTestRule.onNodeWithTag("TextField").performTextInput("12")
        step("Tap Add")
        composeTestRule.onNodeWithTag("AddButton")
        step("Check that weight added")
        composeTestRule.onNodeWithTag("MeasurementValue")
    }

    @Test
    @AllureId("TEST-3")
    @DisplayName("Verify MoreItemCard Clickable")
    fun testMoreItemCardClickable() {
        step("Open app and navigate to More Tab")
        composeTestRule.onNodeWithTag("MoreNavBar").performClick()
        step("Check title is More")
        composeTestRule.onNodeWithTag("title")
            .assertExists()
            .assertTextEquals("More")
        step("Measure is clickable")
        composeTestRule.onNodeWithContentDescription("Measure").assertHasClickAction()
        step("Achievements is clickable")
        composeTestRule.onNodeWithContentDescription("Achievements").assertHasClickAction()
        step("Personalization is clickable")
        composeTestRule.onNodeWithContentDescription("Personalization").assertHasClickAction()
        step("Settings is clickable")
        composeTestRule.onNodeWithContentDescription("Settings").assertHasClickAction()

    }

    @Test
    @AllureId("TEST-4")
    @DisplayName("Verify More tab has Achievement section (Shoudl be Failled)")
    fun testMoreItemCardText() {
        step("Open app and navigate to More Tab")
        composeTestRule.onNodeWithTag("MoreNavBar").performClick()
        step("Check title is More")
        composeTestRule.onNodeWithTag("title")
            .assertExists()
            .assertTextEquals("More")
        step("Achievements is displayed")
        composeTestRule.onNodeWithContentDescription("Achivements").assertExists()
    }

    @Test
    @AllureId("TEST-5")
    @DisplayName("Verify Home Screen has Stats section")
    fun testHomeStats() {
        step("Workouts Card exists")
        composeTestRule.onNodeWithTag("Workouts").assertExists()
        step("Overall Card exists")
        composeTestRule.onNodeWithTag("Overall").assertExists()
    }
}
