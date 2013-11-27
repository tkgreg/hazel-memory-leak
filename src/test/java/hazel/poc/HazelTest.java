package hazel.poc;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * User: greg
 */

@ContextConfiguration(locations = {"classpath:test-context.xml"})
public class HazelTest extends AbstractTestNGSpringContextTests {

    public static final int COUNT = 200000;
    @Autowired
    HazelcastInstance instance;

    int counter = 0;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String dataPrefix = "dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.[1] Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n" +
            "\" +\n" +
            "                    \"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.";

    @Test
    public void simpleTest() throws InterruptedException {
        final IMap<Long, String> simpleMap = instance.getMap("map");

        simpleMap.addEntryListener(new EntryListener<Long, String>() {

            private int i = 0;

            @Override
            public void entryAdded(EntryEvent<Long, String> event) {

            }

            @Override
            public void entryRemoved(EntryEvent<Long, String> event) {

            }

            @Override
            public void entryUpdated(EntryEvent<Long, String> event) {

            }

            @Override
            public void entryEvicted(EntryEvent<Long, String> event) {
                i++;
                System.out.println("Evicted - " + i + " Counter - " + counter);
            }
        }, false);
        System.out.println("Lock elements");
        for (long i = 0; i < COUNT; i++) {
            simpleMap.put(i, dataPrefix + i);
            counter++;
            if (i % 20000 == 0) {
                simpleMap.lock(i);
            }

            if (i % 40000 == 0) {
                if (i != 0) {
                    simpleMap.unlock(i - 20000);
                    System.out.println("element unlocked " + i);
                }
            }
        }

        for (long i = 0; i < COUNT; i++) {
            String str = simpleMap.get(i);

            if (i % 1000 == 0) {

                System.out.println(i);
            }
        }

    }

}
