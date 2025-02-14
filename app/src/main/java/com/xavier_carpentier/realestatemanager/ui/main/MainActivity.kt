package com.xavier_carpentier.realestatemanager.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.xavier_carpentier.realestatemanager.ui.compose.navigation.NavigationScreen
import com.xavier_carpentier.realestatemanager.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent{
            AppTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                NavigationScreen(viewModel, windowSizeClass)
            }
        }

    }

}

/*@AndroidEntryPoint
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
}*/