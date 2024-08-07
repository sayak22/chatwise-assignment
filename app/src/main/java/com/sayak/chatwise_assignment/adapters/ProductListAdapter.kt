package com.sayak.chatwise_assignment.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayak.chatwise_assignment.ProductDetailsActivity
import com.sayak.chatwise_assignment.R
import com.sayak.chatwise_assignment.models.Product

class ProductListAdapter(private var products: MutableList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            productTitle.text = product.title
            Glide.with(itemView).load(product.thumbnail).into(productImage)
            var word: Int = 0
            var noOfChar: Int = 0;
            for (i in product.description) {
                if (word == 10 && i.isWhitespace())
                    break
                if (i.isWhitespace()) word++
                noOfChar++
            }
            productDescription.text = product.description.substring(0, noOfChar) + "..."
        }

        private val productImage = itemView.findViewById<ImageView>(R.id.productImage)
        private val productTitle = itemView.findViewById<TextView>(R.id.productTitle)
        private val productDescription = itemView.findViewById<TextView>(R.id.productDescription)

        init {
            itemView.setOnClickListener {
                val product = products[adapterPosition]

                val intent = Intent(itemView.context, ProductDetailsActivity::class.java)

                intent.putExtra("product", product)
                itemView.context.startActivity(intent)
            }
        }
    }
}
