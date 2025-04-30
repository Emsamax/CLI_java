package org.acme.other;

import jakarta.enterprise.context.Dependent;


@Dependent
public class GreetingService {

    public String greeting(String name) {
        return "Hello " + name;
    }

}
