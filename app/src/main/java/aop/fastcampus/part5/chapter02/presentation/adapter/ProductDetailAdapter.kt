package aop.fastcampus.part5.chapter02.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aop.fastcampus.part5.chapter02.data.entity.product.ProductEntity
import aop.fastcampus.part5.chapter02.databinding.ViewholderProductItemBinding
import aop.fastcampus.part5.chapter02.extensions.loadCenterCrop

class ProductDetailAdapter : RecyclerView.Adapter<ProductDetailAdapter.ToDoItemViewHolder>() {

    private var productList: List<ProductEntity> = listOf()
    private lateinit var productItemClickListener: (ProductEntity) -> Unit

    inner class ToDoItemViewHolder(
        private val binding: ViewholderProductItemBinding,
        val productItemClickListener: (ProductEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ProductEntity) = with(binding) {
            productNameTextView.text = data.productName
            productImageView.loadCenterCrop(data.productImage, 8f)
        }

        fun bindViews(data: ProductEntity) {
            binding.root.setOnClickListener {
                productItemClickListener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = ViewholderProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoItemViewHolder(view, productItemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.bindData(productList[position])
        holder.bindViews(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    fun setProductList(productList: List<ProductEntity>, productItemClickListener: (ProductEntity) -> Unit) {
        this.productList = productList
        this.productItemClickListener = productItemClickListener
        notifyDataSetChanged()
    }
}
