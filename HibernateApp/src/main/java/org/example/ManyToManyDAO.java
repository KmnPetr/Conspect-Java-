package org.example;

import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManyToManyDAO {
    private Configuration configuration;
    private SessionFactory sessionFactory;

    public ManyToManyDAO() {
        //фууу как некрасиво
        this.configuration = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);
    }

    /**
     * получим все заказы человека с id 3
     */
    public void pushMovieAndActors() {
        //try-with-resurses сам закроет ресурс
        // в скобочки можно было воткнуть просто sessionFactory но компилятор заставлял меня сделать ее final поэтому немного переиначил
        try(SessionFactory sessionFactory=configuration.buildSessionFactory()){
            Session session=sessionFactory.getCurrentSession();
            session.beginTransaction();

            Movie movie=new Movie("Pulp fiction",1994);
            Actor actor=new Actor("Harvey Keitel",81);
            Actor actor1=new Actor("Samuel L. Jacson",72);
            movie.setActors(new ArrayList<>(List.of(actor,actor1)));//это новая неизменяемая конструкция но в итоге все равно получится изменяемый аррэй лист
            actor.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            session.persist(movie);
            session.persist(actor);
            session.persist(actor1);

            session.getTransaction().commit();
        }
    }

    public void getMovieWithActors() {
        try(SessionFactory sessionFactory=configuration.buildSessionFactory()){
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.get(Movie.class,1)
                .getActors()
                .forEach(actor -> System.out.println(actor.getName()+", "+actor.getAge()));

        session.getTransaction().commit();
    }
    }

    public void pushMovieWithOldActors() {
        try(SessionFactory sessionFactory=configuration.buildSessionFactory()){
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();

        Movie movie=new Movie("Reservoir Dogs",1992);
        Actor actor=session.get(Actor.class,1);
        movie.setActors(new ArrayList<>(Collections.singletonList(actor)));
        actor.getMovies().add(movie);
        session.persist(movie);

        session.getTransaction().commit();
    }
    }

    public void deleteActorInMovie() {
        try(SessionFactory sessionFactory=configuration.buildSessionFactory()){
        Session session=sessionFactory.getCurrentSession();
        session.beginTransaction();

        Actor actor=session.get(Actor.class,2);
        System.out.println(actor.getMovies());
        Movie movieToRemove=actor.getMovies().get(0);
        actor.getMovies().remove(0);
        movieToRemove.getActors().remove(actor);//для этого действия необходимо переопределить equals() и hashCode()

        session.getTransaction().commit();
    }
    }
}



































