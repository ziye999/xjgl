package com.jiajie.service.dailyManage.impl;

import com.jiajie.bean.MsgBean;
import com.jiajie.bean.PageBean;
import com.jiajie.service.ServiceBridge;
import com.jiajie.service.dailyManage.PhotoService;
import com.jiajie.util.bean.MBspInfo;
import com.jiajie.util.bean.MBspOrgan;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

@Service("photoService")
public class PhotoServiceImpl extends ServiceBridge
  implements PhotoService
{
  public static void main(String[] args)
    throws ParseException
  {
    System.out.println("".equals(null));
  }

  public List<Map<String, Object>> checkCardID(String cardID) {
    String sql = "select XX_JBXX_ID from ZXXS_XS_JBXX where sfzjh='" + cardID + "'";
    List list = getSession().createSQLQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
    return list;
  }

  public String checkZXXS_XS_ZPCJ(String XX_JBXX_ID) throws ParseException {
    String sql = "select a.* from (select * from ZXXS_XS_ZPCJ where XX_JBXX_ID='" + XX_JBXX_ID + "' and flag='1' order by cjsj desc) a where @rownum=1";
    List list = getListSQL(sql);
    if ((list != null) && (list.size() > 0)) {
      String CJSJ = (String)((Map)list.get(0)).get("CJSJ");
      Date begin = DateUtils.parseDate(CJSJ, new String[] { "yyyyMMdd HH:mm:ss" });
      Date end = new Date();

      long between = (end.getTime() - begin.getTime()) / 1000L;
      long day1 = between / 86400L;
      long hour1 = between % 86400L / 3600L;
      long minute1 = between % 3600L / 60L;
      long second1 = between % 60L;
      if (day1 - 730L < 0L) {
        String result = "距离上一次操作已过" + day1 + "天" + hour1 + "小时" + minute1 + "分" + 
          second1 + "秒，请满" + 730 + "天后再操作！";
        return result;
      }
      return "";
    }
    return "";
  }

  public void saveZPCJ(String ZPCJ_ID, String WJMC, String CJSJ, String XX_JBXX_ID, String FLAG) {
    String sql = "insert into ZXXS_XS_ZPCJ(ZPCJ_ID,WJMC,CJSJ,XX_JBXX_ID,FLAG) values('" + ZPCJ_ID + "','" + WJMC + "','" + CJSJ + "','" + XX_JBXX_ID + "','" + FLAG + "')";
    insert(sql);
  }
  public void saveZPCJ_MX_SJPT(String ZPCJ_MXID, String ZPCJ_ID, String WTSM, String WJLJ) {
    String sql = "insert into ZXXS_XS_ZPCJ_MX_SJPT(ZPCJ_MXID,ZPCJ_ID,WTSM,WJLJ)VALUES('" + ZPCJ_MXID + "','" + ZPCJ_ID + "','" + WTSM + "','" + WJLJ + "')";
    insert(sql);
  }
  public List<Map<String, Object>> getSJPT(String ZPCJ_ID) {
    String sql = "select * from ZXXS_XS_ZPCJ_MX_SJPT where ZPCJ_ID='" + ZPCJ_ID + "'";
    return getListSQL(sql);
  }
  public MsgBean saveUpFileInfo(String newfileName, String distPath) {
    MsgBean mb = new MsgBean();
    mb.setShow(true);

    String ZPCJ_ID = UUID.randomUUID().toString().replaceAll("-", "");
    String WJMC = newfileName;
    String CJSJ = DateFormatUtils.format(new Date(), "yyyyMMdd HH:mm:ss");
    ArrayList xxidAry = new ArrayList();
    try {
      ZipFile zip = new ZipFile(distPath);
      Enumeration entries = zip.entries();
      while (entries.hasMoreElements()) {
        ZipEntry entry = (ZipEntry)entries.nextElement();
        String entryName = entry.getName();
        String cardID = org.apache.commons.lang.StringUtils.split(new String(entryName.getBytes("UTF-8")), ".")[0];
        List lstXs = checkCardID(cardID);
        if ((lstXs == null) || (lstXs.size() <= 0)) {
          String ZPCJ_MXID = UUID.randomUUID().toString().replaceAll("-", "");
          String WTSM = "身份证件号码" + cardID + "在系统中未找到！";
          String WJLJ = "";
          saveZPCJ_MX_SJPT(ZPCJ_MXID, ZPCJ_ID, WTSM, WJLJ);
        } else {
          String xxid = ((Map)lstXs.get(0)).get("XX_JBXX_ID").toString();
          if (!xxidAry.contains(xxid)) {
            xxidAry.add(xxid);
          }
        }
      }
      List list = getSJPT(ZPCJ_ID);
      mb.setData(getSJPT(ZPCJ_ID));
      mb.setSuccess(true);
      mb.setMsg(MsgBean.EXPORT_SUCCESS);
      if ((list != null) && (list.size() > 0)) {
        for (int jj = 0; jj < xxidAry.size(); jj++) {
          String XX_JBXX_ID = ((String)xxidAry.get(jj)).toString();
          saveZPCJ(ZPCJ_ID, WJMC, CJSJ, XX_JBXX_ID, "0");
        }
        mb.setMsg("有" + list.size() + "个存在数据采集问题请检查！");
      } else {
        for (int jj = 0; jj < xxidAry.size(); jj++) {
          String XX_JBXX_ID = ((String)xxidAry.get(jj)).toString();
          String sqlXx = "select * from ZXXS_XS_ZPCJ where ZPCJ_ID='" + ZPCJ_ID + "'";
          List lstXx = getListSQL(sqlXx);
          if (lstXx.size() > 0) {
            String sql = "update ZXXS_XS_ZPCJ set WJMC='" + WJMC + "',CJSJ='" + CJSJ + "',XX_JBXX_ID='" + XX_JBXX_ID + "',FLAG='0' where ZPCJ_ID='" + ZPCJ_ID + "'";
            update(sql);
          } else {
            saveZPCJ(ZPCJ_ID, WJMC, CJSJ, XX_JBXX_ID, "0");
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      mb.setSuccess(false);
      mb.setMsg(MsgBean.EXPORT_ERROR);
    }
    return mb;
  }

  public PageBean getPhotoList(String schoolname, MBspInfo bspInfo, String distPath) {
    String sql = "SELECT T1.ZPCJ_ID,T1.WJMC,T2.XXMC,T3.sszgjyxzdm as REGION_CODE,(SELECT COUNT(T4.XS_JBXX_ID) FROM ZXXS_XS_JBXX T4 WHERE T1.XX_JBXX_ID=T4.XX_JBXX_ID) XXRS,T1.FLAG FROM ZXXS_XS_ZPCJ T1 LEFT JOIN ZXXS_XX_JBXX T2 ON T1.XX_JBXX_ID=T2.XX_JBXX_ID LEFT JOIN ZXXS_XX_JBXX T3 ON T1.XX_JBXX_ID=T3.XX_JBXX_ID WHERE (T3.SSZGJYXZDM='" + 
      bspInfo.getOrgan().getOrganCode() + "' or T3.SSZGJYXZDM in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='" + bspInfo.getOrgan().getOrganCode() + 
      "') or T3.SSZGJYXZDM IN (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE in (select REGION_CODE from COM_MEMS_ORGAN where PARENT_CODE='" + 
      bspInfo.getOrgan().getOrganCode() + "'))) AND T1.FLAG IS NOT NULL";
    if ((schoolname != null) && (!"".equals(schoolname))) {
      sql = sql + " AND T1.XX_JBXX_ID IN ('" + schoolname.replaceAll(",", "','") + "')";
    }
    PageBean pb = getSQLPageBean(sql);
    List pbList = pb.getResultList();
    if ((pbList != null) && (pbList.size() > 0))
      for (int i = 0; i < pbList.size(); i++) {
        Map map = (HashMap)pbList.get(i);
        String wjmc = map.get("WJMC") == null ? "" : map.get("WJMC").toString();
        if (!"".equals(wjmc)) {
          String path = distPath + File.separator + wjmc;
          try
          {
            ZipFile zip = new ZipFile(path);
            Enumeration entries = zip.entries();
            int count = 0;
            while (entries.hasMoreElements()) {
              ZipEntry entry = (ZipEntry)entries.nextElement();
              count++;
            }
            map.put("ZPSL", Integer.valueOf(count));
          } catch (FileNotFoundException localFileNotFoundException) {
          }
          catch (IOException localIOException) {
          }
        }
      }
    return pb;
  }

  public List getPhotoListReport(String zpcjId, String distPath, MBspInfo bspInfo) {
    List resultList = new ArrayList();

    String sql = " SELECT WJMC FROM ZXXS_XS_ZPCJ WHERE ZPCJ_ID='" + zpcjId + "' ";
    List wjList = getListSQL(sql);
    if ((wjList == null) || (wjList.size() == 0)) {
      return null;
    }
    String wjmc = ((HashMap)wjList.get(0)).get("WJMC").toString();
    String filePath = distPath + File.separator + wjmc;

    String savaPath = distPath + File.separator + zpcjId;

    delAllFile(savaPath);
    zipFileRead(filePath, savaPath, new String[0], bspInfo);
    ZipFile zip = null;
    try {
      zip = new ZipFile(filePath);
      Enumeration entries = zip.entries();
      while (entries.hasMoreElements()) {
        Map map = new HashMap();
        ZipEntry entry = (ZipEntry)entries.nextElement();
        String fileName = entry.getName();
        String cardID = org.apache.commons.lang.StringUtils.split(fileName, ".")[0];
        map.put("sfzjh", cardID);
        map.put("xm", "");

        sql = " SELECT XM,XS_JBXX_ID FROM ZXXS_XS_JBXX WHERE SFZJH='" + cardID + "' ";
        List xsList = getListSQL(sql);
        String xsid = "";
        if ((xsList != null) && (xsList.size() > 0)) {
          map.put("xm", ((HashMap)xsList.get(0)).get("XM").toString());
          xsid = ((HashMap)xsList.get(0)).get("XS_JBXX_ID").toString();
        }
        map.put("path", "uploadFile" + File.separator + "photo" + File.separator + zpcjId + File.separator + xsid + "_0." + org.apache.commons.lang.StringUtils.split(fileName, ".")[1]);
        resultList.add(map);
      }
    } catch (Exception e) {
      e.printStackTrace();
      try
      {
        if (zip != null)
          zip.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (zip != null)
          zip.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return resultList;
  }

  public MsgBean auditPhoto(String zpcjId)
  {
    try
    {
      StringBuffer sb = new StringBuffer();
      sb.append(" UPDATE ZXXS_XS_ZPCJ SET FLAG='1' WHERE FLAG='0' AND ZPCJ_ID='" + zpcjId + "' ");
      update(sb.toString());
      return getMsgBean(true, "审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "审核失败！");
  }

  public List auditPhotoUpdate(String zpcjId, String distPath, MBspInfo bspInfo)
  {
    try
    {
      String sql = " SELECT T1.XX_JBXX_ID,T2.sszgjyxzdm as XXDZDM,T1.WJMC FROM ZXXS_XS_ZPCJ T1 LEFT JOIN ZXXS_XX_JBXX T2 ON T1.XX_JBXX_ID=T2.XX_JBXX_ID WHERE T1.ZPCJ_ID='" + 
        zpcjId + "' ";
      List wjList = getListSQL(sql);
      if ((wjList == null) && (wjList.size() == 0)) {
        return null;
      }
      String wjmc = ((HashMap)wjList.get(0)).get("WJMC").toString();
      String xxdzdm = ((HashMap)wjList.get(0)).get("XXDZDM").toString();
      String xxdzdm1 = xxdzdm.substring(0, xxdzdm.length() >= 4 ? 4 : xxdzdm.length());
      String xxdzdm2 = xxdzdm.substring(0, xxdzdm.length() >= 6 ? 6 : xxdzdm.length());
      String filePath = distPath + File.separator + wjmc;
      String savePath = distPath + File.separator + xxdzdm.substring(0, 2).toString() + File.separator + 
        xxdzdm1 + File.separator + xxdzdm2;

      System.out.println("获取解压包路径：" + filePath);
      System.out.println("文件保存路径：" + savePath);
      List resultList = new ArrayList();
      List list = zipFileRead(filePath, savePath, new String[0], bspInfo);
      if ((list != null) && (list.size() > 0)) {
        for (int i = 0; i < list.size(); i++) {
          String path = ((HashMap)list.get(i)).get("newFileName").toString();
          String xsdm = ((HashMap)list.get(i)).get("xsdm").toString();
          Map map = new HashMap();
          map.put("xsdm", xsdm);
          path = xxdzdm.substring(0, 2).toString() + File.separator + 
            xxdzdm1 + File.separator + xxdzdm2 + File.separator + path;
          map.put("path", path);
          resultList.add(map);
        }
      }
      return resultList;
    } catch (Exception e) {
      e.printStackTrace();
    }return null;
  }

  public void fileChannelCopy(File s, File t)
  {
    FileInputStream fi = null;
    FileOutputStream fo = null;
    FileChannel in = null;
    FileChannel out = null;
    try {
      fi = new FileInputStream(s);
      fo = new FileOutputStream(t);
      in = fi.getChannel();
      out = fo.getChannel();
      in.transferTo(0L, in.size(), out);
    } catch (IOException e) {
      e.printStackTrace();
      try
      {
        fi.close();
        in.close();
        fo.close();
        out.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        fi.close();
        in.close();
        fo.close();
        out.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public List zipFileRead(String zipFileName, String savePathRoot, String[] types, MBspInfo bspInfo)
  {
    List list = new ArrayList();
    ZipFile zipFile = null;
    try {
      zipFile = new ZipFile(zipFileName);
      Enumeration enu = zipFile.entries();
      while (enu.hasMoreElements()) {
        ZipEntry ze = (ZipEntry)enu.nextElement();
        InputStream read = zipFile.getInputStream(ze);
        String fileName = ze.getName();
        if ((fileName == null) || (fileName.indexOf(".") == -1)) {
          continue;
        }
        String newFileName = getNewFileName(savePathRoot, fileName);
        if ((types == null) || (types.length < 1)) {
          saveToTempFile(read, savePathRoot + File.separator + newFileName);
          String repPath = savePathRoot.replaceFirst("xjgl", "ksgl");
          repPath = repPath.replaceFirst("xjgl", "fzzxks");
          saveToTempFile(read, repPath + File.separator + newFileName);

          File fnew = new File(savePathRoot + File.separator + newFileName);
          File fnew1 = new File(repPath + File.separator + newFileName);
          fileChannelCopy(fnew, fnew1);

          String xsdm = "".equals(newFileName) ? "" : newFileName.split("_")[0];
          Map map = new HashMap();
          map.put("newFileName", newFileName);
          map.put("xsdm", xsdm);
          list.add(map);
        }
        else
        {
          for (int i = 0; i < types.length; i++)
            if (fileName.indexOf(types[i]) != -1) {
              saveToTempFile(read, savePathRoot + File.separator + fileName);
              break;
            }
        }
      }
    } catch (ZipException z) {
      z.printStackTrace();
      try
      {
        if (zipFile != null)
          zipFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      try
      {
        if (zipFile != null)
          zipFile.close();
      } catch (IOException e2) {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (zipFile != null)
          zipFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return list;
  }

  private static String saveToTempFile(InputStream read, String saveFilePath)
  {
    File file = new File(saveFilePath);
    if (!file.exists()) {
      File rootDirectoryFile = new File(file.getParent());

      if (!rootDirectoryFile.exists()) {
        boolean ifSuccess = rootDirectoryFile.mkdirs();
        if (ifSuccess)
          System.out.println("文件夹创建成功!");
        else {
          System.out.println("文件创建失败!");
        }
      }

      BufferedOutputStream write = null;
      int count = -1;
      try {
        file.createNewFile();
        write = new BufferedOutputStream(new FileOutputStream(file));
        byte[] bytes = new byte[1024];
        while ((count = read.read(bytes)) != -1)
          write.write(bytes, 0, count);
      }
      catch (IOException localIOException)
      {
        try {
          if (write != null) {
            write.flush();
            write.close();
          }
          if (read != null)
            read.close();
        }
        catch (IOException localIOException1)
        {
        }
      }
      finally
      {
        try
        {
          if (write != null) {
            write.flush();
            write.close();
          }
          if (read != null)
            read.close();
        } catch (IOException localIOException2) {
        }
      }
    }
    return saveFilePath;
  }

  private String getNewFileName(String savePath, String fileName)
  {
    String sfzjh = fileName.substring(0, fileName.indexOf("."));
    String fileType = fileName.substring(fileName.indexOf("."), fileName.length());
    String sql = " SELECT XS_JBXX_ID FROM ZXXS_XS_JBXX WHERE SFZJH='" + sfzjh + "' ";
    List xsList = getListSQL(sql);
    String xsid = "";
    int[] xsidFileName = new int[100];
    if ((xsList != null) && (xsList.size() > 0)) {
      xsid = ((HashMap)xsList.get(0)).get("XS_JBXX_ID").toString();
      File file = new File(savePath);
      File[] array = file.listFiles();
      int j = 0;
      boolean flag = false;
      if ((array != null) && (array.length > 0)) {
        for (int i = 0; i < array.length; i++) {
          if ((!array[i].isFile()) || (!array[i].getName().startsWith(xsid)))
            continue;
          String str = array[i].getName();
          String strend = str.substring(str.lastIndexOf("_") + 1, str.lastIndexOf("."));
          xsidFileName[(j++)] = Integer.parseInt(strend);
          flag = true;
        }
      }

      if (flag)
        fileName = xsid + "_" + (getMax(xsidFileName) + 1) + fileType;
      else {
        fileName = xsid + "_0" + fileType;
      }
    }
    return fileName;
  }

  public static boolean delAllFile(String path)
  {
    boolean flag = false;
    File file = new File(path);
    if (!file.exists()) {
      return flag;
    }
    if (!file.isDirectory()) {
      return flag;
    }
    String[] tempList = file.list();
    File temp = null;
    for (int i = 0; i < tempList.length; i++) {
      if (path.endsWith(File.separator))
        temp = new File(path + tempList[i]);
      else {
        temp = new File(path + File.separator + tempList[i]);
      }
      if (temp.isFile()) {
        temp.delete();
        flag = true;
      }
      if (temp.isDirectory()) {
        delAllFile(path + File.separator + tempList[i]);
        flag = true;
      }
    }
    return flag;
  }

  public MsgBean updateStudentPhoto(List xsList, MBspInfo bspInfo)
  {
    try
    {
      if ((xsList != null) && (xsList.size() > 0)) {
        for (int i = 0; i < xsList.size(); i++) {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String cjsj = sdf.format(new Date());
          String xsdm = ((HashMap)xsList.get(i)).get("xsdm").toString();
          String path = ((HashMap)xsList.get(i)).get("path").toString();

          String sql = "select * from zxxs_xs_pic where xs_jbxx_id='" + xsdm + "' ";
          List list = getListSQL(sql);
          path = path.replaceAll("\\\\", "/");
          if ((list != null) && (list.size() > 0)) {
            sql = " update zxxs_xs_pic set path='" + path + "',gxr='" + bspInfo.getUserId() + 
              "',gxsj='" + cjsj + "' where xs_jbxx_id='" + xsdm + "' ";
            update(sql);
          } else {
            sql = "insert into zxxs_xs_pic(xs_jbxx_id,path,gxr,gxsj) values ('" + xsdm + "','" + path + 
              "','" + bspInfo.getUserId() + "','" + cjsj + "') ";
            insert(sql);
          }
        }
      }
      return getMsgBean(true, "照片审核成功！");
    } catch (Exception e) {
      e.printStackTrace();
    }return getMsgBean(false, "照片审核失败！");
  }

  public static int getMax(int[] arr)
  {
    int max = arr[0];
    for (int x = 1; x < arr.length; x++) {
      if (arr[x] > max)
        max = arr[x];
    }
    return max;
  }
}