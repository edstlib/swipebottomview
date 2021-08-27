package id.co.edtslib.swipebottomview.example

import id.co.edtslib.baserecyclerview.BaseRecyclerViewAdapterDelegate
import id.co.edtslib.baserecyclerview.BaseViewHolder
import id.co.edtslib.swipebottomview.example.databinding.AdapterTestBinding

class TestHolder(private val binding: AdapterTestBinding): BaseViewHolder<String>(binding) {
    override fun setData(
        list: MutableList<String>,
        position: Int,
        delegate: BaseRecyclerViewAdapterDelegate<String>?
    ) {
        binding.textView.text = list[position]
    }
}