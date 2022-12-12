package com.github.piyushpatel2005.annotations;

@MostUsed
public class Utility {
    void doStuff() {
        System.out.println("Doing stuff");
    }

    @MostUsed("Python")
    void doStuff(String str) {
        System.out.println("Doing stuff with " + str);
    }

    void doStuff(int i) {
        System.out.println("Doing stuff with integer " + String.valueOf(i) );
    }
}

class SubUtility extends Utility {

}
