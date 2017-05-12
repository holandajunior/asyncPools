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
    public Future< List<String> > getUsersFuture() {

        System.out.println( " GetUsersFuture Async Spring is running... " + Thread.currentThread().getName() );

        List<String> names = new ArrayList<String>();
        names.add( "Holanda Junior" );

        try {

            Thread.sleep( 5 * 1000 );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AsyncResult< List<String> > result = new AsyncResult<>( names );
        return result;

    }

}
