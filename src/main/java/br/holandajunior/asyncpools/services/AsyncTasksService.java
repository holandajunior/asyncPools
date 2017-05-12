package br.holandajunior.asyncpools.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by holandajunior on 12/05/17.
 */

@Service
public class AsyncTasksService {

    @Async("taskExecutor")
    public Future< String > getUsersFuture() {

        System.out.println( " GetUsersFuture Async Spring is running... " + Thread.currentThread().getName() );

        try {

            Thread.sleep( 2 * 1000 );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AsyncResult< String > result = new AsyncResult<>( "Holanda Junior" );
        return result;

    }

    @Async // By default, Async without parameter will allocate thread into default task execution. In this case, taskExecutor
    public Future< String > getUsersFutureDefaultPool() {

        System.out.println( " GetUsersFuture Async Spring Default Pool is running... " + Thread.currentThread().getName() );

        try {

            Thread.sleep( 2 * 1000 );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AsyncResult< String > result = new AsyncResult<>( "Holanda Junior" );
        return result;

    }

}
