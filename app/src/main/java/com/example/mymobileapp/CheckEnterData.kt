package com.example.mymobileapp

class CheckEnterData {
    public fun isValidEmail(email: String): Boolean {
        // Регулярное выражение для проверки формата почты
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    
}