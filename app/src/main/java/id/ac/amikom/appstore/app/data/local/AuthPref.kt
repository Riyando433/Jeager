package id.ac.amikom.appstore.app.data.local

import android.content.Context
import android.content.SharedPreferences
import id.ac.amikom.appstore.app.data.model.ActionState
import id.ac.amikom.appstore.app.data.model.AuthUser
import id.ac.amikom.appstore.app.util.getObject
import id.ac.amikom.appstore.app.util.putObject

class AuthPref(val context: Context) {
    private val sp: SharedPreferences by lazy{
        context.getSharedPreferences(AuthPref::class.java.name, Context.MODE_PRIVATE)
    }

    private companion object{
        const val AUTH_USER = "auth_user"
        const val IS_LOGIN = "is_login"
    }

    var authUser: AuthUser?
    get() = sp.getObject(AUTH_USER)
    private set(value) = sp.edit().putObject(AUTH_USER, value).apply()

    var isLogin: Boolean
    get() = sp.getBoolean(IS_LOGIN,false)
    private set(value) = sp.edit().putBoolean(IS_LOGIN, value).apply()

    suspend fun login(email: String,password: String): ActionState<AuthUser>{
        val user = authUser
        if (user == null){
            return ActionState(message = "Anda belum terdaftar", isSucces = false)
        }else if (email.isBlank() || password.isBlank()){
            return ActionState(message = "Email dan password tidak boleh kosong", isSucces = false)
        }else if (user.email == email && user.password == password){
            isLogin = true
            return ActionState(authUser, message = "Anda berhasi login")
        } else {
           return ActionState(message = "Email atau passwordnya salah", isSucces = false)
        }
    }

    suspend fun register(user: AuthUser): ActionState<AuthUser>{
        return if (user.email.isBlank() || user.password.isBlank()){
            ActionState(message = "Email dan password tidak bolaeh kosong", isSucces = false)
        } else{
            authUser = user
            ActionState(user, message = "Anda barhasil daftar")
        }
    }

    suspend fun logout(): ActionState<Boolean>{
        isLogin = false
        return ActionState(true, message = "Anda berhasil logout")
    }
}