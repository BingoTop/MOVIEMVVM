package com.james.movietmdb.presentation.views.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.james.movietmdb.data.utils.BaseResponse
import com.james.movietmdb.data.utils.Constants
import com.james.movietmdb.databinding.ActivityMainBinding
import com.james.movietmdb.presentation.views.LoadingDialog
import com.james.movietmdb.presentation.views.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()
    private val listViewModel: ListViewModel by viewModels()
    lateinit var comingSoonMovieAdapter: MovieAdapter
    lateinit var popularMovieAdapter: MovieAdapter
    lateinit var topRatedMovieAdapter: MovieAdapter
    lateinit var nowPlayingMovieAdapter: NowPlayingMovieAdapter
    lateinit var loadingDialog:LoadingDialog

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapters()
        initViewModel()


    }

    private fun initViewModel() {
        listViewModel.movieComingSoonList.observe(this, {
            when (it) {
                is BaseResponse.Success -> {
                    it.data?.let {
                        comingSoonMovieAdapter.setMovieList(it)
                    }
                }
                is BaseResponse.Error -> {
                }
            }
        })
        listViewModel.moviePopularList.observe(this, {
            when (it) {
                is BaseResponse.Success -> {
                    it.data?.let {
                        popularMovieAdapter.setMovieList(it)
                    }
                }
            }
        })
        listViewModel.movieTopRatedList.observe(this, {
            when (it) {
                is BaseResponse.Success -> {
                    it.data?.let {
                        topRatedMovieAdapter.setMovieList(it)
                    }
                }
            }
        })

        listViewModel.movieNowPlayingList.observe(this, {
            nowPlayingMovieAdapter.submitData(lifecycle, it)
        })

        listViewModel.response.observe(this,{
            when(it){
                is BaseResponse.Loading ->{
                    loadingDialog = LoadingDialog(this)
                    loadingDialog.show()
                }
                is BaseResponse.Loaded ->{
                    loadingDialog.dismiss()
                }
            }
        })

    }

    private fun initAdapters() {
        initComingSoonAdapter()
        initPopularAdapter()
        initTopRatedAdapter()
        initNowPlayingAdapter()
    }

    private fun goDetailActivity(movieId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.MOVIE_ID, movieId)
        startActivity(intent)
    }


    private fun initComingSoonAdapter() {
        comingSoonMovieAdapter = MovieAdapter()
        binding.listRvComingSoon.apply {
            adapter = comingSoonMovieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        compositeDisposable.add(
            comingSoonMovieAdapter.movieClickSubject.subscribe {
                goDetailActivity(it.id)
            }
        )
    }


    private fun initPopularAdapter() {
        popularMovieAdapter = MovieAdapter()
        binding.listRvPolular.apply {
            adapter = popularMovieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        compositeDisposable.add(
            popularMovieAdapter.movieClickSubject.subscribe {
                goDetailActivity(it.id)
            }
        )

    }

    private fun initTopRatedAdapter() {
        topRatedMovieAdapter = MovieAdapter()
        binding.listRvHighRated.apply {
            adapter = topRatedMovieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        compositeDisposable.add(
            topRatedMovieAdapter.movieClickSubject.subscribe {
                goDetailActivity(it.id)
            }
        )
    }

    private fun initNowPlayingAdapter() {
        nowPlayingMovieAdapter = NowPlayingMovieAdapter()
        binding.listRvNowPlaying.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = nowPlayingMovieAdapter
        }
        compositeDisposable.add(
            nowPlayingMovieAdapter.clickSubject.subscribe {
                goDetailActivity(it.id)
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}