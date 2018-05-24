package pl.bladekp.dbbenchmark

import spock.lang.Specification

class BenchamrkControllerTest extends Specification{

    def setup() {
        System.out.println("setup")
    }          // run before every feature method
    def cleanup() {
        System.out.println("cleanup")
    }        // run after every feature method
    def setupSpec() {
        System.out.println("setupSpec")
    }     // run before the first feature method
    def cleanupSpec() {
        System.out.println("cleanUpSpec")
    }   // run after the last feature method

    def "hello world"() {
        setup:
            Stack<Integer> stack = new Stack<>();

        when:
            stack.push(9876)
        then:
            !stack.empty()
            stack.size() == 1
            stack.peek() == 9876
    }

}
