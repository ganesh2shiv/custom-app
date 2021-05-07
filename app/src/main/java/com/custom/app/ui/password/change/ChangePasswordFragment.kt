package com.custom.app.ui.password.change

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.base.app.ui.base.BaseFragment
import com.core.app.util.AlertUtil
import com.core.app.util.Util
import com.custom.app.R
import com.custom.app.databinding.FragmentChangePwdBinding
import com.custom.app.ui.logout.LogoutDialog
import com.custom.app.ui.password.PasswordRequestIntent
import com.custom.app.ui.password.PasswordState
import com.custom.app.ui.password.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@InternalCoroutinesApi
class ChangePasswordFragment : BaseFragment() {

    private var callback: LogoutDialog.Callback? = null

    private val viewModel: PasswordViewModel by viewModels()

    private var _binding: FragmentChangePwdBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): ChangePasswordFragment {
            return ChangePasswordFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as LogoutDialog.Callback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentChangePwdBinding.inflate(inflater, container, false)

        observeViewModel()

        binding.btnCancel.setOnClickListener { view: View? -> fragmentManager?.popBackStackImmediate() }

        binding.btnSave.setOnClickListener { view: View? ->
            Util.hideSoftKeyboard(view)
            AlertUtil.showActionAlertDialog(context(), "Alert",
                    "Once the password is changed successfully, you will need to login again. Still want to proceed?",
                    getString(R.string.btn_no), getString(R.string.btn_yes)) { dialog: DialogInterface?, which: Int -> changePassword() }
        }

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
                        showLoginScreen()
                    }
                    is PasswordState.Error -> {
                        hideProgressBar()
                        showMessage(it.msg)
                    }
                }
            }
        }
    }

    private fun changePassword() {
        lifecycleScope.launch {
            val oldPwd = binding.etOldPwd.text.toString().trim { it <= ' ' }
            val newPwd = binding.etNewPwd.text.toString().trim { it <= ' ' }
            viewModel.requestIntent.send(PasswordRequestIntent.Change(oldPwd, newPwd))
        }
    }

    override fun showMessage(msg: String?) {
        AlertUtil.showToast(context(), msg)
    }

    fun showLoginScreen() {
        if (callback != null) {
            callback!!.showLoginScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }
}