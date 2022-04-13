package com.example.testcompose.survey

import android.Manifest
import android.os.Build
import com.example.testcompose.R
import com.example.testcompose.survey.PossibleAnswer.*
import com.example.testcompose.survey.SurveyActionType.PICK_DATE
import com.example.testcompose.survey.SurveyActionType.TAKE_PHOTO

// Static data of questions
private val jetpackQuestions = mutableListOf(
    Question(
        id = 1,
        questionText = R.string.in_my_free_time,
        answer = MultipleChoice(
            optionsStringRes = listOf(
                R.string.read,
                R.string.work_out,
                R.string.draw,
                R.string.play_games,
                R.string.dance,
                R.string.watch_movies
            )
        ),
        description = R.string.select_all
    ),
    Question(
        id = 2,
        questionText = R.string.pick_superhero,
        answer = SingleChoiceIcon(
            optionsStringIconRes = listOf(
                Pair(R.drawable.spark, R.string.spark),
                Pair(R.drawable.lenz, R.string.lenz),
                Pair(R.drawable.bug_of_chaos, R.string.bugchaos),
                Pair(R.drawable.frag, R.string.frag)
            )
        ),
        description = R.string.select_one
    ),
    Question(
        id = 7,
        questionText = R.string.favourite_movie,
        answer = SingleChoice(
            listOf(
                R.string.star_trek,
                R.string.social_network,
                R.string.back_to_future,
                R.string.outbreak
            )
        ),
        description = R.string.select_one
    ),
    Question(
        id = 3,
        questionText = R.string.takeaway,
        answer = Action(label = R.string.pick_date, actionType = PICK_DATE),
        description = R.string.select_date
    ),
    Question(
        id = 4,
        questionText = R.string.selfies,
        answer = Slider(
            range = 1f..10f,
            steps = 3,
            startText = R.string.strongly_dislike,
            endText = R.string.strongly_like,
            neutralText = R.string.neutral
        )
    ),
).apply {
    if (Build.VERSION.SDK_INT >= 23) {
        add(
            Question(
                id = 975,
                questionText = R.string.selfie_skills,
                answer = Action(label = R.string.add_photo, actionType = TAKE_PHOTO),
                permissionRequired =
                when (Build.VERSION.SDK_INT) {
                    in 23..28 -> listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    else -> emptyList()
                },
                permissionsRationaleText = R.string.selfie_permissions
            )
        )
    }
}.toList()

private val jetpackSurvey = Survey(
    title = R.string.which_jetpack_library,
    questions = jetpackQuestions
)

object SurveyRepository {
    suspend fun getSurvey() = jetpackSurvey

    fun getSurveyResult(answers: List<Answer<*>>): SurveyResult {
        return SurveyResult(
            library = "Compose",
            result = R.string.survey_result,
            description = R.string.survey_result_description
        )
    }
}









