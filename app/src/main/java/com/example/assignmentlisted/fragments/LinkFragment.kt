package com.example.assignmentlisted.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.assignmentlisted.R
import com.example.assignmentlisted.databinding.FragmentLinkBinding
import com.example.assignmentlisted.util.ApiState
import com.example.assignmentlisted.vm.MainViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class LinkFragment : Fragment() {

    private var binding: FragmentLinkBinding? = null
    private val viewModel: MainViewModel by viewModels()

    private var linkContainer: FrameLayout?=null
    private val tL= TopLinkFragment()
    private val rL= RecentLinkFragment()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(inflater, container, false)


        linkContainer = binding?.fragmentLinkContainer


        binding?.recentbtn?.setBackgroundResource(android.R.color.transparent)
        binding?.recentbtn?.setTextColor(Color.GRAY)

        replaceFragment(tL)
        binding?.topbtn?.setOnClickListener {
            replaceFragment(tL)
            binding?.topbtn?.setBackgroundResource(android.R.color.holo_blue_light)
            binding?.recentbtn?.setBackgroundResource(android.R.color.transparent)
            binding?.recentbtn?.setTextColor(Color.GRAY)
            binding?.topbtn?.setTextColor(Color.WHITE)



        }

        binding?.recentbtn?.setOnClickListener {
            replaceFragment(rL)

            binding?.recentbtn?.setBackgroundResource(android.R.color.holo_blue_light)
            binding?.topbtn?.setBackgroundResource(android.R.color.transparent)
            binding?.topbtn?.setTextColor(Color.GRAY)
            binding?.recentbtn?.setTextColor(Color.WHITE)

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
    private fun greetings() {
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

                                populateLineChart(overallUrlChart)


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
        childFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentLinkContainer, fragment)
            ?.commit()

    }


    private fun populateLineChart(data: Map<String, Int>) {
        val entries = ArrayList<Entry>()


        var index = 0
        for ((key, value) in data) {
            entries.add(Entry(index.toFloat(), value.toFloat()))
            index++
        }

        val dataSet = LineDataSet(entries, "Chart Label")
        val lineData = LineData(dataSet)

        binding?.lineChart?.data = lineData
        binding?.lineChart?.invalidate()
    }



}

