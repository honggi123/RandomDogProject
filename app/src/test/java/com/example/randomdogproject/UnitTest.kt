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
import kotlinx.coroutines.test.resetMain
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
    val shouldReturnNetworkError: Boolean = false
) : DogRepository {

    override fun getRandomProfile(): Flow<ApiResponse<DogProfile>> = flow {
        val response = if (shouldReturnNetworkError) {
            ApiResponse.ApiException(RuntimeException())
        } else {
            ApiResponse.ApiSuccess(DogProfile("https://example.com"))
        }
        emit(response)
    }
}

class UnitTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @Test
    fun `뷰모델을 생성하면 ui상태는 로딩중이 반환됨`() {
        // Given
        val viewModel = DogProfileViewModel(
            FakeDogProfileRepository()
        )

        // Then
        assert(viewModel.uiState.value is DogProfileUiState.Loading)
    }
}