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

class LoginFragment : Fragment(), AccountsImagesAdapter.OnPhotoClickListener {
    private val viewModel: LoginViewModel by viewModels<LoginViewModel>()
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var passwordDB: String
    lateinit var chooseAccount: Accounts
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
        val accounts: List<Accounts> = listOf(
        )
        //getListAccounts()
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
        //viewModel.getAccountsFromDatabase()
    }

    private fun goneOrVisible(){
        binding.subscribe.visibility = View.GONE
        binding.yesNo.visibility = View.GONE
        binding.invitado.visibility = View.GONE
        //obtener usuarios de la lista accounts
        //configurar el recyclerview
        setupRV()
        binding.accounts.visibility = View.VISIBLE
    }
    private fun initSession(password: String){
        //para comprobar el password ingresado con el obtenido de la database
        if(password == passwordDB){
            val primaryKey = chooseAccount.primaryKey
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment, bundleOf("primaryKey" to primaryKey))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPhotoClick(account: Accounts) {
        passwordDB = account.password
        chooseAccount = account
        binding.btnInitSession.isClickable
    }

}

