package com.example.springrest.DAO;

import com.example.springrest.model.Person;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * для работы JdbcTemplate в файле application.properties добавляем строки
 * spring.datasource.url=jdbc:postgresql://localhost:5432/first_db
 * spring.datasource.username=postgres
 * spring.datasource.password=1234567890
 * spring.datasourse.platform=postgresql
 * и добавляем кучу кода снизу который найдешь
 * о добавлении доп.полей в pom.xml уже и не помню возможно нужен "postgresql" и "spring-boot-starter-jdbc" последней версии
 */
@Repository//та же аннотация что и @Component
public class DAO_with_Template extends JdbcDaoSupport implements DAO{
    DataSource dataSource;
    @Autowired
    public DAO_with_Template(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    /*
    //можно добавить но не нужно этот JdbcTemplate заменяет getJdbcTemplate() можно и createJdbcTemplate(dataSource)
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DAO_with_Template(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/
    //здесь раньше был код подключения к БД но теперь его нет:) его заменили DataSource, JdbcDaoSupport и JdbcTemplate
    @Override
    public List<Person> index(){
        return getJdbcTemplate().query("SELECT*FROM Person",new BeanPropertyRowMapper<>(Person.class));
    }
    @Override
    public Person show(int id){
        return getJdbcTemplate().query("SELECT*FROM Person WhERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        //здесь по умолчанию всегда используется PreparedStatement,поэтому он ждет что мы вместо "?" подставим значение
        //лямбду использовали чтобы возвращался один обьект а не список
        //в реал.прил. лучше вернуть не null, а new Error("человек с таким id не найден")
    }

    /**
     * этот метод используется для валидации при проверке на уникальность email создаваемого нового Person
     * @param email
     * @return
     */
    public Optional<Person> show(String email){
        return getJdbcTemplate().query("SELECT*FROM Person WHERE email=?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
        //можно было добавить .orElse(null) но мы использовали Optional<Person> эта обертка может не содержать обьекта
    }

    /**
     * добавляем в базу новый обьект
     * @param person
     */
    @Override
    public void save(Person person) {
        getJdbcTemplate().update("INSERT INTO Person(name,age,email,address) VALUES(?,?,?,?)",
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress());
        //в качестве последнего аргумента update() принимает @Nullable Object... args что указывает на любое количество аргументов кот.будут приняты как массив
    }

    /**
     * метод обновляет поле "name" в одном из обьектов списка "people"
     * @param id обьекта чьи данные нужно обновить
     * @param upPerson обьект из которого нужно взять новые данные для обьекта из списка
     */
    @Override
    public void update(int id, Person upPerson) {
        getJdbcTemplate().update("UPDATE Person SET name=?,age=?,email=?,address=? WHERE id=?",
                upPerson.getName(),
                upPerson.getAge(),
                upPerson.getEmail(),
                upPerson.getAddress(),
                id);//в отличии от метода query(), id передается не new Object[]{id} а просто id
    }

    /**
     * метод удалит обьект из списка
     * @param id обьекта на удаление
     */
    @Override
    public void delete(int id) {
        getJdbcTemplate().update("DELETE FROM Person WHERE id=?",id);
    }

    /**
     * метод изменяет имя человека, выбранного админом со страницы /admin
     * @param person
     */
    @Override
    public void addAdmin(Person person) {
        final String[] name = new String[1];
        getJdbcTemplate().query("SELECT name FROM Person WHERE id=?",new Object[]{person.getId()}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                name[0] =rs.getString("name");;
                return null;
            }
        });
        name[0] +=" hi is admin";
        getJdbcTemplate().update("UPDATE Person SET name=? WHERE id=?", name[0],person.getId());
    }


    //////////////////////////////////////////////////////////
    ////////////// тестируем BatchUpdate /////////////////////
    //////////////////////////////////////////////////////////

    /**
     * метод делает запрос на insert 1000 полей в БД каждый запрос по отдельности
     */
    public void testMultipleUpdate() {
        List<Person> people=create1000People();
        long before=System.currentTimeMillis();

        for(Person person:people){
            getJdbcTemplate().update("INSERT INTO Person(name,age,email,address) VALUES (?,?,?,?)",
                    person.getName(),
                    person.getAge(),
                    person.getEmail(),
                    person.getAddress());
        }

        long after=System.currentTimeMillis();
        System.out.println(after-before);//замер времени
    }

    /**
     * метод делает запрос на insert 1000 полей в БД способом BatchUpdate
     */
    public void testBatchUpdate() {
        List<Person>people=create1000People();
        long before=System.currentTimeMillis();

        //далее метод batchUpdate создает пакет с 1000 запросами и отправляет в БД
        getJdbcTemplate().batchUpdate("INSERT INTO Person(name,age,email,address) VALUES (?,?,?,?)",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1,people.get(i).getName());
                ps.setInt(2,people.get(i).getAge());
                ps.setString(3,people.get(i).getEmail());
                ps.setString(4,people.get(i).getAddress());
            }
            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        long after=System.currentTimeMillis();
        System.out.println(after-before);//замер времени
    }

    /**
     * @return возвращает массив с 1000 Person
     */
    private List<Person> create1000People() {
        List<Person>people=new ArrayList<>();
        for (int i = 1000; i < 2000; i++) {
            people.add(new Person(0,
                    "Person"+i,
                    30,
                    "Person"+i+"@gmail.com",
                    "Country, City, 123456"));
        }
        return people;
    }
}

