package com.hacker.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by dinesh.rathore on 25/02/16.
 */
@Path("main")
public class MainResource {

    @GET
    public String getHello(){
            return "Hello world";
    }
}
