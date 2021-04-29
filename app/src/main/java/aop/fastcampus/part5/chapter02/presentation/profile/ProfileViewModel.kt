package aop.fastcampus.part5.chapter02.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import aop.fastcampus.part5.chapter02.data.preference.PreferenceManager
import aop.fastcampus.part5.chapter02.presentation.BaseViewModel
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class ProfileViewModel(
    private val preferenceManager: PreferenceManager
): BaseViewModel() {

    private var _profileStateLiveData = MutableLiveData<ProfileState>(ProfileState.UnInitialized)
    val profileStateLiveData: LiveData<ProfileState> = _profileStateLiveData

    override fun fetchData(): Job = viewModelScope.launch {
        setState(
            ProfileState.Loading
        )
        preferenceManager.getIdToken()?.let {
            setState(
                ProfileState.Login(it)
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }
    }

    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch {
        firebaseUser?.let { user ->
            setState(
                ProfileState.Success.Registered(
                    user.displayName ?: "익명",
                    user.photoUrl
                )
            )
        } ?: kotlin.run {
            setState(
                ProfileState.Success.NotRegistered
            )
        }

    }

    fun saveToken(idToken: String) {
        preferenceManager.putIdToken(idToken)
        fetchData()
    }

    private fun setState(state: ProfileState) {
        _profileStateLiveData.postValue(state)
    }

}
