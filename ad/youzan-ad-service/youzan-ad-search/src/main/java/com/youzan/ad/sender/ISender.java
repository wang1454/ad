package com.youzan.ad.sender;

import com.youzan.ad.mysql.dto.MySqlRowData;

public interface ISender {
    void sender(MySqlRowData rowData);
}
