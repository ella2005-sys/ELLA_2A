package CONFIG;

public class Session {

    // ================= CURRENT USER DATA =================
    private static int userId = 0;
    private static String name;
    private static String address;
    private static String number;
    private static String email;
    private static String role;
    private static String status;

    // Prevent instantiation
    private Session() {}

    // ================= SET SESSION =================
    public static void setSession(int id, String n, String addr, 
                                  String num, String mail, 
                                  String r, String stat) {

        userId = id;
        name = n;
        address = addr;
        number = num;
        email = mail;
        role = r;
        status = stat;

        System.out.println("Session Started for User ID: " + userId);
    }

    // ================= GETTERS =================
    public static int getUserId() {
        return userId;
    }

    public static String getName() {
        return name;
    }

    public static String getAddress() {
        return address;
    }

    public static String getNumber() {
        return number;
    }

    public static String getEmail() {
        return email;
    }

    public static String getRole() {
        return role;
    }

    public static String getStatus() {
        return status;
    }

    // ================= CHECK LOGIN =================
    public static boolean isLoggedIn() {
        return userId != 0;
    }

    // ================= CLEAR SESSION =================
    public static void clearSession() {
        userId = 0;
        name = null;
        address = null;
        number = null;
        email = null;
        role = null;
        status = null;

        System.out.println("Session Cleared.");
    }
}
