package by.gto.test.helpers.pools;

import by.gto.test.helpers.PooledSimpleDateFormat;

public class Pools {

    public static final ObjectPool<PooledSimpleDateFormat> formatDateAndTime4Json = new ObjectPool<PooledSimpleDateFormat>(10) {
        protected PooledSimpleDateFormat createObject() {
            return new PooledSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX:00", this);
        }
    };

    public static final ObjectPool<PooledSimpleDateFormat> formatDate4Json = new ObjectPool<PooledSimpleDateFormat>(10) {
        @Override
        protected PooledSimpleDateFormat createObject() {
            return new PooledSimpleDateFormat("yyyy-MM-dd", this);
        }
    };

    public static final ObjectPool<PooledSimpleDateFormat> formatDDMMYYYY = new ObjectPool<PooledSimpleDateFormat>(6) {
        @Override
        protected PooledSimpleDateFormat createObject() {
            return new PooledSimpleDateFormat("dd.MM.yyyy", this);
        }
    };
}

