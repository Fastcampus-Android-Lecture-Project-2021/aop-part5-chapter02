package aop.fastcampus.part5.chapter02.presentation.detail

import aop.fastcampus.part5.chapter02.R
import aop.fastcampus.part5.chapter02.databinding.ActivityProductDetailBinding
import aop.fastcampus.part5.chapter02.presentation.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

internal class ProductDetailActivity: BaseActivity<ProductDetailViewModel, ActivityProductDetailBinding>() {

    override fun getViewBinding(): ActivityProductDetailBinding =
        ActivityProductDetailBinding.inflate(layoutInflater)

    override val viewModel by viewModel<ProductDetailViewModel>()

    override fun observeData() {

    }

    private fun initViews() = with(binding) {

    }

}
