/*
 * Copyright (c) 2022 Ankit Suda.
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

package com.ankitsuda.rebound.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ankitsuda.rebound.domain.LogSetType
import com.ankitsuda.rebound.domain.entities.ExerciseLogEntry
import com.ankitsuda.rebound.ui.theme.LocalThemeState
import com.ankitsuda.rebound.ui.theme.ReboundTheme


private val ExerciseLogEntryComparator = Comparator<ExerciseLogEntry> { left, right ->
    left.setNumber?.compareTo(right.setNumber ?: 0) ?: 0
}

@Composable
fun SessionExerciseCardItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    exerciseName: String,
    entries: List<ExerciseLogEntry>
) {
    val sortedEntries by remember(key1 = entries) {
        mutableStateOf(entries.sortedWith(ExerciseLogEntryComparator))
    }

    fun getRevisedSetNumbers(): List<Pair<String, Color?>> {
        var counter = 0
        val newPairs = sortedEntries.map {
            when (it.setType ?: LogSetType.NORMAL) {
                LogSetType.NORMAL -> {
                    counter++
                    Pair(counter.toString(), null)
                }
                LogSetType.WARM_UP -> Pair("W", Color.Yellow)
                LogSetType.DROP_SET -> {
                    counter++
                    Pair("D", Color.Magenta)
                }
                LogSetType.FAILURE -> {
                    counter++
                    Pair("F", Color.Red)
                }
            }
        }

        return newPairs
    }

    val revisedSetsTexts by remember(key1 = sortedEntries) {
        mutableStateOf(getRevisedSetNumbers())
    }

    AppCard(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exerciseName, style = ReboundTheme.typography.body1,
                color = LocalThemeState.current.onBackgroundColor
            )
            RSpacer(8.dp)
            if (sortedEntries.isNotEmpty()) {
                for (i in sortedEntries.indices) {
                    val entry = sortedEntries[i]
                    SessionExerciseSetItem(
                        entry = entry,
                        revisedSetText = revisedSetsTexts[sortedEntries.indexOf(entry)],
                    )
                    if (i != sortedEntries.size - 1) {
                        RSpacer(8.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun SessionExerciseSetItem(
    entry: ExerciseLogEntry,
    revisedSetText: Pair<String, Color?>,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
//                .background(Color(224, 224, 224))
                .background(ReboundTheme.colors.card/*.lighterOrDarkerColor(0.15f)*/),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = revisedSetText.first,
                style = ReboundTheme.typography.caption,
                color = revisedSetText.second ?: LocalThemeState.current.onBackgroundColor,
                textAlign = TextAlign.Center,
            )
        }

        RSpacer(16.dp)
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(ReboundTheme.colors.onBackground)) {
                append((entry.weight ?: 0).toString())
            }
            withStyle(style = SpanStyle(ReboundTheme.colors.onBackground.copy(alpha = 0.65f))) {
                append(" kg")
            }
        })
        RSpacer(20.dp)
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(ReboundTheme.colors.onBackground)) {
                append((entry.reps ?: 0).toString())
            }
            withStyle(style = SpanStyle(ReboundTheme.colors.onBackground.copy(alpha = 0.65f))) {
                append(" reps")
            }
        })
    }
}
