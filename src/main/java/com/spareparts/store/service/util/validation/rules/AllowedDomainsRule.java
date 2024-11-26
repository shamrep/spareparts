package com.spareparts.store.service.util.validation.rules;

public class AllowedDomainsRule implements ValidationRule<String> {

    private final String[] allowedDomains;


    public AllowedDomainsRule(String... allowedDomains) {

        this.allowedDomains = (allowedDomains != null && allowedDomains.length > 0)
                ? allowedDomains
                : new String[]{"@gmail.com", "@outlook.com"};
    }


    @Override
    public boolean validate(String email) {

        for (String domain : allowedDomains) {

            if (email.toLowerCase().endsWith(domain.toLowerCase())) { // Case-insensitive match

                return true;
            }
        }

        return false;
    }

    @Override
    public String getMessage() {

        return "Invalid email domain. Allowed domains are: " + String.join(", ", allowedDomains);
    }
}
