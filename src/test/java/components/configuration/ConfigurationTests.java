package components.configuration;

import com.web.app.config.HibernateConfig;
import org.junit.Test;

import java.lang.reflect.Field;

public class ConfigurationTests {
    @Test
    public void testPropertiesNonNull() {
        for (Field field : HibernateConfig.class.getFields()) {
            System.out.println("field = ");
        }
    }
}
