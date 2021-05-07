package com.custom.app.ui.login

import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.base.app.ui.base.BaseActivity
import com.core.app.BuildConfig
import com.core.app.util.ActivityUtil
import com.core.app.util.AlertUtil
import com.core.app.util.Util
import com.custom.app.R
import com.custom.app.databinding.ActivityLoginBinding
import com.custom.app.ui.home.HomeActivity
import com.custom.app.ui.password.forgot.ForgotPasswordDialog.Companion.newInstance
import com.custom.app.util.Constants
import com.google.gson.Gson
import com.network.app.model.login.LoginResponse
import com.network.app.oauth.AccountConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
@InternalCoroutinesApi
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var accountManager: AccountManager

    private var resultBundle: Bundle? = null
    private var authTokenType: String? = null
    private var authenticatorResponse: AccountAuthenticatorResponse? = null

    private val viewModel: LoginViewModel by viewModels()

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE)

        if (authenticatorResponse != null) {
            authenticatorResponse!!.onRequestContinued()
        }

        authTokenType = intent.getStringExtra(AccountConstants.KEY_TOKEN_TYPE)

        if (authTokenType == null) {
            authTokenType = AccountConstants.TYPE_FULL_ACCESS
        }

        observeViewModel()

        binding.includeFab.fabGhost.setOnClickListener { view: View? -> showGhostMenu(view) }
        binding.title.setOnLongClickListener { view: View? ->
            showHideGhostFab()
            true
        }

        binding.btnLogin.setOnClickListener { view: View? ->
            Util.hideSoftKeyboard(view)
            login()
        }

        binding.tvForgotPwd.setOnClickListener { view: View? -> newInstance().show(supportFragmentManager, Constants.FORGOT_PWD_DIALOG) }
        binding.tvSignup.setOnClickListener { view: View? -> Util.handleUrl(this, "mailto:admin@custom.com") }
        binding.includeFab.fabGhost.setOnClickListener { view: View? -> showGhostMenu(view) }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is LoginState.Loading -> showProgressBar()
                    is LoginState.Success -> {
                        hideProgressBar()
                        showHomeScreen(it.response)
                    }
                    is LoginState.Error -> {
                        hideProgressBar()
                        showMessage(it.msg)
                    }
                }
            }
        }
    }

    private fun login() {
        lifecycleScope.launch {
            val username = binding.etUsername.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }
            viewModel.requestIntent.send(LoginRequestIntent.Login(username, password))
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnLogin.visibility = View.INVISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnLogin.visibility = View.VISIBLE
    }

    override fun showMessage(msg: String?) {
        AlertUtil.showSnackBar(binding.layoutMain, msg)
    }

    fun showHomeScreen(response: LoginResponse?) {
        addOrUpdateAccount(response)
        if (!userManager.isLoggedIn) {
            val user = response?.userData
            userManager.login(Gson().toJson(user))
            userManager.userId = user?.id
            userManager.username = user?.username
            userManager.profilePath = user?.profile
            ActivityUtil.startActivity(this, HomeActivity::class.java, true)
        } else {
            finish()
        }
    }

    fun addOrUpdateAccount(response: LoginResponse?) {
        Timber.d("Add or update account...")
        val accountName = response?.userData?.username
        val account = Account(accountName, getString(R.string.account_type))
        val tokenExpireInMillis = Instant.now().toEpochMilli() + AccountConstants.TOKEN_EXPIRE_IN_MILLIS
        val data = Bundle()
        data.putString(AccountManager.KEY_ACCOUNT_NAME, accountName)
        data.putString(AccountManager.KEY_ACCOUNT_TYPE, getString(R.string.account_type))
        data.putString(AccountConstants.KEY_REFRESH_TOKEN, response?.refreshToken)
        data.putString(AccountConstants.KEY_TOKEN_EXPIRE_IN, tokenExpireInMillis.toString())
        accountManager.addAccountExplicitly(account, null, data)
        accountManager.setAuthToken(account, authTokenType, response?.token)
        resultBundle = data
    }

    override fun finish() {
        if (authenticatorResponse != null) {
            if (resultBundle != null) {
                authenticatorResponse!!.onResult(resultBundle)
            } else {
                authenticatorResponse!!.onError(AccountManager.ERROR_CODE_CANCELED, "Canceled")
            }
            authenticatorResponse = null
        }
        super.finish()
    }

    private fun showHideGhostFab(): Boolean {
        if (BuildConfig.DEBUG) {
            if (binding.includeFab.fabGhost.visibility == View.VISIBLE) {
                binding.includeFab.fabGhost.hide()
            } else {
                binding.includeFab.fabGhost.show()
            }
        }
        return true
    }
}