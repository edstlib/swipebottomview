package id.co.edtslib.swipebottomview.example

import android.view.LayoutInflater
import android.view.ViewGroup
import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapter
import id.co.edtslib.swipebottomview.example.databinding.AdapterTestBinding

class TestAdapter: BaseRecyclerViewAdapter<AdapterTestBinding, String>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterTestBinding
        get() = AdapterTestBinding::inflate

    override fun createHolder() = TestHolder(binding)
}