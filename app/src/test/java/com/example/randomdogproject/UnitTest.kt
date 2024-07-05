package com.example.randomdogproject

import com.example.randomdogproject.data.DogRepository
import com.example.randomdogproject.data.model.ApiResponse
import com.example.randomdogproject.data.model.DogProfile
import com.example.randomdogproject.ui.profile.DogProfileUiState
import com.example.randomdogproject.ui.profile.DogProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.lang.RuntimeException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class FakeDogProfileRepository(
    val shouldReturnException: Boolean = false,
    val shouldReturnError: Boolean = false
) : DogRepository {

    override fun getRandomProfile(): Flow<ApiResponse<DogProfile>> = flow {
        val response = if (!shouldReturnException) {
            if (shouldReturnError) {
                ApiResponse.ApiSuccess(DogProfile("https://example.com"))
            } else {
                ApiResponse.ApiError(999, "")
            }
        } else {
            ApiResponse.ApiException(RuntimeException())
        }
        emit(response)
    }
}

class UnitTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @Test
    fun `뷰모델을_생성하면_ui상태는_로딩중이_반환됨`() {
        // Given
        val viewModel = DogProfileViewModel(
            FakeDogProfileRepository()
        )

        // Then
        assert(viewModel.uiState.value is DogProfileUiState.Loading)
    }

    @Test
    fun `새로고침_API가_정상적으로_반환된다면_uiState는_LoadFailed_가진다`() = runTest {
        // Given
        val viewModel = DogProfileViewModel(
            FakeDogProfileRepository()
        )

        viewModel.onRefreshClick()
        advanceUntilIdle()

        // Then
        assert(viewModel.uiState.value is DogProfileUiState.Profile)
    }

    @Test
    fun `API_호출할때_Exception이_발생했을_때_uiState는_LoadFailed를_가진다`() = runTest {
        // Given
        val viewModel = DogProfileViewModel(
            FakeDogProfileRepository(true)
        )

        viewModel.onRefreshClick()
        advanceUntilIdle()

        // Then
        assert(viewModel.uiState.value is DogProfileUiState.LoadFailed)
    }
}