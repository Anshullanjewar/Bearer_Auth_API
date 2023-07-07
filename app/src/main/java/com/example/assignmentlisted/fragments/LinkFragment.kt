package com.example.assignmentlisted.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.GRAY
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentlisted.R
import com.example.assignmentlisted.data.ServerResponse
import com.example.assignmentlisted.data.network.ApiService
import com.example.assignmentlisted.databinding.FragmentLinkBinding
import com.example.assignmentlisted.util.ApiState
import com.example.assignmentlisted.vm.MainViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class LinkFragment : Fragment() {

    private var binding: FragmentLinkBinding? = null
    private val viewModel: MainViewModel by viewModels()

    private var btnTop: Button?=null
    private var btnRecent: Button?=null
    private var linkContainer: FrameLayout?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(inflater, container, false)

        btnTop =view?.findViewById(R.id.topbtn)
        btnRecent =view?.findViewById(R.id.recentbtn)
        linkContainer=view?.findViewById(R.id.fragmentLinkContainer)

        btnRecent?.setTextColor(Color.GRAY)
        btnRecent?.setBackgroundColor(Color.TRANSPARENT)

        btnTop?.setOnClickListener {
            replaceFragment((TopLinkFragment()))
            btnRecent?.setBackgroundColor(Color.BLUE)
        }

        btnRecent?.setOnClickListener {
            replaceFragment((RecentLinkFragment()))
            btnTop?.setTextColor(Color.GRAY)
            btnTop?.setBackgroundColor(Color.TRANSPARENT)
            btnRecent?.setBackgroundColor(Color.BLUE)
            btnRecent?.setTextColor(Color.GRAY)
        }



        greetings()
        getDetails()



        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
//////////////////////////////////////////////////////////////////////////////////////////////
    fun greetings() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (currentHour) {
            in 0..11 -> "Good Morning"
            in 12..15 -> "Good Afternoon"
            else -> "Good Evening"
        }

        binding?.tvGreetings?.text = greeting
    }


    private fun getDetails() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                viewModel.data.collect {
                    when (it) {
                        is ApiState.Success -> {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()

                            val supportWhatsappNumber = it.data.support_whatsapp_number
                            val totalClicks = it.data.total_clicks
                            val topLocation = it.data.top_location
                            val topSource = it.data.top_source
                            val bestTime = it.data.startTime
                            val overallUrlChart = it.data.data.overall_url_chart
                            viewModel.overallUrlChart.value = overallUrlChart



                            binding?.tvTotalClick?.text= totalClicks.toString()
                            binding?.tvQuantity?.text= topLocation
                            binding?.tvTopSource?.text= topSource
                            binding?.tvBestTime?.text= bestTime

                            binding?.cvWhatsapp?.setOnClickListener {
                                openWhatsApp(supportWhatsappNumber)
                            }


                        }

                        is ApiState.Failure -> {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()

                        }

                        ApiState.Loading -> {
                            Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()

                        }

                    }
                }
            }
        }
    }

    private fun openWhatsApp(phoneNumber: String) {
        val packageManager = requireContext().packageManager
        val whatsappIntent = Intent(Intent.ACTION_SEND).apply {
            data = Uri.parse("https://wa.me/$phoneNumber")
        }

        if (whatsappIntent.resolveActivity(packageManager) != null) {
            startActivity(whatsappIntent)
        } else {
            val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=com.whatsapp")
                setPackage("com.android.vending")
            }

            val browserIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
            }

            val chooserIntent = Intent.createChooser(playStoreIntent, "Install WhatsApp").apply {
                val activities = mutableListOf<Intent>()
                if (playStoreIntent.resolveActivity(packageManager) != null) {
                    activities.add(playStoreIntent)
                }
                if (browserIntent.resolveActivity(packageManager) != null) {
                    activities.add(browserIntent)
                }
                putExtra(Intent.EXTRA_INITIAL_INTENTS, activities.toTypedArray())
            }

            startActivity(chooserIntent)
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentLinkContainer, fragment)
            .commit()
    }


    private fun setupchart(){
        val lineChart = binding?.lineChart

        lineChart?.clear()

        val entries = mutableListOf<Entry>()
        viewModel.overallUrlChart.value?.forEach { (key, value) ->
            entries.add(Entry(key.toFloat(), value.toFloat()))
        }
        val dataSet = LineDataSet(entries, "URL Chart")

// Customize the appearance of the line chart
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK

// Create a LineData object and set it to the chart
        val lineData = LineData(dataSet)
        lineChart?.data = lineData

// Refresh the chart
        lineChart?.invalidate()
    }



}

