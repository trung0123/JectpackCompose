package com.example.testcompose.survey

import android.net.Uri
import androidx.annotation.StringRes

data class SurveyResult(
    val library: String,
    @StringRes
    val result: Int,
    @StringRes
    val description: Int
)

data class Survey(
    @StringRes
    val title: Int,
    val questions: List<Question>
)

data class Question(
    val id: Int,
    @StringRes
    val questionText: Int,
    val answer: PossibleAnswer,
    @StringRes
    val description: Int? = null,
    val permissionRequired: List<String> = emptyList(),
    @StringRes
    val permissionsRationaleText: Int? = null
)

/**
 * Type of supported actions for a survey
 */
enum class SurveyActionType { PICK_DATE, TAKE_PHOTO, SELECT_CONTACT }

sealed class SurveyActionResult {
    data class Date(val date: String) : SurveyActionResult()
    data class Photo(val uri: Uri) : SurveyActionResult()
    data class Contact(val contact: String) : SurveyActionResult()
}

sealed class PossibleAnswer {
    data class SingleChoice(val optionsStringRes: List<Int>) : PossibleAnswer()
    data class SingleChoiceIcon(val optionsStringIconRes: List<Pair<Int, Int>>) : PossibleAnswer()
    data class MultipleChoice(val optionsStringRes: List<Int>) : PossibleAnswer()
    data class MultipleChoiceIcon(val optionsStringIconRes: List<Pair<Int, Int>>) : PossibleAnswer()
    data class Action(
        @StringRes val label: Int,
        val actionType: SurveyActionType
    ) : PossibleAnswer()

    data class Slider(
        val range: ClosedFloatingPointRange<Float>,
        val steps: Int,
        @StringRes val startText: Int,
        @StringRes val endText: Int,
        @StringRes val neutralText: Int,
        val defaultValue: Float = 5.5f
    ) : PossibleAnswer()
}

sealed class Answer<T : PossibleAnswer> {
    object PermissionDenied : Answer<Nothing>()
    data class SingleChoice(@StringRes val answer: Int) : Answer<PossibleAnswer.SingleChoice>()
    data class MultipleChoice(val answersStringRes: Set<Int>) :
        Answer<PossibleAnswer.MultipleChoice>()

    data class Action(val result: SurveyActionResult) : Answer<PossibleAnswer.Action>()
    data class Slider(val answerValue: Float) : Answer<PossibleAnswer.Slider>()
}













