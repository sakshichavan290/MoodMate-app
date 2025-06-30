package com.example.moodmate.viewmodel
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoodViewModel {
    private val db = FirebaseFirestore.getInstance()
    private val _moodList = MutableStateFlow<List<Mood>>(emptyList())
    val moodList: StateFlow<List<Mood>> = _moodList
    private val _communityMoodList = MutableStateFlow<List<Mood>>(emptyList())
    val communityMoodList: StateFlow<List<Mood>> = _communityMoodList
    data class Mood(
        val mood: String = "",
        val note: String = "",
        val timestamp: Long = 0L,
        val isShared: Boolean = false,
        val username: String = "Anonymous",
        val profileImageUrl: String = "", // Later you can fetch from Firebase Storage
        var likes: Int = 0,
        var comments: MutableList<String> = mutableListOf()
    )


    fun fetchMoods() {
        db.collection("moods")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { snapshot ->
                val moods = snapshot.documents.mapNotNull { it.toObject(Mood::class.java) }
                _moodList.value = moods
            }
            .addOnFailureListener {
            }
    }
    fun fetchCommunityMoods() {
        val dummyMoods = listOf(
            Mood("😊 Happy", "Had a great day at work!", isShared = true),
            Mood("😢 Sad", "Miss my family today.", isShared = true),
            Mood("🤩 Excited", "Just started learning Compose!", isShared = true),
            Mood("😌 Calm", "Enjoyed a peaceful walk.", isShared = true),
            Mood("😡 Angry", "Got stuck in traffic for hours!", isShared = true)
        )
        _communityMoodList.value = dummyMoods
    }
    fun likeMood(mood: Mood) {
        val updatedList = _communityMoodList.value.map {
            if (it.timestamp == mood.timestamp) it.copy(likes = it.likes + 1) else it
        }
        _communityMoodList.value = updatedList
    }

    fun addComment(mood: Mood, comment: String) {
        val updatedList = _communityMoodList.value.map {
            if (it.timestamp == mood.timestamp) {
                val newComments = it.comments.toMutableList().apply { add(comment) }
                it.copy(comments = newComments)
            } else it
        }
        _communityMoodList.value = updatedList
    }


    fun saveMood(context: Context, mood: String, note: String) {
            val moodData = hashMapOf(
                "mood" to mood,
                "note" to note,
                "timestamp" to System.currentTimeMillis()
            )

            db.collection("moods")
                .add(moodData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Mood saved successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Error saving mood: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }


