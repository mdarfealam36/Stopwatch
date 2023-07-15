package com.mdarfealam.stopwatch

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import com.mdarfealam.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var isRunning = false
    private var minutes:String? ="00:00:00"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnClock.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialoge_box)
            var numberPicker= dialog.findViewById<NumberPicker>(R.id.numberPicker)
            numberPicker.minValue=0
            numberPicker.maxValue=5
            dialog.findViewById<Button>(R.id.btn_setTime).setOnClickListener {

                minutes = numberPicker.value.toString()

                binding.chronometer.text = dialog.findViewById<NumberPicker>(R.id.numberPicker).value.toString()+" mins"
                dialog.dismiss()
            }
            dialog.show()
            runningTime()
        }


        binding.btnStart.setOnClickListener {
            runningTime()
        }
    }

    fun runningTime(){

        if (!isRunning){
            isRunning=false
            if (!minutes.equals("00:00:00")){
                var totalmin=minutes!!.toInt()*60*1000L
                var countDown=1000L
                binding.chronometer.base = SystemClock.elapsedRealtime()+totalmin
                binding.chronometer.format="%S %S"
                binding.chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                    var elapsedtime = SystemClock.elapsedRealtime() -binding.chronometer.base
                    if (elapsedtime>=totalmin){
                        binding.chronometer.stop()
                        isRunning=false
                        binding.btnStart.text= (R.drawable.baseline_play).toString()
                    }
                }
            }
            else{
                isRunning=true
                binding.chronometer.base=SystemClock.elapsedRealtime()
                binding.btnStart.text = "Stop"
                binding.chronometer.start()

            }
        }
        else{
            binding.chronometer.stop()
            isRunning=false
            binding.btnStart.text = "Run"

        }

    }
}