package pers.ricardo.control;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class ConfigurationExposer {

    private Properties properties; //This means it will load the file everytime this bean is called, in order to do this it must be application scopped

    @PostConstruct
    private void initProperties(){
        try (InputStream inputStream = ConfigurationExposer.class.getResourceAsStream("/aplication.properties")) {
            properties = new Properties();
            properties.load(inputStream);
            if(inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Produces
    @Config("unsused")
    public String exposeConfig(InjectionPoint injectionPoint) {
        String key = injectionPoint.getAnnotated().getAnnotation(Config.class).value();
        //... some logic to retrieve the value with the given key, be it DB ou file.
        return properties.getProperty(key); // Example how to get the property from the properties file
    }

}
