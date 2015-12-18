package dotadodge.core;

import com.google.inject.Injector;
import dotadodge.core.db.ApplicationInitializer;
import dotadodge.core.db.GlobalStatisticDao;
import dotadodge.core.misc.GuiceFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 18.12.2015.
 */
public class GlobalTest {
    @Test
    public void test() throws IOException {

        Injector injector = GuiceFactory.getInjector();
        GlobalStatisticDao globalStatisticDao = injector.getInstance(GlobalStatisticDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.add(49716582);
        ids.add(186782126);
        ids.add(71146225);
        ids.add(110645196);
        ids.add(26559018);
        List<String> blabla = new ArrayList<>(globalStatisticDao.getWinRate(ids));
        System.out.println(blabla);
    }
}