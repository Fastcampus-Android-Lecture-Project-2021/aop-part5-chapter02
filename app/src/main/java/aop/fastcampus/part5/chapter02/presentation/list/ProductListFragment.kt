package aop.fastcampus.part5.chapter02.presentation.list

import android.widget.Toast
import androidx.core.view.isGone
import aop.fastcampus.part5.chapter02.databinding.FragmentProductListBinding
import aop.fastcampus.part5.chapter02.presentation.BaseFragment
import aop.fastcampus.part5.chapter02.presentation.adapter.ProductListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

internal class ProductListFragment : BaseFragment<ProductListViewModel, FragmentProductListBinding>() {

    companion object {
        const val TAG = "ProductListFragment"
    }

    override fun getViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

    private val adapter = ProductListAdapter()

    override val viewModel by viewModel<ProductListViewModel>()

    private fun initViews(binding: FragmentProductListBinding) = with(binding) {
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

    }

    override fun observeData() {
        viewModel.productListStateLiveData.observe(this) {
            when (it) {
                is ProductListState.UnInitialized -> {
                    initViews(binding)
                }
                is ProductListState.Loading -> {
                    handleLoadingState()
                }
                is ProductListState.Success -> {
                    handleSuccessState(it)
                }
                is ProductListState.Error -> {
                    handleErrorState()
                }
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ProductListState.Success) = with(binding) {
        refreshLayout.isEnabled = state.productList.isNotEmpty()
        refreshLayout.isRefreshing = false

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                Toast.makeText(requireContext(), "아이템 클릭 : $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleErrorState() {
        Toast.makeText(requireContext(), "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

}
