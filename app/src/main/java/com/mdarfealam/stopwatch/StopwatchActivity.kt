package com.mdarfealam.stopwatch

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.mdarfealam.stopwatch.databinding.ActivityStopwatchBinding
import kotlinx.coroutines.Runnable

/**
 * Created by - Android Rider.
 * Website - www.androidrider.com
 * Youtube - Android Rider
 */

class StopwatchActivity : AppCompatActivity() {


    lateinit var  binding: ActivityStopwatchBinding

    private var isRunning=false
    private var timerSeconds = 0
    private val handler = Handler(Looper.getMainLooper())

    private  val runnable = object : Runnable{

        override fun run() {
            timerSeconds++

            val hours = timerSeconds/3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timerText.text = time

            handler.postDelayed(this, 1000)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val typeface = resources.getFont(R.font.digital_font)
        binding.timerText.typeface = typeface

        var lapList = ArrayList<String>()
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lapList)
        binding.listView.adapter = arrayAdapter

        binding.lapBtn.setOnClickListener {
            if (isRunning){
                lapList.add(binding.timerText.text.toString())
                arrayAdapter.notifyDataSetChanged()
            }
        }


        binding.startBtn.setOnClickListener {
            startTimer()
        }

        binding.stopBtn.setOnClickListener {
            stopTimer()
        }

        binding.resetBtn.setOnClickListener {
            resetTimer()
        }


    }

    private fun startTimer(){
        if (!isRunning){
            handler.postDelayed(runnable, 1000)
            isRunning = true

            binding.startBtn.isEnabled = false
            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true
        }
    }

    private fun stopTimer(){
        if (isRunning){
            handler.removeCallbacks(runnable)
            isRunning = false

            binding.startBtn.isEnabled = true
            binding.startBtn.text = "Resume"
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = false
        }
    }

    private fun resetTimer(){
        stopTimer()

        timerSeconds = 0
        binding.timerText.text = "00:00:00"

        binding.startBtn.isEnabled = true
        binding.startBtn.text = "Start"
        binding.resetBtn.isEnabled = false

    }
}