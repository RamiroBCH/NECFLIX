package com.rama.necflix.ui.login

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.rama.necflix.data.DrawableResourceName
import com.rama.necflix.databinding.AccountImagesRowBinding
import com.rama.necflix.databinding.CreateAccountFragmentBinding
import com.rama.necflix.LIST_OF_NAME_RESOURCE_NAME
import com.rama.necflix.R
import com.rama.necflix.data.Accounts

class CreateAccountFragment : Fragment(), CreateAccountAdapter.OnResourceClickListener {
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    private var _binding: CreateAccountFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var image: Drawable
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
        viewModel.insertDrawableName(LIST_OF_NAME_RESOURCE_NAME)
        //todo lo del recyclerview
        setupRecyclerView()
        //todo lo de crear cuenta
        binding.btnCreateAccount.setOnClickListener {
            if (binding.username.text != null) {
                createAccount()
                val primaryKey = account.username
                findNavController().navigate(
                    R.id.action_createAccountFragment_to_homeFragment,
                    bundleOf("primaryKey" to primaryKey)
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
        val requestToken: String = "wqeqwem12k3o132"
        //autorizar requestToken con login
        account = Accounts(
            binding.username.text.toString(),
            binding.password.text.toString(),
            requestToken,
            null,
            image.current
        )
        //obtenemos un requestToken autorizado
        //crear una session id y actualizar en room
        val sessionId: String = "2314134123"
        account = Accounts(
            binding.username.text.toString(),
            binding.password.text.toString(),
            requestToken,
            sessionId,
            image.current
        )
        //ponemos en room
        viewModel.insertAccountToRoom(account)
    }


    private fun setupRecyclerView() {
        binding.userImages.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
        binding.userImages.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

    }

    override fun onDrawableClick(imgSrc: Drawable) {
        image = imgSrc
    }
}