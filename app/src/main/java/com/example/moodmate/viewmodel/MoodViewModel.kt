package com.example.moodmate.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoodViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val database = FirebaseDatabase.getInstance()

    private val _moodList = MutableStateFlow<List<Mood>>(emptyList())
    val moodList: StateFlow<List<Mood>> = _moodList

    private val _communityMoodList = MutableStateFlow<List<Mood>>(emptyList())
    val communityMoodList: StateFlow<List<Mood>> = _communityMoodList

    data class Mood(
        val mood: String = "",
        val note: String = "",
        val timestamp: Long = System.currentTimeMillis(),
        val isShared: Boolean = false,
        val username: String = "Anonymous",
        val profileImageUrl: String = "",
        var likes: Int = 0,
        var comments: MutableList<String> = mutableListOf()
    )

    // Fetch personal moods from Firestore
    fun fetchMoods() {
        db.collection("moods")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { snapshot ->
                val moods = snapshot.documents.mapNotNull { it.toObject(Mood::class.java) }
                _moodList.value = moods
            }
            .addOnFailureListener {
                // Handle failure if needed
            }
    }

    // Dummy community moods (you can later load from Firestore or Realtime DB)
    fun fetchCommunityMoods() {
        val dummyMoods = listOf(
            Mood("😊 Happy", "Had a great day at work!", isShared = true, timestamp = 1L),
            Mood("😢 Sad", "Miss my family today.", isShared = true, timestamp = 2L),
            Mood("🤩 Excited", "Just started learning Compose!", isShared = true, timestamp = 3L),
            Mood("😌 Calm", "Enjoyed a peaceful walk.", isShared = true, timestamp = 4L),
            Mood("😡 Angry", "Got stuck in traffic for hours!", isShared = true, timestamp = 5L)
        )
        _communityMoodList.value = dummyMoods
    }

    // Like mood — updates local and Realtime Database
    fun likeMood(mood: Mood) {
        val updatedList = _communityMoodList.value.map {
            if (it.timestamp == mood.timestamp) it.copy(likes = it.likes + 1) else it
        }
        _communityMoodList.value = updatedList

        // Update likes in Realtime DB
        val postId = mood.timestamp.toString()
        val likesRef = database.getReference("likes").child(postId)

        likesRef.get().addOnSuccessListener { snapshot ->
            val currentLikes = snapshot.getValue(Int::class.java) ?: 0
            likesRef.setValue(currentLikes + 1)
        }
    }

    // Add comment locally
    fun addComment(mood: Mood, comment: String) {
        val updatedList = _communityMoodList.value.map {
            if (it.timestamp == mood.timestamp) {
                val newComments = it.comments.toMutableList().apply { add(comment) }
                it.copy(comments = newComments)
            } else it
        }
        _communityMoodList.value = updatedList
    }

    // Save mood to Firestore
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
                Toast.makeText(context, "Error saving mood: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
