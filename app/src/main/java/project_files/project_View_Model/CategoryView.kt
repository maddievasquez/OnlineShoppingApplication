package project_files.project_View_Model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project_files.project_model.CategoryJSon
import project_files.project_network.OnlineShoppingApi
import project_files.project_network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch

class CategoryView : ViewModel() {
    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _categories = MutableLiveData<List<CategoryJSon>>()
    val categories: LiveData<List<CategoryJSon>> = _categories

    private val _category = MutableLiveData<CategoryJSon>()
    val category: LiveData<CategoryJSon> = _category

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCategoriesList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _categories.value =
                        convertToCategoryList(OnlineShoppingApi.retrofitService.getCategories())
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    _categories.value = listOf()
                    Log.d("CategoryViewModel", e.toString())
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToCategoryList(categories: List<String>): List<CategoryJSon> {
        var i = 1
        val categoriesList = mutableListOf<CategoryJSon>()

        categories.forEach {
            categoriesList.add(CategoryJSon(i, it))
            i++
        }

        return categoriesList
    }

    fun onCategoryClicked(category: CategoryJSon): String {
        _category.value = category

        return _category.value!!.name
    }
}