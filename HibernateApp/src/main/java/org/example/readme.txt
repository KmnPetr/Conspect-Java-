
        try {
            session.beginTransaction();
            Person person=session.get(Person.class,1/*это @Id*/);

            System.out.println(person.getName());
            System.out.println(person.getAge());

            session.getTransaction().commit();//применили транзакцию
        }finally {
            sessionFactory.close();
        }
        //////////////////////////////////////
        try {
            session.beginTransaction();

            Person person1=new Person("Test1",30);
            Person person2=new Person("Test2",40);
            Person person3=new Person("Test3",50);
//            session.save(person1);//устарела
            session.persist(person1);
            session.persist(person2);
            session.persist(person3);

            session.getTransaction().commit();//применили транзакцию
        }finally {
            sessionFactory.close();
        }
        /////////////////////////////////
        try {
            session.beginTransaction();

            Person person=session.get(Person.class,2);//сначала получим чела по id
            person.setName("NewName");
            //hibernate сам вызовет update
            session.getTransaction().commit();//применили транзакцию
        }finally {
            sessionFactory.close();
        }
        //////////////////////////////
        try {
            session.beginTransaction();
            Person person=session.get(Person.class,1);
//            session.delete(person);//устарела
            session.remove(person);//вызовет delete

            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }
        ////////////////////////////////////
        try {
            session.beginTransaction();

            Person person=new Person("SomePerson",60);
            session.persist(person);

            session.getTransaction().commit();

            System.out.println(person.getId());
        }finally {
            sessionFactory.close();
        }
        ///////////////////////////////////////////////////
                    List<Person> people=session.createQuery("FROM Person WHERE age>30").getResultList();
//////////////////////////////////////////////////////////////
            List<Person> people=session.createQuery("FROM Person WHERE name LIKE 'T%'" ).getResultList();//запрос по патерну всех людей с заглавной буквы "T"
//////////////////////////////////
            session.createQuery("update Person set name='Test'where age<30" ).executeUpdate();//у всех младше 30 зададим имя тест
///////////////////////////////////////
            session.createQuery("delete Person where age<30" ).executeUpdate();//удалим
