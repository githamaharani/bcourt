package com.bcourt.util;

public class UserSession {

    private static int idPengguna;
    private static String nama;
    private static String username;
    private static String role;

    public static void setUser(
            int id,
            String namaPengguna,
            String usernamePengguna,
            String rolePengguna) {

        idPengguna = id;
        nama = namaPengguna;
        username = usernamePengguna;
        role = rolePengguna;
    }

    public static int getIdPengguna() {
        return idPengguna;
    }

    public static String getNama() {
        return nama;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return idPengguna > 0;
    }

    public static void clear() {
        idPengguna = 0;
        nama = null;
        username = null;
        role = null;
    }
}
