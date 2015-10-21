package com.hw.corcow.sampledatabase;

import android.provider.BaseColumns;

/**
 * Created by Tacademy on 2015-10-21.
 */
public class AddressDB {
    /**
     * 쿼리문을 날릴 때
     * Query의 key를 String으로 넣는 과정에서 오타를 낼 수 있고, 이 오타는 찾아내기 매우 어려움.
     * 따라서 key값의 String은 상수로 정의해서 사용하되
     * 다음과 같은 interface를 만들어서 정의하라.
     */

    public interface AddressTable extends BaseColumns {
        public static final String TABLE_NAME = "addressTable";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_OFFICE = "office";

    }

}
