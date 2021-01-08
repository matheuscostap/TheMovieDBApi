package com.costa.matheus.filmesapi.moviedetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.costa.matheus.filmesapi.CoroutineRule
import com.costa.matheus.filmesapi.model.dto.MovieDetailModel
import com.costa.matheus.filmesapi.model.dto.VideoModel
import com.costa.matheus.filmesapi.model.response.TrendingResponse
import com.costa.matheus.filmesapi.model.response.VideoResponse
import com.costa.matheus.filmesapi.repository.moviedetails.MovieDetailsRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.moviedetails.MovieDetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()


    @MockK
    lateinit var repository: MovieDetailsRepository
    lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MovieDetailsViewModel(repository)
    }


    @Test
    fun initialStateTest() {
        val currentState = viewModel.state.value
        Assert.assertTrue(currentState is RequestState.Success && currentState.data == null)
    }

    @Test
    fun successStateTest() = coroutineRule.runBlockingTest {
        val apiReturn = mockk<MovieDetailModel>()

        coEvery { repository.getMovie(1).await() } returns apiReturn

        viewModel.getMovieDetail(1)
        val currentState = viewModel.state.value

        Assert.assertTrue(currentState is RequestState.Success)
        Assert.assertTrue((currentState as RequestState.Success).data == apiReturn)
    }

    @Test
    fun errorStateTest() = coroutineRule.runBlockingTest {
        coEvery { repository.getMovie(1).await() } throws Exception()

        viewModel.getMovieDetail(1)
        val currentState = viewModel.state.value

        Assert.assertTrue(currentState is RequestState.Error)
    }


    @Test
    fun loadingAndSuccessStateTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<MovieDetailModel>>()

        val job = launch {
            viewModel.state.toList(statesHistory)
        }

        val apiReturn = mockk<MovieDetailModel>()

        coEvery { repository.getMovie(1).await() } returns apiReturn

        viewModel.getMovieDetail(1)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Success State
        Assert.assertTrue(statesHistory[2] is RequestState.Success)
        Assert.assertTrue((statesHistory[2] as RequestState.Success).data == apiReturn)

        job.cancel()
    }

    @Test
    fun loadingAndErrorStateTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<MovieDetailModel>>()

        val job = launch {
            viewModel.state.toList(statesHistory)
        }

        coEvery { repository.getMovie(1).await() } throws Exception()

        viewModel.getMovieDetail(1)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Error State
        Assert.assertTrue(statesHistory[2] is RequestState.Error)

        job.cancel()
    }

    @Test
    fun canShowVideoMustReturnTrueTest() {
        val videoResponse = VideoResponse(listOf(VideoModel("id","key")))
        Assert.assertTrue(viewModel.canShowVideo(videoResponse))
    }

    @Test
    fun canShowVideoMustReturnFalseTest() {
        val videoResponse = VideoResponse(emptyList())
        Assert.assertFalse(viewModel.canShowVideo(videoResponse))
    }
}