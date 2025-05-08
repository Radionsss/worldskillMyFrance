package coffee.company.lintu.domain

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

//class SharedPreferencesManager @Inject constructor(context: Context) {
class SharedPreferencesManager(context: Context) {
    private val gson = Gson()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    ////////////////////////////////
     fun saveIsFirstSignIn(name: Boolean) {
        editor.putBoolean("firstSignIn", name)
        editor.apply()
    }

    fun getIsFirstSignIn(): Boolean {
        val getIsNeed = sharedPreferences.getBoolean("firstSignIn", true)
        return getIsNeed
    }
    ////////////////////////////////
     fun saveIsNeedNotification(name: Boolean) {
        editor.putBoolean("isNeedNotification", name)
        editor.apply()
    }

    fun getIsNeedNotification(): Boolean {
        val getIsNeed = sharedPreferences.getBoolean("isNeedNotification", true)
        return getIsNeed
    }
    ////////////////////////////////
     fun saveShopUserWhenUse(name: Int) {
        editor.putInt("shopUserWhenUse", name)
        editor.apply()
    }

    fun getShopUserWhenUse(): Int {
        val getIsNeed = sharedPreferences.getInt("shopUserWhenUse", -1)
        return getIsNeed
    } ////////////////////////////////
     fun saveUserToken(id: String) {
        editor.putString("userToken", id)
        editor.apply()
    }

    fun getUserToken(): String? {
        val getIsNeed = sharedPreferences.getString("userToken", "")
        return getIsNeed
    }
}
