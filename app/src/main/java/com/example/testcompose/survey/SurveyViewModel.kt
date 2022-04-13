package com.example.testcompose.survey

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val simpleDateFormatPattern = "EEE, MMM d"

class SurveyViewModel(
    private val surveyRepository: SurveyRepository,
    private val photoUriManager: PhotoUriManager
) : ViewModel() {

    private val _uiState = MutableLiveData<SurveyState>()
    val uiState: LiveData<SurveyState>
        get() = _uiState

    var askForPermissions by mutableStateOf(true)
        private set

    private lateinit var surveyInitialState: SurveyState

    // Uri used to save photos taken with the camera
    private var uri: Uri? = null

    init {
        viewModelScope.launch {
            val survey = surveyRepository.getSurvey()

            // Create the default questions state based on the survey questions
            val questions: List<QuestionState> = survey.questions.mapIndexed { index, question ->
                val showPrevious = index > 0
                val showDone = index == survey.questions.size - 1
                QuestionState(
                    question = question,
                    questionIndex = index,
                    totalQuestionsCount = survey.questions.size,
                    showPrevious = showPrevious,
                    showDone = showDone
                )
            }
            surveyInitialState = SurveyState.Questions(survey.title, questions)
            _uiState.value = surveyInitialState
        }
    }
}