package com.rama.necflix.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.rama.necflix.databinding.CreateAccountFragmentBinding
import com.rama.necflix.LIST_OF_NAME_RESOURCE_URL
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.Token
import com.rama.necflix.databinding.AccountImagesRowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), CreateAccountAdapter.OnDrawableClickListener {
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    private var _binding: CreateAccountFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var image: String
    lateinit var account: Accounts
    lateinit var token: Token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateAccountFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = LIST_OF_NAME_RESOURCE_URL
        //lo del recyclerview
        setupRecyclerView()
        binding.userImages.adapter = CreateAccountAdapter(requireContext(), data, this)
        //lo de crear cuenta
        binding.btnCreateAccount.setOnClickListener {
            if (binding.username.text != null || binding.password.text != null) {
                createAccount()
                val primaryKey = account.username
                findNavController().navigate(
                    CreateAccountFragmentDirections
                        .actionCreateAccountFragmentToHomeFragment(primaryKey,"0")
                )
            } else {
                Toast.makeText(
                    context, "Ingrese nombre de usuario y contraseña", Toast.LENGTH_LONG
                ).show()
            }
        }


    }

    private fun createAccount() {
        //crar un token
        var requestToken = getTokenNew()
        //autorizar requestToken con login
        token = Token(
            binding.password.text.toString(),
            requestToken,
            binding.username.text.toString()
        )
        var requestTokenValidate = createTokenActivated(token)
        //obtenemos un requestToken autorizado
        //crear una session id y actualizar en room
        val sessionId: String = createSessionId(requestTokenValidate)
        account = Accounts(
            binding.username.text.toString(),
            binding.password.text.toString(),
            requestTokenValidate,
            sessionId,
            image
        )
        //ponemos en room
        viewModel.insertAccountToRoom(account)
    }

    private fun createTokenActivated(getToken: Token): String {
        return viewModel.createTokenActivated(getToken)
    }

    private fun getTokenNew(): String {
        return viewModel.getTokenNew()
    }

    private fun createSessionId(tokenValidate: String): String {
        return viewModel.createSessionId(tokenValidate)
    }


    private fun setupRecyclerView() {
        binding.userImages.layoutManager =
            GridLayoutManager(
                requireContext(),
                4,
                GridLayoutManager.VERTICAL,
                false
            )
        binding.userImages.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun onDrawableClick(
        imgSrc: String,
        position: Int,
        bindingRow: AccountImagesRowBinding
    ) {
        binding.btnCreateAccount.visibility = View.VISIBLE
        image = imgSrc
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.btnCreateAccount.visibility = View.GONE
    }
}