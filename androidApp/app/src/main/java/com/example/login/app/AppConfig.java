package com.example.login.app;

public class AppConfig {
    // Server user login url http://192.168.1.67/
    public static String URL_LOGIN = "http://192.168.1.66/android_login_api/include/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://192.168.1.66/android_login_api/include/register.php";

    //course list
    public static String url_Courses = "http://192.168.1.66/android_login_api/include/populate_CourseList.php";

    //booklist
    //https://run.mocky.io/v3/6e35600c-eca1-445e-8a4b-1835b8783a55
    public  static String URL_BOOKLIST="http://192.168.1.66/android_login_api/include/populate_BookList.php";
    //JSON array name
    public static final String JSON_ARRAY = "courses";
}
