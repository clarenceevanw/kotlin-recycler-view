package com.codelab.c14240069.recyclerview

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.codelab.c14240069.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val _arWayang = ArrayList<DcWayang>()
    private var _nama: Array<String> = arrayOf()
    private var _deskripsi: Array<String> = arrayOf()
    private var _karakter: Array<String> = arrayOf()
    private var _gambar: Array<String> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToggleLayout.setOnCheckedChangeListener { _, isChecked ->
            tampilkanData()
        }

        siapkanData()
        tambahData()
        tampilkanData()
    }

    private fun siapkanData() {
        _nama = resources.getStringArray(R.array.namaWayang)
        _deskripsi = resources.getStringArray(R.array.deskripsiWayang)
        _karakter = resources.getStringArray(R.array.karakterUtamaWayang)
        _gambar = resources.getStringArray(R.array.gambarWayang)
    }

    private fun tambahData() {
        _arWayang.clear()
        for (i in _nama.indices) {
            val wayang = DcWayang (
                foto = _gambar[i],
                nama = _nama[i],
                karakter = _karakter[i],
                deskripsi = _deskripsi[i]
            )
            _arWayang.add(wayang)
        }
    }

    private fun tampilkanData() {
        if (binding.btnToggleLayout.isChecked) {
            binding.rvWayang.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvWayang.layoutManager = StaggeredGridLayoutManager(
                2,
                LinearLayoutManager.VERTICAL
            )
        }

        val adapterWayang = AdapterRecView(_arWayang)
        adapterWayang.setOnItemClickCallback(object : AdapterRecView.OnItemClickCallback {
            override fun onItemClicked(data: DcWayang) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("nama", data.nama)
                    putExtra("karakter", data.karakter)
                    putExtra("deskripsi", data.deskripsi)
                    putExtra("foto", data.foto)
                }
                startActivity(intent)
            }
        })

        binding.rvWayang.adapter = adapterWayang
    }
}