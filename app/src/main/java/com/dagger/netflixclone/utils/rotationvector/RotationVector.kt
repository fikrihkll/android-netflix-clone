package com.dagger.netflixclone.utils.rotationvector

import com.dagger.netflixclone.ui.entity.AircraftAxes
import kotlinx.coroutines.flow.Flow

interface RotationVector {

    fun streamGyroscopeData(): Flow<AircraftAxes>

}