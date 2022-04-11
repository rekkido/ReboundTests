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

package com.ankitsuda.rebound.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ankitsuda.rebound.domain.LogSetType
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.*

@Parcelize
@Entity(tableName = "exercise_log_entries")
data class ExerciseLogEntry(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "entry_id")
    val entryId: String,

    @ColumnInfo(name = "log_id")
    var logId: String? = null,
    @ColumnInfo(name = "junction_id")
    var junctionId: String? = null,

    // Number of set
    @ColumnInfo(name = "set_number")
    var setNumber: Int? = null,
    @ColumnInfo(name = "set_type")
    var setType: LogSetType? = null,

    @ColumnInfo(name = "weight")
    var weight: Float? = null,
    @ColumnInfo(name = "reps")
    var reps: Int? = null,

    @ColumnInfo(name = "completed")
    var completed: Boolean = false,

    // Time in milliseconds
    @ColumnInfo(name = "time_recorded")
    var timeRecorded: Long? = null,

    @ColumnInfo(name = "distance")
    var distance: Long? = null,

    @ColumnInfo(name = "weight_unit")
    var weight_unit: String? = null,
    @ColumnInfo(name = "distance_unit")
    var distance_unit: String? = null,


    @ColumnInfo(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @ColumnInfo(name = "update_at")
    var updatedAt: LocalDateTime? = null,
) : Parcelable
