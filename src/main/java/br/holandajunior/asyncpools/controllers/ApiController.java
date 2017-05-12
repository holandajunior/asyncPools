package br.holandajunior.asyncpools.controllers;

import br.holandajunior.asyncpools.services.AsyncTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * Created by holandajunior on 12/05/17.
 */

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private Executor taskExecutor;
    @Autowired
    private AsyncTasksService asyncTasksService;

    @RequestMapping("/users/asyncCompletable")
    public DeferredResult<List<String>> getUsersAsync() {

        DeferredResult< List<String> > result = new DeferredResult< List<String> >();

        CompletableFuture.runAsync( () -> {

            System.out.println( " GetUsersAsync is running... " + Thread.currentThread().getName() );
            List<String> names = new ArrayList<String>();
            names.add( "Holanda Junior" );

            try {

                Thread.sleep( 2 * 1000 );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            result.setResult( names );
        });

        System.out.println( " Servlet thread freed... " );

        return result;
    }

    @RequestMapping("/users/asyncCompletableOtherPool")
    public DeferredResult< String > getUsersAsyncOtherPool() {

        DeferredResult< String > result = new DeferredResult< String >();

        CompletableFuture.runAsync( () -> {

            System.out.println( " GetUsersAsyncOtherPool is running... " + Thread.currentThread().getName() );


            try {

                Thread.sleep( 2 * 1000 );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            result.setResult( "Holanda Junior" );

        },  taskExecutor );

        System.out.println( " Servlet thread freed... " );

        return result;
    }

    @RequestMapping("/users/asyncSpring")
    public String getUsersAsyncSpring() {

        try {

            String result = asyncTasksService.getUsersFuture().get();
            String resultOther = asyncTasksService.getUsersFutureDefaultPool().get();

            System.out.println( " Servlet thread freed... " );
            System.out.println( "Async Spring returning..." );

            return result + resultOther;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }



}
