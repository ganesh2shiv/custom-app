package com.custom.app.ui.password.forgot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.base.app.ui.base.BaseDialog
import com.core.app.util.AlertUtil
import com.core.app.util.Util
import com.custom.app.databinding.DialogForgotPwdBinding
import com.custom.app.ui.password.PasswordRequestIntent
import com.custom.app.ui.password.PasswordState
import com.custom.app.ui.password.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@InternalCoroutinesApi
class ForgotPasswordDialog : BaseDialog() {

    private val viewModel: PasswordViewModel by viewModels()

    private var _binding: DialogForgotPwdBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance(): ForgotPasswordDialog {
            return ForgotPasswordDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogForgotPwdBinding.inflate(inflater, container, false)
        binding.btnSubmit.setOnClickListener { view: View? ->
            Util.hideSoftKeyboard(view)
            forgotPassword()
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is PasswordState.Loading -> showProgressBar()
                    is PasswordState.Success -> {
                        hideProgressBar()
                        showMessage(it.msg)
                        showLoginScreen(it.msg)
                    }
                    is PasswordState.Error -> {
                        hideProgressBar()
                        showMessage(it.msg)
                    }
                }
            }
        }
    }

    private fun forgotPassword() {
        lifecycleScope.launch {
            val username = binding.etUsername.text.toString().trim { it <= ' ' }
            viewModel.requestIntent.send(PasswordRequestIntent.Forgot(username))
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.INVISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSubmit.visibility = View.VISIBLE
    }

    override fun showMessage(msg: String?) {
        AlertUtil.showSnackBar(binding.root, msg)
    }

    fun showLoginScreen(msg: String?) {
        AlertUtil.showToast(context(), msg)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}