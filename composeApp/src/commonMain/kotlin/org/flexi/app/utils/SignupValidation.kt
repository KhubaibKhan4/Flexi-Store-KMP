package org.flexi.app.utils

object SignupValidation {
    fun validateUsername(username: String): String?{
        return if (username.length < 6){
            "Username should be at least 6 characters Long"
        }else{
            null
        }
    }
    fun validateEmail(email: String): String? {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}".toRegex()
        return if (!email.matches(emailPattern)) {
            "Invalid email format"
        } else {
            null
        }
    }

    fun validatePassword(password: String): String? {
        return if (password.length < 8 || !password.contains(Regex(".*[A-Z].*")) || !password.contains(Regex(".*[^A-Za-z0-9].*"))) {
            "Password should be at least 8 characters long and contain at least one uppercase letter and one special character"
        } else {
            null
        }
    }

    fun validateConfirmPassword(password: String, cpassword: String): String? {
        return if (cpassword != password) {
            "Passwords do not match"
        } else {
            null
        }
    }
}