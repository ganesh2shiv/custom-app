package com.custom.app.ui.logout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.base.app.ui.base.BaseDialog
import com.core.app.util.AlertUtil
import com.custom.app.databinding.DialogLogoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@InternalCoroutinesApi
class LogoutDialog : BaseDialog() {

    private var callback: Callback? = null

    private val viewModel: LogoutViewModel by viewModels()

    private var _binding: DialogLogoutBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance(): LogoutDialog {
            return LogoutDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogLogoutBinding.inflate(inflater, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        observeViewModel()

        binding.btnYes.setOnClickListener { logout() }
        binding.btnNo.setOnClickListener { dismiss() }

        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is LogoutState.Loading -> showProgressBar()
                    is LogoutState.Success -> {
                        hideProgressBar()
                        showMessage(it.msg)
                        showLoginScreen()
                    }
                    is LogoutState.Error -> {
                        hideProgressBar()
                        showMessage(it.msg)
                    }
                }
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            viewModel.requestIntent.send(LogoutRequestIntent.Logout)
        }
    }

    override fun showMessage(msg: String?) {
        AlertUtil.showToast(context, msg)
    }

    fun showLoginScreen() {
        if (callback != null) {
            callback!!.showLoginScreen()
        }
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    interface Callback {
        fun showLoginScreen()
    }
}