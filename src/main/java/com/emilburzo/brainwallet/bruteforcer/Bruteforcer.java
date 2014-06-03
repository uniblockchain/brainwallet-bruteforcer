package com.emilburzo.brainwallet.bruteforcer;

import java.util.List;
import java.util.Map;

public class Bruteforcer extends Thread {

    private Map<String, Double> balances;
    private List<String> passwords;

    public Bruteforcer(Map<String, Double> balances, List<String> passwords) {
        this.balances = balances;
        this.passwords = passwords;
    }

    @Override
    public void run() {
        System.out.println(String.format("[*] Trying %d passwords", passwords.size()));

        int size = passwords.size();
        long start = System.currentTimeMillis();
        int counter = 0;

        for (int i = 0; i < size; i++) {
            String pw = passwords.get(i);

            long elapsed = System.currentTimeMillis() - start;

            if (elapsed >= 1 /* minutes */ * 60 * 1000) {
                System.out.println(String.format("Trying: '%30s'\t (%5d/min)\t (%10d of %10d)",
                        pw, counter, i, size));
                start = System.currentTimeMillis();
                counter = 0;
            }

            isValid(pw);

            counter++;
        }
    }

    private void isValid(String input) {
        String publicAddr = DigestUtil.getPublicAddress(input);

        if (balances.containsKey(publicAddr)) {
            System.out.println(String.format("[!] FOUND: '%s'", input));
        }
    }
}
