package com.rama.necflix.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rama.necflix.R
import com.rama.necflix.data.GenresDB
import com.rama.necflix.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val args: HomeFragmentArgs by navArgs()
    lateinit var genresDB: List<GenresDB>
    val map = HashMap<String, ArrayList<String>>()
    var words = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //obtener datos de la cuenta ingresada
        val primaryKey:String = args.primaryKey
        //obtener el id de sesion de invitado
        val guestSessionId: String = args.sessionId
        //probar si se inicio sesion
        if( primaryKey == "no" || guestSessionId == "no") {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    //lo de los expandable list view
        //obtener listas de generos
        genresDB = getGenreApi()
        setData(genresDB)
        val adapterExpList = FilterByAdapter(genresDB,map,words)
        binding.genero.setAdapter(adapterExpList)
        binding.genero.setOnChildClickListener { genero, view, groupPosition, childPosition, l ->
            Toast.makeText(context, map[words[groupPosition]]?.get(childPosition), Toast.LENGTH_SHORT).show()
            true
        }

        var lastIndex = -1
        binding.genero.setOnGroupExpandListener {
            if (lastIndex != -1 && lastIndex != it){
                binding.genero.collapseGroup(lastIndex)
            }
            Log.d("tag", "onCreate: $it $lastIndex")
            lastIndex = it
        }
    }

    private fun setData(genresDB: List<GenresDB>) {
        val listArray : ArrayList<String> = arrayListOf()
        for(i in genresDB.indices){
            listArray.add(genresDB[i].name)
            Toast.makeText(context, listArray[i], Toast.LENGTH_SHORT).show()
        }
        map["Generos"] = listArray
        words = arrayListOf("Genero")
    }

    private fun getGenreApi(): List<GenresDB> {
        //return homeViewModel.getGenre()
        val listaDeGeneros: List<GenresDB> = listOf(GenresDB(1, "nombre"),GenresDB(2,"apellido"))
        return listaDeGeneros
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}