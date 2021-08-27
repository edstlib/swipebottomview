package id.co.edtslib.swipebottomview.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.swipebottomview.example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = TestAdapter()
        adapter.list = mutableListOf("1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
            "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28")
        adapter.delegate = object : BaseRecyclerViewAdapterDelegate<String> {

            override fun onClick(t: String, position: Int, holder: BaseViewHolder<String>?) {
                Toast.makeText(this@MainActivity, "Hai $t", Toast.LENGTH_SHORT).show()
            }

            override fun onDraw(t: String, position: Int) {
                // tracker send
            }
        }

        val v = binding.swipeBottomView.contentView

        val recyclerView = v?.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
    }
}