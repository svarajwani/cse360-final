package utils;

public final class Session {
    private static String user;
    private static String role;

    public static void login(String u, String r) { user = u; role = r; }
    public static String user() { return user; }
    public static String role() { return role; }
}
