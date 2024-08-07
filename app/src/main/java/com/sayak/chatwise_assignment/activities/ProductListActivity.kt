package com.sayak.chatwise_assignment.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sayak.chatwise_assignment.adapters.ProductListAdapter
import com.sayak.chatwise_assignment.databinding.ActivityProductListBinding
import com.sayak.chatwise_assignment.interfaces.ApiInterface
import com.sayak.chatwise_assignment.models.ApiResponse
import com.sayak.chatwise_assignment.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.awaitResponse

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var progressDialog: ProgressDialog
    private var productList: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing variables
        recyclerView = binding.rvListItem
        recyclerView.layoutManager = LinearLayoutManager(this)
        productListAdapter = ProductListAdapter(productList)
        recyclerView.adapter = productListAdapter

        initializeProgressDialog()
        fetchProducts()
    }

    private fun fetchProducts() {
        lifecycleScope.launch {
            try {
                progressDialog.show()
                val response = fetchProductsFromApi()
                if (response.isSuccessful) {
                    Log.d("RESPONSE", "Response -> ${response.body()?.products}")
                    productList.clear()
                    productList.addAll(response.body()?.products ?: emptyList())
                    Log.d("PRODUCT LIST", "Product List -> $productList")
                    productListAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                handleException(e)
            } finally {
                progressDialog.dismiss()
            }
        }
    }

    private suspend fun fetchProductsFromApi(): Response<ApiResponse<List<Product>>> {
        return withContext(Dispatchers.IO) {
            val retrofit = createRetrofitInstance()
            retrofit.getProducts().awaitResponse()
        }
    }

    private fun createRetrofitInstance(): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ApiInterface::class.java)
    }

    private fun handleException(e: Exception) {
        Toast.makeText(this, "INTERNAL ERROR -> ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("CatchException", e.message.toString())
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this).apply {
            setMessage("Loading products...")
            setCancelable(false)
        }
    }
}
