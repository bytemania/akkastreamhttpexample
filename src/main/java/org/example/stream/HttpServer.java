package org.example.stream;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.example.stream.rest.Routes;

import java.util.concurrent.CompletionStage;

public class HttpServer extends Routes {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("routes");

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        HttpServer app = new HttpServer();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                app.createRoute().flow(system, materializer);

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to Stop...");
        System.in.read();

        binding.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());

    }


}
