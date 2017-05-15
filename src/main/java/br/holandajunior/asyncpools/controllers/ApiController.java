package br.holandajunior.asyncpools.controllers;

import br.holandajunior.asyncpools.services.AsyncTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
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

    //it will execute async task allowing servlet's thread execute another request
    @RequestMapping("/users/asyncCompletable")
    public DeferredResult< String > getUsersAsync() {

        DeferredResult< String > result = new DeferredResult< String >();


        // The difference between runAsync and supplyAsync is just supplyAsync provide a return value because it waits for Supply<U> instance
        CompletableFuture.runAsync( () -> {

            System.out.println( " GetAsyncCompletable is running... " + Thread.currentThread().getName() );

            try {

                Thread.sleep( 2 * 1000 );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            result.setResult( "Holanda Junior");
        });

        System.out.println( " Servlet thread freed... " );

        return result;
    }

    //it will execute async task allowing servlet's thread execute another request
    @RequestMapping("/users/asyncCompletableOther")
    public DeferredResult<String> getUsersAsyncOther() {

        DeferredResult< String > result = new DeferredResult< String >();

        CompletableFuture.supplyAsync( () -> {

            System.out.println( " GetAsyncCompletableOther is running... " + Thread.currentThread().getName() );

            try {

                Thread.sleep( 2 * 1000 );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Holanda Junior";

        }).whenCompleteAsync( ( value, throwable ) -> {

            System.out.println( " GetAsyncCompletableOther result is being returned... " + Thread.currentThread().getName() );
            result.setResult( value );
        } );

        System.out.println( " Servlet thread freed... " );

        return result;
    }

    //it will execute async task allowing servlet's thread execute another request
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

    // Such tasks will wait for both results, then blocking servlet's thread
    @RequestMapping("/users/asyncSpring")
    public String getUsersAsyncSpring() {

        try {

            String result = asyncTasksService.getUsersFuture().get();
            String resultOther = asyncTasksService.getUsersFutureDefaultPool().get();

            System.out.println( " Servlet thread freed... " );
            System.out.println( " Async Spring returning..." );

            return result + resultOther;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping( "/users/asyncCallable" )
    public Callable<String> getUserCallable() {

        Callable<String> result = asyncTasksService::getName;
        System.out.println( " Servlet thread freed... " );

        return result;
    }


}
