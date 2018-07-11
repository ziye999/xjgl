package com.jiajie.util;

import java.util.Date;

public class TimeToStr
{
  public static synchronized String getTimeStr()
  {
    String s = new Date().getTime()+"";
    try {
      Thread.sleep(1L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return s;
  }
}