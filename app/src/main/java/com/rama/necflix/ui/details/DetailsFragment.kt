package com.rama.necflix.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rama.necflix.data.PosterDBMovieSelected
import com.rama.necflix.data.VideosDBMovieSelected
import com.rama.necflix.data.normalDetailsOfMovie
import com.rama.necflix.databinding.FragmentDetailsBinding
import com.rama.necflix.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(), DetailsAdapterMovies.OnMovieDataClickListener {

    private val detailsVM by viewModels<DetailsVM>()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var details: normalDetailsOfMovie

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id
        setupRecyclerPosters()
        setupRecyclerVideos()
        getDetails(id)

    }

    private fun setupRecyclerPosters() {
        binding.recyclerPosters.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        binding.recyclerPosters.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }
    private fun setupRecyclerVideos(){
        binding.recyclerVideos.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerVideos.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.HORIZONTAL
            )
        )
    }

    private fun getDetails(id: Int) {
        detailsVM.getDetails(id)
        return detailsVM.details.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {
                    Toast.makeText(
                        context, "Cargando", Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Success -> {
                    details = result.data
                    binding.title.text = details.title
                    binding.overview.text = details.overview
                    binding.idMovie.text = details.id.toString()
                    binding.puntuacion.text = details.vote_average.toString()
                    binding.languageOriginal.text = details.original_language
                    val generos = arrayListOf<String>()
                    for(i in details.GenresDBMovieSelected){
                        generos.add(i.name)
                        binding.genres.text = generos.toString()
                    }
                    binding.origTitle.text = details.original_title
                    binding.releasedate.text = details.release_date
                    binding.recyclerPosters.adapter = DetailsAdapterMovies(requireContext(),
                        details.PosterDBMovieSelected, emptyList(),"posters",this)
                    binding.recyclerVideos.adapter = DetailsAdapterMovies(requireContext(),
                    emptyList(), details.VideosDBMovieSelected, "videos", this)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPosterClickListener(item: PosterDBMovieSelected, position: Int) {
        Toast.makeText(requireContext(), "posterClick", Toast.LENGTH_LONG)
            .show()
    }

    override fun onVideoClickListener(item: VideosDBMovieSelected, position: Int) {
        Toast.makeText(requireContext(), "wtf", Toast.LENGTH_LONG)
            .show()
    }
}