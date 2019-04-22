package com.youzan.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class BinLogServiceTest {

    public static void main(String[] args) throws IOException {
        BinaryLogClient client =  new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "root"
        );

        client.setBinlogFilename("mysql-bin.000004");

        client.registerEventListener(
                event ->
                {
                    final EventData data = event.getData();

                    if(data instanceof UpdateRowsEventData){
                        log.info("update--------------------");
                        log.info(data.toString());
                    }

                    if(data instanceof WriteRowsEventData){
                        log.info("insert--------------------------");
                        log.info(data.toString());
                    }

                    if(data instanceof DeleteRowsEventData){
                        log.info("delete-------------------");
                        log.info(data.toString());
                    }
                }
        );

        client.connect();

    }
}
