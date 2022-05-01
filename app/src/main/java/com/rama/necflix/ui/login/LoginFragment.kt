package com.rama.necflix.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rama.necflix.R
import com.rama.necflix.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater,container,false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //primero obtiene los datos de los usuarios registrados

        //aqui van las cositas del inicio de sesion
        binding.yes.setOnClickListener {
            //mostrar usuarios y pedir contraseña

        }
        binding.no.setOnClickListener {
            //navega al fragmento de crear cuenta
            findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment)
        }
        binding.invitado.setOnClickListener {
            //aqui hay que crear una cuenta de invitado

            //navega home como invitado
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}