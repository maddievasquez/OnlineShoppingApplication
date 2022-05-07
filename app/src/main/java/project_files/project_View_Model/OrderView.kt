package project_files.project_View_Model

import android.icu.text.NumberFormat
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import project_files.project_model.CartJSon
import project_files.project_model.CartProductJson
import project_files.project_model.ProductJSon
import project_files.project_model.SessionObject.userId
import project_files.project_network.OnlineShoppingApi
import project_files.project_network.OnlineShoppingApiStatus
import kotlinx.coroutines.launch
import androidx.annotation.RequiresApi as RequiresApi1

class OrderView : ViewModel() {

    private val _status = MutableLiveData<OnlineShoppingApiStatus>()
    val status: LiveData<OnlineShoppingApiStatus> = _status

    private val _orders = MutableLiveData<List<CartJSon>>()
    val orders: LiveData<List<CartJSon>> = _orders

    private val _order = MutableLiveData<CartJSon>()
    val order: LiveData<CartJSon> = _order

    private val _orderProducts = MutableLiveData<List<CartProductJson>>()
    private val orderProducts: LiveData<List<CartProductJson>> = _orderProducts

    private val _orderProductsDetails = MutableLiveData<List<ProductJSon>>()
    val orderProductsDetails: LiveData<List<ProductJSon>> = _orderProductsDetails

    private val _orderProductDetails = MutableLiveData<ProductJSon>()
    val orderProductDetails: LiveData<ProductJSon> = _orderProductDetails

    private val _orderedQuantities = MutableLiveData<List<Int>>()
    private val orderedQuantities: LiveData<List<Int>> = _orderedQuantities

    private val _orderedPrices = MutableLiveData<List<Double>>()
    private val orderedPrices: LiveData<List<Double>> = _orderedPrices

    private val _totalAmount = MutableLiveData<Double>()
    private val totalAmount: LiveData<Double> = _totalAmount

    private val _totalAmountFormatted = MutableLiveData<String>()
    val totalAmountFormatted: LiveData<String> = _totalAmountFormatted

    fun getOrdersList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _orders.value =
                        OnlineShoppingApi.retrofitService.getCarts(userId.toString()).dropLast(1)
                    _status.value = OnlineShoppingApiStatus.DONE
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("OrderViewModel", e.toString())
                    _orders.value = listOf()
                }
            }
        }
    }

    @RequiresApi1(Build.VERSION_CODES.N)
    fun getOrderProductsList(order: CartJSon) {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _orderProducts.value = order.products
                    _status.value = OnlineShoppingApiStatus.DONE
                    getOrderProductsDetailsList()
                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("OrderViewModel", e.toString())
                    _orderProducts.value = listOf()
                }
            }
        }
    }

    @RequiresApi1(Build.VERSION_CODES.N)
    private fun getOrderProductsDetailsList() {
        viewModelScope.launch {
            viewModelScope.launch {
                _status.value = OnlineShoppingApiStatus.LOADING
                try {
                    _orderProductsDetails.value = OnlineShoppingApi.retrofitService.getProducts()
                        .filter {
                            orderProducts.value?.map { orderProduct -> orderProduct.productId }
                                ?.contains(it.id) ?: false
                        }
                    _status.value = OnlineShoppingApiStatus.DONE

                    calculateTotalAmount()
                    _totalAmountFormatted.value =
                        NumberFormat.getInstance(NumberFormat.CURRENCYSTYLE)
                            .format(totalAmount.value)

                } catch (e: Exception) {
                    _status.value = OnlineShoppingApiStatus.ERROR
                    Log.d("OrderViewModel", e.toString())
                    _orderProductsDetails.value = listOf()
                }
            }
        }
    }

    @RequiresApi1(Build.VERSION_CODES.N)
    private fun calculateTotalAmount() {
        _orderedPrices.value = orderProductsDetails.value?.sortedBy { it.id }?.map { it.price }
        _orderedQuantities.value =
            orderProducts.value?.sortedBy { it.productId }?.map { it.quantity }

        _totalAmount.value = orderedPrices.value?.zip(orderedQuantities.value!!) { op, oq ->
            op * oq
        }?.reduce { acc, d ->
            acc + d
        }
    }

    @RequiresApi1(Build.VERSION_CODES.N)
    fun getProductPriceQuantityById(id: Int): String {
        return NumberFormat.getInstance(NumberFormat.CURRENCYSTYLE)
            .format((orderProductsDetails.value?.find {
                it.id == id
            }?.price ?: 0.0) * getQuantityById(id))
    }

    fun getQuantityById(id: Int): Int {
        return orderProducts.value?.find {
            it.productId == id
        }?.quantity ?: 0
    }

    fun onOrderClicked(order: CartJSon): CartJSon {
        _order.value = order
        return order
    }

    fun onProductClicked(product: ProductJSon) {
        _orderProductDetails.value = product
    }

    fun onCheckOut(order: CartJSon) {
        _order.value = order
    }
}