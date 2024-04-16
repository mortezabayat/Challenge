package co.meteto.cat.core.app

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.snapshots.SnapshotStateList
import co.meteto.cat.domain.model.Cat
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AppPreferences {
    //key
    private const val FAVE = "fave"
    private const val CAT = "CAT"
    private var mPreferences: SharedPreferences? = null
    private val gson = Gson()
    private val _faves = SnapshotStateList<Cat>()

    fun subscribe() = _faves

    fun initPreferences(context: Context) {
        mPreferences = context.getSharedPreferences("FaveData", Context.MODE_PRIVATE)
        val jsonData = mPreferences?.getString(FAVE, "") ?: ""
        if (jsonData.isNotEmpty()) {
            _faves.addAll(gson.fromJson(jsonData, Array<Cat>::class.java).asList())
        }
    }

    fun onDispose() {
        val jsonData = gson.toJson(_faves)
        mPreferences?.run {
            edit().putString(FAVE, jsonData).apply()
        }
    }

    fun saveBookMark(cat: Cat) {
        if (!_faves.contains(cat)) {
            _faves.add(cat)
        }
    }
    fun removeBookMarks() {
        _faves.clear()
    }

    fun saveCatData(cat: String) {
        mPreferences?.run {
            edit().putString(CAT, cat).apply()
        }
    }

    fun getCatData(): Cat {
        val jsonData = mPreferences?.getString(CAT, "") ?: ""
        if (jsonData.isNotEmpty()) {
            return gson.fromJson(jsonData, Cat::class.java)
        }
        return Cat()
    }
}