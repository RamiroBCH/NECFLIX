package com.rama.necflix.ui.login

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.rama.necflix.databinding.CreateAccountFragmentBinding
import com.rama.necflix.LIST_OF_NAME_RESOURCE_URL
import com.rama.necflix.R
import com.rama.necflix.data.Accounts
import com.rama.necflix.data.DrawableResourceUrl
import com.rama.necflix.data.Token
import com.rama.necflix.databinding.AccountImagesRowBinding
import com.rama.necflix.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment(), CreateAccountAdapter.OnDrawableClickListener {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: CreateAccountFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var image: String
    private var activarToken: Token = Token("","","")
    private var tokenNew: String = ""
    private var tokenActivado: String = ""
    private var sessionId: String = ""
    lateinit var account: Accounts

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateAccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
            } else {
                Toast.makeText(
                    context, "Ingrese nombre de usuario y contraseña", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun createAccount() {
        //obtener un token
        getTokenNew()
        //autorizar el token con login
        createTokenActivated(activarToken)
        //crear una session id y actualizar en room
        createSessionId(tokenActivado)
        //crear objeto Accounts con los datos
        //nombre de usuario, contraseña, sessionId y Image de perfil
        createAccountObject()
        //ponemos el objeto creado en room
        insertAccountObjectToRoom()
        //ir al fragmento Home pasando el nombre como dato primary key
        goToHomeFragment()
    }
    //Obtener el token
    private fun getTokenNew() {
        return viewModel.getTokenNew.observe(viewLifecycleOwner, { token ->
            when(token){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Obteniendo Token", Toast.LENGTH_SHORT )
                        .show()
                }
                is Resource.Success -> {
                    tokenNew = token.data
                    activarToken = Token(
                        binding.password.text.toString(),
                        tokenNew,
                        binding.username.text.toString()
                    )
                    Toast.makeText(requireContext(), "Token Obtenido", Toast.LENGTH_SHORT )
                        .show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${token.exception}", Toast.LENGTH_LONG )
                        .show()
                }
            }
        })
    }
    //activarlo
    private fun createTokenActivated(getToken: Token) {
        viewModel.getToken = getToken
        return viewModel.createTokenActivated.observe(viewLifecycleOwner, { actToken ->
            when(actToken){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Activando", Toast.LENGTH_SHORT )
                        .show()
                }
                is Resource.Success -> {
                    tokenActivado = actToken.data
                    Toast.makeText(requireContext(), "Token activado", Toast.LENGTH_SHORT )
                        .show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${actToken.exception}", Toast.LENGTH_LONG )
                        .show()
                }
            }
        })
    }
    //crear un id de sesion
    private fun createSessionId(tokenValidate: String) {
        viewModel.tokenValidate = tokenValidate
        return viewModel.createSessionId.observe(viewLifecycleOwner, { session ->
            when(session){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Creando Id de sesion", Toast.LENGTH_SHORT )
                        .show()
                }
                is Resource.Success -> {
                    sessionId = session.data
                    Toast.makeText(requireContext(), "Id de sesion creado", Toast.LENGTH_SHORT )
                        .show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${session.exception}", Toast.LENGTH_LONG )
                        .show()
                }
            }
        })
    }
    //crear objeto Accounts con los datos
    private fun createAccountObject() {
        account = Accounts(
            binding.username.text.toString(),
            binding.password.text.toString(),
            tokenActivado,
            sessionId,
            image,
            true
        )
    }
    //ponemos el objeto creado en room
    private fun insertAccountObjectToRoom() {
        viewModel.insertAccountToRoom(account)
    }
    //vamos a Home
    private fun goToHomeFragment() {
        val primaryKey = account.username
        findNavController().navigate(
            CreateAccountFragmentDirections
                .actionCreateAccountFragmentToHomeFragment(primaryKey,"0")
        )
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
        bindingRow: AccountImagesRowBinding,
        accountImgs: List<DrawableResourceUrl>
    ) {
        for(i in accountImgs.indices){
            binding.userImages[i].isSelected = false
            binding.userImages[i].setBackgroundColor(resources.getColor(R.color.white))
        }
        binding.userImages[position].isSelected = true
        when(binding.userImages[position].isSelected){
            true -> binding.userImages[position].setBackgroundColor(resources.getColor(R.color.lightRed))
        }
        binding.btnCreateAccount.visibility = View.VISIBLE
        image = imgSrc
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.btnCreateAccount.visibility = View.GONE
    }
}