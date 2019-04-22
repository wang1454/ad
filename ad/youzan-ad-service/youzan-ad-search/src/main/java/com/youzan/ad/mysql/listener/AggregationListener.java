package com.youzan.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.youzan.ad.mysql.TemplateHolder;
import com.youzan.ad.mysql.dto.BinLogRowData;
import com.youzan.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbName;
    private String tableName;

    private Map<String,IListener> listenerMap=new HashMap<>();

    @Autowired
    TemplateHolder templateHolder;

    private String getkey(String dbName,String tableName){
        return dbName +":"+tableName;
    }
    public void register(String _bdName,String _tableName
            ,IListener ilistener){
        log.info("register :{}-{}",_bdName,_tableName);
        this.listenerMap.put(getkey(_bdName,_tableName),ilistener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();

        if (type==EventType.TABLE_MAP){
            TableMapEventData data = event.getData();
            this.dbName=data.getDatabase();
            this.tableName=data.getTable();
            return;
        }
        if (type!=EventType.EXT_UPDATE_ROWS
                &&type!=EventType.EXT_WRITE_ROWS
                &&type!=EventType.EXT_DELETE_ROWS){
            return;
        }
        //表名和库名是否已经完成填充
        if (StringUtils.isEmpty(dbName)||StringUtils.isEmpty(tableName)){
            log.error("no meta data event(没有元数据事件)");
            return;
        }

        //找出对应表有兴趣的监听器
        String key = getkey(this.dbName, this.tableName);
        IListener listener=this.listenerMap.get(key);
        if (null==listener){
            log.debug("skip{}",key);
            return;
        }
        log.info("trigger event:{}",type.name());
        try{
            BinLogRowData rowData=buildRowData(event.getData());
            if (rowData==null){
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }finally {
            this.dbName="";
            this.tableName="";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData){
        if (eventData instanceof WriteRowsEventData){
            return ((WriteRowsEventData) eventData).getRows();
        }

        if (eventData instanceof UpdateRowsEventData){
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData){
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    private BinLogRowData buildRowData(EventData eventData){
        TableTemplate table = templateHolder.getTable(tableName);
        if (null==table){
            log.warn("table {} not found",tableName);
            return null;
        }
        List<Map<String,String>> afterMapList=new ArrayList<>();
        for (Serializable[] after: getAfterValues(eventData)){
            Map<String,String> afterMap=new HashMap<>();
            int colLen = after.length;
            for (int ix=0;ix<colLen;ix++){
                //取出当前位置对应的列名
                String colName = table.getPosMap().get(ix);
                //如果没有着说明不关心这个列
                if (null==colName){
                    log.debug("ignore position:{}",ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName,colValue);
            }
            afterMapList.add(afterMap);
        }
        BinLogRowData rowData=new BinLogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
