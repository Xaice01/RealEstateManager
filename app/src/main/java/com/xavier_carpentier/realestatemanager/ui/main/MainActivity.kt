package com.xavier_carpentier.realestatemanager.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.databinding.ActivityMainBinding
import com.xavier_carpentier.realestatemanager.ui.create.CreatePropertyFragment
import com.xavier_carpentier.realestatemanager.ui.detail.DetailFragment
import com.xavier_carpentier.realestatemanager.ui.listProperty.ListPropertyFragment
import com.xavier_carpentier.realestatemanager.ui.loanSimulator.LoanSimulatorFragment
import com.xavier_carpentier.realestatemanager.ui.map.MapFragment
import com.xavier_carpentier.realestatemanager.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private val viewModel: MainViewModel by viewModels()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this , object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })



        drawerLayout =  binding.drawerLayout

        val toolbar =binding.appBarMain.toolbar
        setSupportActionBar(toolbar)

        val navigationView: NavigationView =binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            showListProperty()
            navigationView.setCheckedItem(R.id.nav_list_property)
        }

        binding.appBarMain.fab.setOnClickListener { view ->
            replaceFragment(CreatePropertyFragment.newInstance())

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_list_property -> showListProperty()
            R.id.nav_map -> replaceFragment(MapFragment())
            R.id.nav_loan -> replaceFragment(LoanSimulatorFragment())
            R.id.nav_setting -> replaceFragment(SettingFragment())
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showListProperty() {
        if (viewModel.getIsTablet()){
            replaceFragment(ListPropertyFragment(), DetailFragment())
        } else {
            replaceFragment(ListPropertyFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment, fragmentDetail: Fragment?=null){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment)
        //using in tablet
        if(fragmentDetail!=null){
            fragmentTransaction.replace(R.id.main_FrameLayout_container_detail,fragmentDetail)
        }
        fragmentTransaction.commit()
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResume(resources.getBoolean(R.bool.isTablet))
    }
}