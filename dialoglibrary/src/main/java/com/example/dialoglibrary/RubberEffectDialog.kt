package com.example.dialoglibrary

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.dialoglibrary.databinding.DialogRubberEffectBinding

class RubberEffectDialog : DialogFragment() {

    companion object {
        fun newInstance(
            title: String,
            message: String,
            buttontext: String,
            onConfirm: () -> Unit,
            onCancel: (() -> Unit)? = null
        ): RubberEffectDialog {
            val dialog = RubberEffectDialog()
            val args = Bundle()
            args.putString("TITLE", title)
            args.putString("MESSAGE", message)
            args.putString("BUTTONTEXT", buttontext)
            dialog.arguments = args
            dialog.onConfirm = onConfirm
            dialog.onCancel = onCancel
            return dialog
        }
    }

    private var onConfirm: (() -> Unit)? = null
    private var onCancel: (() -> Unit)? = null
    private var onCloseAction: (() -> Unit)? = null

    private var _binding: DialogRubberEffectBinding? = null
    private val binding get() = _binding!!

    private lateinit var springAnimationX: SpringAnimation
    private lateinit var springAnimationY: SpringAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog)
        isCancelable = false // Диалог больше не закрывается при клике за пределами
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogRubberEffectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем текст из аргументов
        val title = arguments?.getString("TITLE") ?: "Заголовок"
        val message = arguments?.getString("MESSAGE") ?: "Сообщение"
        val buttontext = arguments?.getString("BUTTONTEXT") ?: "Текст"

        binding.dialogTitle.text = title
        binding.helpText.text = message
        binding.closeButton.text = buttontext

        // Настраиваем анимации
        setupSpringAnimations(view)

        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    springAnimationX.cancel()
                    springAnimationY.cancel()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Двигаем view в зависимости от жеста
                    v.translationX = event.rawX - v.width / 2f
                    v.translationY = event.rawY - v.height / 2f
                    true
                }
                MotionEvent.ACTION_UP -> {
                    // Сообщаем системе, что клик обработан
                    v.performClick()

                    // Запускаем возврат в исходное положение
                    springAnimationX.start()
                    springAnimationY.start()
                    true
                }
                else -> false
            }
        }

        // Закрытие диалога по кнопке "Закрыть"
        binding.closeButton.setOnClickListener {
            onConfirm?.invoke()
            dismiss()
        }

//        // Обработчик кнопки "Отмена" (если нужно)
//        binding.cancelButton.setOnClickListener {
//            onCancel?.invoke()
//            dismiss()
//        }
    }

    private fun setupSpringAnimations(view: View) {
        springAnimationX = SpringAnimation(view, DynamicAnimation.TRANSLATION_X).apply {
            spring = SpringForce().apply {
                stiffness = SpringForce.STIFFNESS_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                finalPosition = 0f
            }
        }
        springAnimationY = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).apply {
            spring = SpringForce().apply {
                stiffness = SpringForce.STIFFNESS_LOW
                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                finalPosition = 0f
            }
        }
    }
    fun setOnCloseAction(action: () -> Unit) {
        onCloseAction = action
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




//package com.example.dialoglibrary
//
//import android.app.Dialog
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.DialogFragment
//import androidx.dynamicanimation.animation.DynamicAnimation
//import androidx.dynamicanimation.animation.SpringAnimation
//import androidx.dynamicanimation.animation.SpringForce
//import com.example.dialoglibrary.databinding.DialogRubberEffectBinding
//
//class RubberEffectDialog : DialogFragment() {
//
//    companion object {
//        fun newInstance(title: String, message: String, onConfirm: () -> Unit): RubberEffectDialog {
//            val dialog = RubberEffectDialog()
//            val args = Bundle()
//            args.putString("TITLE", title)
//            args.putString("MESSAGE", message)
//            dialog.arguments = args
//            dialog.onConfirm = onConfirm
//            return dialog
//        }
//    }
//
//    private var onConfirm: (() -> Unit)? = null
//
//    private var _binding: DialogRubberEffectBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var springAnimationX: SpringAnimation
//    private lateinit var springAnimationY: SpringAnimation
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Устанавливаем стиль для модального диалога
//        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog)
////        setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
//
//
//        isCancelable = false // Диалог больше не закрывается при клике за пределами
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = DialogRubberEffectBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.window?.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        return dialog
//    }
//
//    override fun onStart() {
//        super.onStart()
//        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val title = arguments?.getString("TITLE") ?: "Заголовок"
//        val message = arguments?.getString("MESSAGE") ?: "Сообщение"
//
//        binding.dialogTitle.text = title
//        binding.helpText.text = message
//
//
//        // Настраиваем анимации
//        setupSpringAnimations(view)
//
//        view.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    springAnimationX.cancel()
//                    springAnimationY.cancel()
//                    true
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    // Двигаем view в зависимости от жеста
//                    v.translationX = event.rawX - v.width / 2f
//                    v.translationY = event.rawY - v.height / 2f
//                    true
//                }
//                MotionEvent.ACTION_UP -> {
//                    // Сообщаем системе, что клик обработан
//                    v.performClick()
//
//                    // Запускаем возврат в исходное положение
//                    springAnimationX.start()
//                    springAnimationY.start()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        // Закрытие диалога по кнопке
//        binding.closeButton.setOnClickListener {
//            dismiss()
//        }
//    }
//
//    private fun setupSpringAnimations(view: View) {
//        springAnimationX = SpringAnimation(view, DynamicAnimation.TRANSLATION_X).apply {
//            spring = SpringForce().apply {
//                stiffness = SpringForce.STIFFNESS_LOW
//                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
//                finalPosition = 0f
//            }
//        }
//        springAnimationY = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).apply {
//            spring = SpringForce().apply {
//                stiffness = SpringForce.STIFFNESS_LOW
//                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
//                finalPosition = 0f
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
