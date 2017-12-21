package io.smily.rxjavadigitas

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import io.smily.rxjavadigitas.MainAdapter.ProductViewHolder
import org.threeten.bp.format.DateTimeFormatter
import kotlin.LazyThreadSafetyMode.NONE

class MainActivity : AppCompatActivity(), TextWatcher {

    private val editText by bindView<EditText>(R.id.searchview)
    private val recyclerView by bindView<RecyclerView>(R.id.recyclerview)
    private val mainAdapter by lazy(NONE) { MainAdapter(this) }

    private val viewModel by lazy(NONE) { ViewModel(SearchRepository.create(this, false)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun afterTextChanged(s: Editable) {
        mainAdapter.setData(viewModel.getProducts(s))
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun onResume() {
        super.onResume()
        editText.addTextChangedListener(this)
    }

    override fun onPause() {
        editText.removeTextChangedListener(this)
        super.onPause()
    }
}

class ViewModel(private val searchRepository: SearchRepository) {

    fun getProducts(text: CharSequence): List<Product> {
        return searchRepository.search(text.toString()).products
    }
}


class MainAdapter(private val context: Context) : RecyclerView.Adapter<ProductViewHolder>() {

    private var products: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_row, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(products[position])

    fun setData(data: List<Product>) {
        products = data.toList()
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val view: View) : ViewHolder(view) {
        private val nameView by view.bindView<TextView>(R.id.product_name)
        private val priceView by view.bindView<TextView>(R.id.product_price)
        private val expirationDate by view.bindView<TextView>(R.id.product_expiration_date)

        fun bind(product: Product) {
            nameView.text = product.name
            priceView.text = "${product.price} €"
            expirationDate.text = product.expirationDate.format(DateTimeFormatter.ISO_TIME)
        }
    }
}

