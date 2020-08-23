package components.repository;

import com.web.app.ApplicationRunner;
import com.web.app.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationRunner.class)
@Transactional
@Slf4j
@EnableJpaRepositories
public class RepositoryTests {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Rollback(value = false)
    public void testDelete() {
        usersRepository.deleteAll();
    }
}
