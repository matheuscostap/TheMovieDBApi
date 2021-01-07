package com.costa.matheus.filmesapi.trending


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.costa.matheus.filmesapi.CoroutineRule
import com.costa.matheus.filmesapi.model.response.TrendingResponse
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.repository.trending.TrendingRepository
import com.costa.matheus.filmesapi.view.trending.TrendingViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Request
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TrendingViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    @MockK
    lateinit var repository: TrendingRepository
    lateinit var viewModel: TrendingViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = TrendingViewModel(repository)
    }


    @Test
    fun initialStateTest() {
        val currentState = viewModel.state.value
        Assert.assertTrue(currentState is RequestState.Success && currentState.data == null)
    }

    @Test
    fun successStateTest() = coroutineRule.runBlockingTest {
        val apiReturn = TrendingResponse(1, emptyList())

        coEvery { repository.getTrending().await() } returns apiReturn

        viewModel.getTrending()
        val currentState = viewModel.state.value

        Assert.assertTrue(currentState is RequestState.Success)
        Assert.assertTrue((currentState as RequestState.Success).data == apiReturn)
    }

    @Test
    fun errorStateTest() = coroutineRule.runBlockingTest {
        coEvery { repository.getTrending().await() } throws Exception()

        viewModel.getTrending()
        val currentState = viewModel.state.value

        Assert.assertTrue(currentState is RequestState.Error)
    }

    @Test
    fun loadingAndSuccessStateTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<TrendingResponse>>()

        val job = launch {
            viewModel.state.toList(statesHistory)
        }

        val apiReturn = TrendingResponse(1, emptyList())

        coEvery { repository.getTrending().await() } returns apiReturn

        viewModel.getTrending()

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Success State
        Assert.assertTrue(statesHistory[2] is RequestState.Success)
        Assert.assertTrue((statesHistory[2] as RequestState.Success).data == apiReturn)

        job.cancel()
    }

    @Test
    fun loadingAndErrorStateTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<TrendingResponse>>()

        val job = launch {
            viewModel.state.toList(statesHistory)
        }

        coEvery { repository.getTrending().await() } throws Exception()

        viewModel.getTrending()

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Error State
        Assert.assertTrue(statesHistory[2] is RequestState.Error)

        job.cancel()
    }
}