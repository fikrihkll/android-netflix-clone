package com.dagger.netflixclone.utils.rotationvector

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.dagger.netflixclone.ui.entity.AircraftAxes
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidRotationVector(private val context: Context) : RotationVector {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    override fun streamGyroscopeData(): Flow<AircraftAxes> = callbackFlow {
        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ROTATION_VECTOR -> {
                        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                        SensorManager.getOrientation(rotationMatrix, orientationAngles)

                        val pitch = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
                        val roll = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()

                        val adjustedPitch = adjustAngle(pitch)
                        val adjustedRoll = adjustAngle(roll)
                        trySend(
                            AircraftAxes(
                                roll = adjustedRoll,
                                pitch = adjustedPitch
                            )
                        )
                    }
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
        }

        sensorManager.registerListener(sensorListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_FASTEST)

        awaitClose { sensorManager.unregisterListener(sensorListener) }
    }

    private fun adjustAngle(angle: Float): Float {
        var adjustedAngle = angle
        while (adjustedAngle > 180) {
            adjustedAngle -= 360
        }
        while (adjustedAngle < -180) {
            adjustedAngle += 360
        }
        return adjustedAngle
    }

}