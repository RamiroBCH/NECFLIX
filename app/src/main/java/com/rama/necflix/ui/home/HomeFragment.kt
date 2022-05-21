package com.rama.necflix.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rama.necflix.data.GenresDB
import com.rama.necflix.data.resultsDB
import com.rama.necflix.databinding.FragmentHomeBinding
import com.rama.necflix.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), MoviesAdapter.OnClickListener {

    private var countListNowPlaying: Int = 0
    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val args: HomeFragmentArgs by navArgs()
    private var genresDB: List<GenresDB> = emptyList()
    var map = HashMap<String, List<String>>()
    var words: List<String> = emptyList()
    private var mode = "movie"

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
        val primaryKey: String = args.primaryKey
        //obtener el id de sesion de invitado
        val guestSessionId: String = args.sessionId
        //probar si se inicio sesion
        getActiveAccount()
        homeViewModel.setType("Upcoming")
//SEARCH SETUP***************************************************************************
        setupSearch()
//BUTTON MODE SETUP**********************************************************************
        binding.btnModo.setOnClickListener {
            Toast.makeText(context, "Modo oscuro", Toast.LENGTH_SHORT).show()
        }
//RECYCLERVIEW NOWPLAYING****************************************************************
        //obtener los datos nowplaying
        setPlayingRecyclerView()
        getDataNowPlaying()
//RECYCLERVIEW MOVIES AND TV SHOWS*******************************************************
        //obtener los datos movies tv shows
        setMoviesTvShowsRecyclerView()
        getMoviesFromDB()
//EXPANDABLELISTVIEW GENRE***************************************************************
        //obtener listas de generos
        getGenreApi()
        //clicklistener de los items
        binding.genero.setOnChildClickListener { genero, view, groupPosition, childPosition, l ->
            //homeViewModel.setGenre(map[words[groupPosition]]?.get(childPosition).toString())
            if (groupPosition == 1) {
                mode = "movie"
                homeViewModel.setMode(mode)
                homeViewModel.setType(map[words[groupPosition]]?.get(childPosition).toString())
            }
            else if(groupPosition == 2){
                mode = "tv"
                homeViewModel.setMode(mode)
                homeViewModel.setType(map[words[groupPosition]]?.get(childPosition).toString())

            }
            else {
                Toast.makeText(
                    context,
                    map[words[groupPosition]]?.get(childPosition),
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
        //clicklistener del viewGroup que contiene los items
        var lastIndex = -1
        binding.genero.setOnGroupExpandListener {
            if (lastIndex != -1 && lastIndex != it) {
                binding.genero.collapseGroup(lastIndex)
            }
            Log.d("tag", "onCreate: $it $lastIndex")
            lastIndex = it
        }
    }

    private fun getActiveAccount() {
        return homeViewModel.getActiveAcc.observe(viewLifecycleOwner, Observer {account ->
            when (account) {
                is Resource.Loading -> {
                    Toast.makeText(context, "Obteniendo cuenta activa", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val account = account.data
                    if (account.isActive){
                        Toast.makeText(context,
                            " Bienvenido ${account.username} !!!", Toast.LENGTH_SHORT).show()
                        binding.nameApp.text = account.username
                    }else{
                        Toast.makeText(context,
                            "no se inicio sesion", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Error ${account.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun setupSearch() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                homeViewModel.setType((p0!!).toString())
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun setPlayingRecyclerView() {
        binding.recyclerNowplaying.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerNowplaying.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setMoviesTvShowsRecyclerView() {
        binding.recyclerMoviestvshows.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerMoviestvshows.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun getDataNowPlaying() {
        return homeViewModel.getNowPlaying.observe(viewLifecycleOwner, Observer { list ->
            when (list) {
                is Resource.Loading -> {
                    Toast.makeText(context, "Cargando", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    countListNowPlaying = list.data.size
                    //pasar los datos nowplaying a nowPlayingAdapter
                    binding.recyclerNowplaying.adapter =
                        MoviesAdapter(requireContext(), list.data, this, "nowplaying")
                    smoothScrolling(countListNowPlaying, binding)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${list.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun getMoviesFromDB() {
        return homeViewModel.getMoviesFromDB.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    //Toast.makeText(context, "Cargando", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    //pasar los datos al adapter
                    binding.recyclerMoviestvshows.adapter =
                        MoviesAdapter(requireContext(), result.data, this, "movies")
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),
                        "Error ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    //Movimiento automatico
    private fun smoothScrolling(count: Int, binding: FragmentHomeBinding) {
        homeViewModel.smoothScrolling(count, binding)
    }

    //obtener lista de generos
    private fun getGenreApi() {
        return homeViewModel.getGenre.observe(viewLifecycleOwner, Observer { generos ->
            when (generos) {
                is Resource.Loading -> {
                    //Toast.makeText(context, "Cargando", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    genresDB = generos.data
                    //configurar los objetos map y words con los datos obtenidos
                    setData(genresDB)
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Error ${generos.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    //pasar los datos al adapter del boton filtrar
    private fun setData(genresDB: List<GenresDB>) {
        var list: List<String> = emptyList()
        for (i in genresDB.indices) {
            list = list + genresDB[i].name
            //Toast.makeText(context, list[i], Toast.LENGTH_SHORT).show()
        }
        map["Filtrar por Generos"] = list
        map["Filtrar peliculas por:"] = arrayListOf("Upcoming", "Popular", "Top Rated")
        map["Filtrar Tv Shows por:"] = arrayListOf("AiringToday", "Tv Popular", "Tv Top Rated")
        words = listOf("Filtrar por Generos", "Filtrar peliculas por:", "Filtrar Tv Shows por:")
        //pasar los datos al adapter
        val adapterExpList = FilterByAdapter(genresDB, map, words)
        binding.genero.setAdapter(adapterExpList)
    }

    override fun onNowPlayingClickListener(item: resultsDB, position: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id))
    }

    override fun onMoviesTvShowsClickListener(item: resultsDB, position: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}