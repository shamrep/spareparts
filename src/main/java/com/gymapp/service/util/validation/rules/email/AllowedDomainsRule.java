package com.gymapp.service.util.validation.rules.email;

import com.gymapp.service.util.validation.rules.AbstractRule;

public class AllowedDomainsRule extends AbstractRule<String> {

    private static String[] allowedDomains = new String[]{"@gmail.com", "@outlook.com"};;

    public AllowedDomainsRule() {
        super("Invalid email domain. Allowed domains are: " + String.join(", ", allowedDomains));
    }

    public AllowedDomainsRule(String s) {
        super(s);
    }

    public boolean validate(String email) {

        for (String domain : allowedDomains) {

            if (email.toLowerCase().endsWith(domain.toLowerCase())) { // Case-insensitive match

                return true;
            }
        }

        return false;
    }

    public void addAllowedDomains(String... allowedDomains) {
        this.allowedDomains = (allowedDomains != null && allowedDomains.length > 0)
                ? allowedDomains
                : new String[]{"@gmail.com", "@outlook.com"};
    }

    @Override
    public String toString() {
        return "Invalid email domain. Allowed domains are: " + String.join(", ", allowedDomains);
    }
}
