package hello;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApplicationTests {

    ConfigurableApplicationContext application1;
    ConfigurableApplicationContext application2;
    ConfigurableApplicationContext application3;

    @Before
    public void startApps() {
        this.application1 = startApp(8090);
        this.application2 = startApp(9092);
        this.application3 = startApp(9999);
    }

    @After
    public void closeApps() {
        this.application1.close();
        this.application2.close();
        this.application3.close();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldRoundRobinOverInstancesWhenCallingServicesViaRibbon() throws InterruptedException {
        ResponseEntity<String> response1 = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/hi?name=foo", String.class);
        ResponseEntity<String> response2 = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/hi?name=foo", String.class);
        ResponseEntity<String> response3 = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/hi?name=foo", String.class);

        then(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response1.getBody()).isEqualTo("1, foo!");
        then(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response2.getBody()).isEqualTo("2, foo!");
        then(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response3.getBody()).isEqualTo("3, foo!");
    }

    private ConfigurableApplicationContext startApp(int port) {
        return SpringApplication.run(TestApplication.class,
                "--server.port=" + port,
                "--spring.jmx.enabled=false");
    }

    @Configuration
    @EnableAutoConfiguration
    @RestController
    static class TestApplication {

        static AtomicInteger atomicInteger = new AtomicInteger();

        @RequestMapping(value = "/greeting")
        public Integer greet() {
            return atomicInteger.incrementAndGet();
        }

        @RequestMapping(value = "/")
        public String health() {
            return "ok";
        }
    }
}
