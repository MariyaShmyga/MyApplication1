package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dialoglibrary.RubberEffectDialog
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

//        binding.buyButton.setOnClickListener {
//            if (cartViewModel.cartItems.value.isNullOrEmpty()) {
//                Toast.makeText(requireContext(), "Корзина пуста", Toast.LENGTH_SHORT).show()
//            } else {
//                cartViewModel.clearCart()
//                Toast.makeText(requireContext(), "Поздравляем с покупкой!", Toast.LENGTH_LONG).show()
//            }
//        }
        // Обработка нажатия кнопки "Купить"
        binding.buyButton.setOnClickListener {
            val cartItems = cartViewModel.cartItems.value ?: emptyList()
            if (cartItems.isNotEmpty()) {
//                Toast.makeText(requireContext(), "Поздравляем с покупкой!", Toast.LENGTH_SHORT).show()
//                cartViewModel.clearCart()
                // Показываем диалоговое окно с поздравлением
                val dialog = RubberEffectDialog.newInstance(
                    title = "Поздравляем!",
                    message = "Вы успешно совершили покупку. Спасибо, что выбрали нас!",
                    buttontext = "Отлично! Продолжить покупки",
                    onConfirm = {
                        cartViewModel.clearCart() // Очищаем корзину после подтверждения
                    }
                )
                dialog.show(parentFragmentManager, "PurchaseDialog")
            } else {
                Toast.makeText(requireContext(), "Ваша корзина пуста", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}