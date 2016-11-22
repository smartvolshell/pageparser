import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.volshell.entity.*;

/**
 * Created by volshell on 16-11-19.
 */
public class DBTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-hibernate.xml");
        sessionFactory = context.getBean(SessionFactory.class);
    }

    @Test
    public void testConnection() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Category.class);
        criteria.add(Restrictions.eq("c_id", 2));
        criteria.list().forEach(each -> {
            System.out.println(each);
        });
    }
}
