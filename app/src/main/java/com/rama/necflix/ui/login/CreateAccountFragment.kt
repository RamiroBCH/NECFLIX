package com.rama.necflix.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.rama.necflix.databinding.CreateAccountFragmentBinding
import com.rama.necflix.LIST_OF_NAME_RESOURCE_URL
import com.rama.necflix.R
import com.rama.necflix.data.Accounts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), CreateAccountAdapter.OnDrawableClickListener {
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    private var _binding: CreateAccountFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var image: String
    lateinit var account: Accounts

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
        viewModel.insertDrawableName(LIST_OF_NAME_RESOURCE_URL)
        val data = LIST_OF_NAME_RESOURCE_URL
        //lo del recyclerview
        setupRecyclerView()
        binding.userImages.adapter = CreateAccountAdapter(requireContext(),data,this)
        //lo de crear cuenta
        binding.btnCreateAccount.setOnClickListener {
            if (binding.username.text != null ) {
                createAccount()
                val primaryKey = account.username
                findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToHomeFragment(primaryKey))
            } else {
                Toast.makeText(
                    context, "Ingrese nombre de usuario y contraseña", Toast.LENGTH_LONG
                ).show()
            }
        }


    }

    private fun createAccount() {
        //crar un token
        val requestToken: String = "wqeqwem12k3o132"
        //autorizar requestToken con login
        account = Accounts(
            binding.username.text.toString(),
            binding.password.text.toString(),
            requestToken,
            "admin",
            image
        )
        //obtenemos un requestToken autorizado
        //crear una session id y actualizar en room
        val sessionId: String = "2314134123"
        account = Accounts(
            binding.username.text.toString(),
            binding.password.text.toString(),
            requestToken,
            sessionId,
            image
        )
        //ponemos en room
        viewModel.insertAccountToRoom(account)
    }


    private fun setupRecyclerView(){
        binding.userImages.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
        binding.userImages.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun onDrawableClick(imgSrc: String,position: Int) {
        binding.userImages[position].elevation = 1.25F
        binding.btnCreateAccount.visibility = View.VISIBLE
        image = imgSrc
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.btnCreateAccount.visibility = View.GONE
    }
}