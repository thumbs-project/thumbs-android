package com.thumbs.android.thumbsAndroid.ui.shake

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.thumbs.android.thumbsAndroid.ui.base.BaseActivity
import org.koin.android.ext.android.inject

public class ShakeEvent : BaseActivity(), ShakeContract.ShakeEvent, SensorEventListener{

    val presenter  by inject<ShakeContract.ShakeEventListener>()

    override fun startInject() {
        presenter.attachView(this)
    }

    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SLOP_TIME_MS = 500
    private val SHAKE_COUNT_RESET_TIME_MS = 3000

    private var mListener: ShakeContract.ShakeEvent? = null
    private var mShakeTimestamp: Long = 0
    private var mShakeCount: Int = 0

    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeEvent? = null

    init{
        // ShakeDetector initialization
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeEvent()
        mShakeDetector!!.setOnShakeListener(object : ShakeContract.ShakeEvent {
            override fun onShake(count: Int) {
            }
        })
    }

    override fun onShake(count: Int) {
    }

    fun setOnShakeListener(listener: ShakeContract.ShakeEvent) {
        this.mListener = listener
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // ignore
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (mListener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH

            // gForce will be close to 1 when there is no movement.
            val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                val now = System.currentTimeMillis()
                // ignore shake events too close to each other (500ms)
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }

                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0
                }

                mShakeTimestamp = now
                mShakeCount++

                Log.d("tag", mShakeCount.toString())

                mListener!!.onShake(mShakeCount)
            }
        }
    }


    public override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager!!.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    public override fun onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager!!.unregisterListener(mShakeDetector)
        super.onPause()
    }

}