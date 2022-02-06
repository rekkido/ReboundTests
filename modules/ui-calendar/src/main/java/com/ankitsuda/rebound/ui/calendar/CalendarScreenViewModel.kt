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

package com.ankitsuda.rebound.ui.calendar

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.ankitsuda.base.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarScreenViewModel @Inject constructor() : ViewModel() {
    private var _calendar: SnapshotStateList<CalendarItem> = mutableStateListOf()
    val calendar = _calendar
    private var displayedDatesRange = DatesRange.emptyRange()
    private var minMaxDatesRange = NullableDatesRange()

    fun getMonth(year: Int, month: Int): List<CalendarItem> {
        Timber.d("Getting month $month year $year")
        return CalendarUtils(1).generateCalendarItemsForMonth(year, month)
    }

    fun getCalendar(
    ) {

        displayedDatesRange = DisplayedDatesRangeFactory.getDisplayedDatesRange(
            initialDate = CalendarDate.today,
            minDate = null,
            maxDate = null
        )

        val calendarItems = CalendarUtils(1).generateCalendarItems(
            dateFrom = displayedDatesRange.dateFrom,
            dateTo = displayedDatesRange.dateTo
        )


        _calendar.addAll(calendarItems)

    }

    fun generateNextCalendarItems() {
        Timber.d("generateNextCalendarItems")
        val maxDate = minMaxDatesRange.dateTo
        if (maxDate != null && displayedDatesRange.dateTo.monthsBetween(maxDate) == 0) {
            return
        }

        val generateDatesFrom = displayedDatesRange.dateTo.plusMonths(1)
        val generateDatesTo: CalendarDate

        generateDatesTo = if (maxDate != null) {
            val monthBetween = generateDatesFrom.monthsBetween(maxDate)

            if (monthBetween > CalendarUtils.MONTHS_PER_PAGE) {
                generateDatesFrom.plusMonths(CalendarUtils.MONTHS_PER_PAGE)
            } else {
                generateDatesFrom.plusMonths(monthBetween)
            }
        } else {
            generateDatesFrom.plusMonths(CalendarUtils.MONTHS_PER_PAGE)
        }

        val calendarItems = CalendarUtils(1).generateCalendarItems(
            dateFrom = generateDatesFrom,
            dateTo = generateDatesTo
        )

        _calendar.addAll(calendarItems)
        displayedDatesRange = displayedDatesRange.copy(dateTo = generateDatesTo)
    }

    fun generatePrevCalendarItems() {
        Timber.d("generatePrevCalendarItems")
        val minDate = minMaxDatesRange.dateFrom
        if (minDate != null && minDate.monthsBetween(displayedDatesRange.dateFrom) == 0) {
            return
        }

        val generateDatesFrom: CalendarDate
        val generateDatesTo = displayedDatesRange.dateFrom.minusMonths(1)

        generateDatesFrom = if (minDate != null) {
            val monthBetween = minDate.monthsBetween(generateDatesTo)

            if (monthBetween > CalendarUtils.MONTHS_PER_PAGE) {
                generateDatesTo.minusMonths(CalendarUtils.MONTHS_PER_PAGE)
            } else {
                generateDatesTo.minusMonths(monthBetween)
            }

        } else {
            generateDatesTo.minusMonths(CalendarUtils.MONTHS_PER_PAGE)
        }

        val calendarItems = CalendarUtils(1).generateCalendarItems(
            dateFrom = generateDatesFrom,
            dateTo = generateDatesTo
        )

        _calendar.addAll(0, calendarItems)
        displayedDatesRange = displayedDatesRange.copy(dateFrom = generateDatesFrom)
    }
}