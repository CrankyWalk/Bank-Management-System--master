package edu.bank.web;

/** Run from the IDE to generate a PBKDF2 hash for a demo account. */
public final class PasswordHashTool {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: PasswordHashTool <demo-password>");
            System.exit(2);
        }
        System.out.println(PasswordUtil.hash(args[0].toCharArray()));
    }
}
