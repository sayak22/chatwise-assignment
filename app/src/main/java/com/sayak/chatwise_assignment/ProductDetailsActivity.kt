package com.sayak.chatwise_assignment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sayak.chatwise_assignment.databinding.ActivityProductDetailsBinding
import com.sayak.chatwise_assignment.models.Product

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val product = intent.getParcelableExtra<Product>("product")

        product?.let {
            Glide.with(this)
                .load(it.thumbnail)
                .into(binding.productThumbnail)

            binding.productTitle.text = it.title
            binding.productCategory.text = it.category
            binding.productDescription.text = it.description
            binding.productPrice.text = "Price: $${it.price}"
            binding.productDiscountPercentage.text = "Discount: ${it.discountPercentage}%"
            binding.productRating.text = "Rating: ${it.rating}"
            binding.productStock.text = "Stock: ${it.stock}"
        }


    }
}