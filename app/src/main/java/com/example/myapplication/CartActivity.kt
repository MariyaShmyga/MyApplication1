package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val cartAdapter = CartAdapter()
        binding.recyclerView.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.submitList(items.toList())
            binding.totalPrice.text = "${cartViewModel.getTotalPrice()} USD"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



//package com.example.myapplication
//
//import android.app.Application
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import androidx.activity.viewModels
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.myapplication.databinding.ActivityCartBinding
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class CartViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
//            return CartViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//
//class CartActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityCartBinding
//    private val cartViewModel: CartViewModel by lazy {
//        ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory(application)
//        ).get(CartViewModel::class.java)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Правильная инициализация binding
//        binding = ActivityCartBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val cartAdapter = CartAdapter()
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = cartAdapter
//
//        // Наблюдаем за изменениями в корзине
//        cartViewModel.cartItems.observe(this) { items ->
//            cartAdapter.submitList(items.toList())
//            binding.totalPrice.text = "Total: ${cartViewModel.getTotalPrice()} USD"
//        }
//
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigationView.selectedItemId = R.id.nav_cart // Укажите текущий экран
//
//        bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    if (this !is MainActivity) {
//                        startActivity(Intent(this, MainActivity::class.java))
//                        finish()
//                    }
//                    true
//                }
//                R.id.nav_cart -> {
//                    // Текущий экран, ничего не делаем
//                    true
//                }
//                R.id.nav_profile -> {
//                    if (this !is ProfileActivity) {
//                        startActivity(Intent(this, ProfileActivity::class.java))
//                        finish()
//                    }
//                    true
//                }
//                R.id.nav_settings -> {
//                    if (this !is SettingsActivity) {
//                        startActivity(Intent(this, SettingsActivity::class.java))
//                        finish()
//                    }
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}
