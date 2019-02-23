package com.thumbs.android.thumbsAndroid.ui.shake

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import com.thumbs.android.thumbsAndroid.R
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import org.koin.android.ext.android.inject

class ShakeEvent : BaseActivity(), ShakeContract.ShakeEvent, SensorEventListener{

    val presenter  by inject<ShakeContract.ShakeEventListener>()

    override fun startInject() {
        presenter.attachView(this)
    }

    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SLOP_TIME_MS = 500
    private val SHAKE_COUNT_RESET_TIME_MS = 3000

    private var Listener: ShakeContract.ShakeEvent? = null
    private var ShakeTimestamp: Long = 0
    private var ShakeCount: Int = 0

    private var SensorManager: SensorManager? = null
    private var Accelerometer: Sensor? = null
    private var ShakeDetector: ShakeEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
    }

    init{
        // ShakeDetector initialization
        SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Accelerometer = SensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        ShakeDetector = ShakeEvent()
        ShakeDetector!!.setOnShakeListener(object : ShakeContract.ShakeEvent {
            override fun onShake(count: Int) {
            }
        })
    }

    override fun onShake(count: Int) {
    }

    fun setOnShakeListener(listener: ShakeContract.ShakeEvent) {
        this.Listener = listener
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // ignore
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (Listener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gX = x / android.hardware.SensorManager.GRAVITY_EARTH
            val gY = y / android.hardware.SensorManager.GRAVITY_EARTH
            val gZ = z / android.hardware.SensorManager.GRAVITY_EARTH

            // gForce will be close to 1 when there is no movement.
            val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                val now = System.currentTimeMillis()
                // ignore shake events too close to each other (500ms)
                if (ShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }

                // reset the shake count after 3 seconds of no shakes
                if (ShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    ShakeCount = 0
                }

                ShakeTimestamp = now
                ShakeCount++

                Log.d("tag", ShakeCount.toString())
                Listener!!.onShake(ShakeCount)
            }


        }
    }


    public override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        SensorManager!!.registerListener(ShakeDetector, Accelerometer, android.hardware.SensorManager.SENSOR_DELAY_UI)
    }

    public override fun onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        SensorManager!!.unregisterListener(ShakeDetector)
        super.onPause()
    }

}