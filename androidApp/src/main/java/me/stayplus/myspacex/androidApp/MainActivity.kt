package me.stayplus.myspacex.androidApp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.stayplus.myspacex.androidApp.databinding.ActivityMainBinding
import me.stayplus.myspacex.shared.SpaceXSDK
import me.stayplus.myspacex.shared.cache.DatabaseDriverFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainScope = MainScope()

    private val sdk = SpaceXSDK(DatabaseDriverFactory(this))
    private val adapter = LaunchesAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTitle(R.string.space_x_launches)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.apply {
                launches = listOf()
                notifyDataSetChanged()
            }
            displayLaunches(true)
        }

        displayLaunches(false)
    }

    private fun displayLaunches(forceReload: Boolean) {
        binding.progressBar.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                sdk.getLaunches(forceReload)
            }.onSuccess {
                adapter.apply {
                    launches = it
                    notifyDataSetChanged()
                }
            }.onFailure {
                Toast.makeText(
                    this@MainActivity,
                    it.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.progressBar.isVisible = false
        }
    }
}
