package project_files.project_View_Model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project_files.project_model.UserJSon
import project_files.project_network.OnlineShoppingApi
import project_files.project_network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch

class UserView : ViewModel(){

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _user = MutableLiveData<UserJSon>()
    val user: LiveData<UserJSon> = _user

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserDetails(userId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _user.value = OnlineShoppingApi.retrofitService.getUser(userId.toString())
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("UserViewModel", e.toString())
                }
            }
        }
    }
}