package com.rama.necflix.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.rama.necflix.R
import com.rama.necflix.data.Accounts
import com.rama.necflix.databinding.LoginFragmentBinding
import com.rama.necflix.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment : Fragment(), AccountsImagesAdapter.OnPhotoClickListener {
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var accounts: List<Accounts>
    lateinit var passwordDB: String
    private var chooseAccount: Accounts? = null
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
        getListAccounts()
        //aqui van las cositas del inicio de sesion
        binding.yes.setOnClickListener {
            if(accounts == emptyList<Accounts>()){
                Toast.makeText(context,"No hay usuarios registrados", Toast.LENGTH_SHORT).show()
            }else{
                //mostrar usuarios y pedir contraseña
                goneOrVisible()
            }
        }
        binding.btnInitSession.setOnClickListener {
            val password = binding.passwordTiped.text.toString()
            initSession(password)
        }
        //crear cuenta
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

    private fun getListAccounts() {
        viewModel.accounts.observe(viewLifecycleOwner, androidx.lifecycle.Observer { result ->
            when(result){
                is Resource.Loading -> {
                    Toast.makeText(requireContext(),"Obteniendo datos", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    accounts = result.data
                    Toast.makeText(requireContext(),"Datos obtenisod", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_SHORT ).show()
                }
            }
        })
    }

    private fun goneOrVisible(){
        binding.subscribe.visibility = View.GONE
        binding.yesNo.visibility = View.GONE
        binding.invitado.visibility = View.GONE
        //obtener usuarios de la lista accounts
        //configurar el recyclerview
        setupRV()
        binding.accountsImages.adapter = AccountsImagesAdapter(requireContext(),accounts,this)
        binding.accounts.visibility = View.VISIBLE
    }
    private fun initSession(password: String){
        //para comprobar el password ingresado con el obtenido de la database
        if(password == passwordDB){
            val primaryKey = chooseAccount?.username
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(primaryKey!!))
        }else{
            Toast.makeText(context,"Contraseña incorrecta",Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRV(){
        binding.accountsImages.layoutManager = GridLayoutManager(requireContext(),4,
            GridLayoutManager.VERTICAL,false)
        binding.accountsImages.addItemDecoration(
            DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL)
        )
    }
    override fun onPhotoClick(account: Accounts) {
        passwordDB = account.password
        chooseAccount = account
        binding.btnInitSession.isClickable = true
    }
    override fun onDestroyView() {
        super.onDestroyView()
        chooseAccount = null
        binding.btnInitSession.isClickable = false
        _binding = null
    }



}

