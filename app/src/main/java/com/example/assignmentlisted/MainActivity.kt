package com.example.assignmentlisted

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.assignmentlisted.databinding.ActivityMainBinding
import com.example.assignmentlisted.fragments.AddFragment
import com.example.assignmentlisted.fragments.CampaignsFragment
import com.example.assignmentlisted.fragments.CoursesFragment
import com.example.assignmentlisted.fragments.LinkFragment
import com.example.assignmentlisted.fragments.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private var  binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val link = LinkFragment()
        val courses= CoursesFragment()
        val add= AddFragment()
        val campaign= CampaignsFragment()
        val profile= ProfileFragment()

        replaceFragment(link)
        binding?.bottomNavView?.setOnItemSelectedListener {

            when(it.itemId){
                R.id.link -> replaceFragment(link)
                R.id.courses -> replaceFragment(courses)
                R.id.campaign -> replaceFragment(campaign)
                R.id.add -> replaceFragment(add)
                R.id.profile -> replaceFragment(profile)

                else ->{
                }
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }
}