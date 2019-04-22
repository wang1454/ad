package com.youzan.ad.mysql.listener;

import com.youzan.ad.mysql.dto.BinLogRowData;

public interface IListener {
    void register();
    void onEvent(BinLogRowData eventData);
}
