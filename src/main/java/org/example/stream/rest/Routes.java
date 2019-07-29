package org.example.stream.rest;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

public abstract class Routes extends AllDirectives {

    protected Route createRoute() {
        return concat(path("hello", () ->
                get(() ->
                        complete("<h1>Say hello to akka-http</h1>"))));
    }

}
