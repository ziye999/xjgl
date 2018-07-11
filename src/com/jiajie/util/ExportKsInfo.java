package com.jiajie.util;

import com.jiajie.util.bean.KsTaskInfo;
import com.jiajie.util.bean.XsInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ExportKsInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private static ExportKsInfo eki = new ExportKsInfo();

  private boolean isfull = false;

  private int allThreads = 300;

  private int usedThreads = 0;

  private Map<String, List<XsInfo>> map = new HashMap();

  private Map<String, KsTaskInfo> mmap = new HashMap();

  private Map<String, List<XsInfo>> errMap = new HashMap();

  public synchronized void removeTaskByKey(String key)
  {
    this.map.remove(key);
  }

  public synchronized void doneThreadsByKey(String key)
  {
    ((KsTaskInfo)this.mmap.get(key)).setDoneThreads(((KsTaskInfo)this.mmap.get(key)).getDoneThreads() + 1);
  }

  public boolean getStatuByKey(String key) {
    return ((KsTaskInfo)this.mmap.get(key)).isok().booleanValue();
  }

  public synchronized void putErrorData(String key, XsInfo value)
  {
    if (this.errMap.get(key) == null) {
      List list = new ArrayList();
      this.errMap.put(key, list);
    }
    ((List)this.errMap.get(key)).add(value);
  }

  public synchronized List<XsInfo> getErrorData(String key)
  {
    return (List)this.errMap.get(key);
  }

  public synchronized int isGoOk(String key)
  {
    if (this.map.containsKey(key)) {
      return -1;
    }
    return 1;
  }

  public synchronized int isGoOk(String key, List<XsInfo> value)
  {
    if (this.map.containsKey(key)) {
      return -1;
    }
    this.map.put(key, value);
    int tds = 60;
    if (value.size() / 10 < 60) {
      tds = value.size() + 1;
    }

    KsTaskInfo ks = new KsTaskInfo();
    ks.setUsedThreads(tds);

    this.mmap.put(key, ks);
    this.errMap.remove(key);
    return tds;
  }

  public boolean getTaskStatuByKey(String key)
  {
    return true;
  }

  public synchronized XsInfo assignInfoByKey(String key) {
    if ((this.map.get(key) != null) && (((List)this.map.get(key)).size() > 0)) {
      return (XsInfo)((List)this.map.get(key)).remove(0);
    }
    return null;
  }

  public synchronized void gobackUesdThreads(int tds)
  {
    this.usedThreads -= tds;
  }

  public static ExportKsInfo getInstance() {
    return eki;
  }
}