package by.gto.test.helpers;

import by.gto.test.helpers.pools.ObjectPool;
import java.text.SimpleDateFormat;

public class PooledSimpleDateFormat extends SimpleDateFormat implements AutoCloseable {
    private ObjectPool<PooledSimpleDateFormat> master = null;

    public PooledSimpleDateFormat(String pattern, ObjectPool<PooledSimpleDateFormat> master) {
        super(pattern);
        this.master = master;
    }

    @Override
    public void close() {
        if (master != null) {
            master.returnObject(this);
        }
    }
}
