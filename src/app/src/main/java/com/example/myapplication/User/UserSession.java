package com.example.myapplication.User;
/**
 * UserSession
 *
 * Diese Klasse speichert den aktuell eingeloggten Nutzer global im Speicher.
 * Sie ist so lange gültig, wie die App läuft.
 *
 */
public class UserSession {
    // Der aktuell eingeloggte Nutzer
    private static User currentUser = null;

    /**
     * Setzt den aktuell eingeloggten Nutzer.
     */
    public static void setUser(User user) {
        currentUser = user;
    }

    /**
     * Gibt den aktuell eingeloggten Nutzer zurück.
     */
    public static User getUser() {
        return currentUser;
    }

    /**
     * Löscht die Session (z. B. beim Logout).
     */
    public static void clear() {
        currentUser = null;
    }

    /**
     * Prüft, ob ein Nutzer eingeloggt ist.
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
