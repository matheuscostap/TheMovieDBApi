package com.costa.matheus.filmesapi.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.costa.matheus.filmesapi.CoroutineRule
import com.costa.matheus.filmesapi.model.dto.*
import com.costa.matheus.filmesapi.repository.account.AccountRepository
import com.costa.matheus.filmesapi.repository.login.LoginRepository
import com.costa.matheus.filmesapi.repository.state.RequestState
import com.costa.matheus.filmesapi.view.login.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
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
class LoginViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRule()

    @MockK
    lateinit var loginRepository: LoginRepository

    @MockK
    lateinit var accountRepository: AccountRepository

    @MockK
    lateinit var appTokenModelMock: AppTokenModel

    @MockK
    lateinit var userAccessTokenMock: UserAccessTokenModel

    @MockK
    lateinit var sessionModelMock: SessionModel

    @MockK
    lateinit var accountModelMock: AccountModel

    lateinit var viewModel: LoginViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(loginRepository, accountRepository)
    }


    @Test
    fun initialTest() {
        val getRequestTokenState = viewModel.getRequestTokenState.value
        val getAccessTokenState = viewModel.getAccessTokenState.value
        val createSessionState = viewModel.createSessionState.value
        val getAccountState = viewModel.getAccountState.value

        Assert.assertTrue(getRequestTokenState is RequestState.Success && getRequestTokenState.data == null)
        Assert.assertTrue(getAccessTokenState is RequestState.Success && getAccessTokenState.data == null)
        Assert.assertTrue(createSessionState is RequestState.Success && createSessionState.data == null)
        Assert.assertTrue(getAccountState is RequestState.Success && getAccountState.data == null)
    }

    @Test
    fun getRequestTokenSuccessStateTest() = coroutineRule.runBlockingTest {
        val apiReturn = appTokenModelMock
        val param = LoginRequestTokenBody("")

        coEvery { loginRepository.getRequestToken(param).await() } returns apiReturn

        viewModel.getRequestToken(param)
        val currentState = viewModel.getRequestTokenState.value

        Assert.assertTrue(currentState is RequestState.Success)
        Assert.assertTrue((currentState as RequestState.Success).data == apiReturn)
    }

    @Test
    fun getRequestTokenErrorStateTest() = coroutineRule.runBlockingTest {
        val param = LoginRequestTokenBody("")
        coEvery { loginRepository.getRequestToken(param).await() } throws Exception()

        viewModel.getRequestToken(param)
        val currentState = viewModel.getRequestTokenState.value

        Assert.assertTrue(currentState is RequestState.Error)
    }

    @Test
    fun getRequestTokenLoadingAndSuccessTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<AppTokenModel>>()
        val param = LoginRequestTokenBody("")

        val job = launch {
            viewModel.getRequestTokenState.toList(statesHistory)
        }

        val apiReturn = appTokenModelMock

        coEvery { loginRepository.getRequestToken(param).await() } returns apiReturn

        viewModel.getRequestToken(param)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Success State
        Assert.assertTrue(statesHistory[2] is RequestState.Success)
        Assert.assertTrue((statesHistory[2] as RequestState.Success).data == apiReturn)

        job.cancel()
    }

    @Test
    fun getRequestTokenLoadingAndErrorTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<AppTokenModel>>()
        val param = LoginRequestTokenBody("")

        val job = launch {
            viewModel.getRequestTokenState.toList(statesHistory)
        }

        coEvery { loginRepository.getRequestToken(param).await() } throws java.lang.Exception()

        viewModel.getRequestToken(param)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Error State
        Assert.assertTrue(statesHistory[2] is RequestState.Error)

        job.cancel()
    }

    @Test
    fun getAccessTokenSuccessStateTest() = coroutineRule.runBlockingTest {
        val apiReturn = userAccessTokenMock
        val param = GetAccessTokenRequestBody("")

        coEvery { loginRepository.getAccessToken(param).await() } returns apiReturn

        viewModel.getAccessToken(param)
        val currentState = viewModel.getAccessTokenState.value

        Assert.assertTrue(currentState is RequestState.Success)
        Assert.assertTrue((currentState as RequestState.Success).data == apiReturn)
    }

    @Test
    fun getAccessTokenErrorStateTest() = coroutineRule.runBlockingTest {
        val param = GetAccessTokenRequestBody("")
        coEvery { loginRepository.getAccessToken(param).await() } throws Exception()

        viewModel.getAccessToken(param)
        val currentState = viewModel.getAccessTokenState.value

        Assert.assertTrue(currentState is RequestState.Error)
    }

    @Test
    fun getAccessTokenLoadingAndSuccessTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<UserAccessTokenModel>>()
        val param = GetAccessTokenRequestBody("")

        val job = launch {
            viewModel.getAccessTokenState.toList(statesHistory)
        }

        val apiReturn = userAccessTokenMock

        coEvery { loginRepository.getAccessToken(param).await() } returns apiReturn

        viewModel.getAccessToken(param)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Success State
        Assert.assertTrue(statesHistory[2] is RequestState.Success)
        Assert.assertTrue((statesHistory[2] as RequestState.Success).data == apiReturn)

        job.cancel()
    }

    @Test
    fun getAccessTokenLoadingAndErrorTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<UserAccessTokenModel>>()
        val param = GetAccessTokenRequestBody("")

        val job = launch {
            viewModel.getAccessTokenState.toList(statesHistory)
        }

        coEvery { loginRepository.getAccessToken(param).await() } throws java.lang.Exception()

        viewModel.getAccessToken(param)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Error State
        Assert.assertTrue(statesHistory[2] is RequestState.Error)

        job.cancel()
    }

    @Test
    fun createSessionSuccessStateTest() = coroutineRule.runBlockingTest {
        val apiReturn = sessionModelMock
        val param = CreateSessionRequestBody("")

        coEvery { loginRepository.createSession(param).await() } returns apiReturn

        viewModel.createSession(param)
        val currentState = viewModel.createSessionState.value

        Assert.assertTrue(currentState is RequestState.Success)
        Assert.assertTrue((currentState as RequestState.Success).data == apiReturn)
    }

    @Test
    fun createSessionErrorStateTest() = coroutineRule.runBlockingTest {
        val param = CreateSessionRequestBody("")
        coEvery { loginRepository.createSession(param).await() } throws Exception()

        viewModel.createSession(param)
        val currentState = viewModel.createSessionState.value

        Assert.assertTrue(currentState is RequestState.Error)
    }

    @Test
    fun createSessionLoadingAndSuccessTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<SessionModel>>()
        val param = CreateSessionRequestBody("")

        val job = launch {
            viewModel.createSessionState.toList(statesHistory)
        }

        val apiReturn = sessionModelMock

        coEvery { loginRepository.createSession(param).await() } returns apiReturn

        viewModel.createSession(param)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Success State
        Assert.assertTrue(statesHistory[2] is RequestState.Success)
        Assert.assertTrue((statesHistory[2] as RequestState.Success).data == apiReturn)

        job.cancel()
    }

    @Test
    fun createSessionLoadingAndErrorTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<SessionModel>>()
        val param = CreateSessionRequestBody("")

        val job = launch {
            viewModel.createSessionState.toList(statesHistory)
        }

        coEvery { loginRepository.createSession(param).await() } throws java.lang.Exception()

        viewModel.createSession(param)

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Error State
        Assert.assertTrue(statesHistory[2] is RequestState.Error)

        job.cancel()
    }

    @Test
    fun getAccountSuccessStateTest() = coroutineRule.runBlockingTest {
        val apiReturn = accountModelMock

        coEvery { accountRepository.getAccount().await() } returns apiReturn

        viewModel.getAccount()
        val currentState = viewModel.getAccountState.value

        Assert.assertTrue(currentState is RequestState.Success)
        Assert.assertTrue((currentState as RequestState.Success).data == apiReturn)
    }

    @Test
    fun getAccountErrorStateTest() = coroutineRule.runBlockingTest {
        coEvery { accountRepository.getAccount().await() } throws Exception()

        viewModel.getAccount()
        val currentState = viewModel.getAccountState.value

        Assert.assertTrue(currentState is RequestState.Error)
    }

    @Test
    fun getAccountLoadingAndSuccessTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<AccountModel>>()

        val job = launch {
            viewModel.getAccountState.toList(statesHistory)
        }

        val apiReturn = accountModelMock

        coEvery { accountRepository.getAccount().await() } returns apiReturn

        viewModel.getAccount()

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Success State
        Assert.assertTrue(statesHistory[2] is RequestState.Success)
        Assert.assertTrue((statesHistory[2] as RequestState.Success).data == apiReturn)

        job.cancel()
    }

    @Test
    fun getAccountLoadingAndErrorTest() = coroutineRule.runBlockingTest {
        val statesHistory = mutableListOf<RequestState<AccountModel>>()

        val job = launch {
            viewModel.getAccountState.toList(statesHistory)
        }

        coEvery { accountRepository.getAccount().await() } throws java.lang.Exception()

        viewModel.getAccount()

        //Testando Loading State
        Assert.assertTrue(statesHistory[1] is RequestState.Loading)

        //Testando Error State
        Assert.assertTrue(statesHistory[2] is RequestState.Error)

        job.cancel()
    }

}