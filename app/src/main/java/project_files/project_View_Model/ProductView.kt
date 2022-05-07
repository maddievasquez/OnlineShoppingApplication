package project_files.project_View_Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project_files.project_model.ProductJSon
import project_files.project_network.OnlineShoppingApi
import project_files.project_network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch

class ProductView : ViewModel(){
    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _products = MutableLiveData<List<ProductJSon>>()
    val products: LiveData<List<ProductJSon>> = _products

    private val _product = MutableLiveData<ProductJSon>()
    val product: LiveData<ProductJSon> = _product

    fun getProductsList(categoryName: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _products.value = OnlineShoppingApi.retrofitService.getProducts(categoryName)
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("ProductViewModel", e.toString())
                    _products.value = listOf()
                }
            }
        }
    }

    fun getProductsList(productId: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _product.value = OnlineShoppingApi.retrofitService.getProducts().find {
                        it.id == productId
                    }
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("ProductViewModel", e.toString())
                }
            }
        }
    }

    fun onProductClicked(product: ProductJSon) {
        _product.value = product
    }
}