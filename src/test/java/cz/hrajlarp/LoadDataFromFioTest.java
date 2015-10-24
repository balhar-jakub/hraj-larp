package cz.hrajlarp;

import cz.hrajlarp.model.Bank;
import org.junit.Test;

/**
 * Created by jbalhar on 24. 10. 2015.
 */
public class LoadDataFromFioTest {
    @Test
    public void loadData() {
        new Bank().loadData();
    }
}
