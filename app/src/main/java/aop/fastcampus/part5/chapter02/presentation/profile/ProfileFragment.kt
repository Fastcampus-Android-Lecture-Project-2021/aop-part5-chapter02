package aop.fastcampus.part5.chapter02.presentation.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import aop.fastcampus.part5.chapter02.R
import aop.fastcampus.part5.chapter02.databinding.FragmentProfileBinding
import aop.fastcampus.part5.chapter02.extensions.loadCenterCrop
import aop.fastcampus.part5.chapter02.presentation.BaseFragment
import aop.fastcampus.part5.chapter02.presentation.adapter.ProductListAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception


internal class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    companion object {
        const val TAG = "ProfileFragment"
    }

    override fun getViewBinding(): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override val viewModel by viewModel<ProfileViewModel>()

    private val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val gsc by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                viewModel.saveToken(account.idToken ?: throw Exception())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val adapter = ProductListAdapter()

    private fun initViews(binding: FragmentProfileBinding) = with(binding) {
        loginButton.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent: Intent = gsc.signInIntent
        launcher.launch(signInIntent)
    }

    override fun observeData() {
        viewModel.profileStateLiveData.observe(this) {
            when (it) {
                is ProfileState.UnInitialized -> {
                    initViews(binding)
                }
                is ProfileState.Loading -> {
                    handleLoadingState()
                }
                is ProfileState.Login -> {
                    handleLogin(it)
                }
                is ProfileState.Success -> {
                    handleSuccessState(it)
                }
                is ProfileState.Error -> {
                    handleErrorState()
                }
            }
        }
    }

    private fun handleLoadingState() = with(binding) {
        progressBar.isVisible = true
    }

    private fun handleLogin(state: ProfileState.Login) {
        binding.progressBar.isVisible = true
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    viewModel.setUserInfo(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun handleSuccessState(state: ProfileState.Success) = with(binding) {
        progressBar.isVisible = false
        when (state) {
            is ProfileState.Success.Registered -> {
                handleRegisteredState(state)
            }
            is ProfileState.Success.NotRegistered -> {
                loginRequiredGroup.isVisible = true
            }
        }
    }

    private fun handleRegisteredState(state: ProfileState.Success.Registered) = with(binding) {
        loginRequiredGroup.isVisible = false
        profileImageView.loadCenterCrop(state.profileImgUri.toString(), 60f)
        userNameTextView.text = state.userName

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
