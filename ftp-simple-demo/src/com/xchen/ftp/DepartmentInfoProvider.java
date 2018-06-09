package com.lolaage.externalp100.ftp.szTran;

import com.lolaage.dals.distributetransaction.DistributeTransaction;
import com.lolaage.dals.sessionmanager.SessionPoint;
import com.lolaage.entity.resBean.DeptBean;
import com.lolaage.externalp100.common.GlobalConstValue;
import com.lolaage.externalp100.ftp.FtpUtils;
import com.lolaage.externalp100.util.JsonUtil;
import com.lolaage.newdao.DepartmentDao;
import com.lolaage.newdao.impl.DepartmentDaoImpl;
import com.lolaage.util.FilePathUtil;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xbchen
 * @date 2018-6-5
 * @description 深圳公安局公交分局---给警务云提供部门信息，生成JSON格式的文件信息，通过FTP进行发送
 */
public class DepartmentInfoProvider implements Runnable {

    public static Logger log = Logger.getLogger(DepartmentInfoProvider.class);
    public static DepartmentDao deptDao = null;
    public static String ftp_temp_dir=null;
    public static FtpUtils ftpUtils=null;
    private static final String detpCode="44030704";//深圳公安局公交分局的部门编码44030704;

    static {
        deptDao = new DepartmentDaoImpl(GlobalConstValue.COMPANY_DEMO_ID, new DistributeTransaction(), new SessionPoint());
        ftp_temp_dir=FilePathUtil.CONF_PATH.substring(0, FilePathUtil.CONF_PATH.indexOf("/conf"))+"/ftp_temp_file";
        ftpUtils=new FtpUtils("210.21.204.53",24251,"sdjw","sdjw!@#wsx","/lolaage");
    }

    public void run() {

        //1.获取数据
        List<DeptBean> deptList = getData(detpCode);
        if(deptList ==null || deptList.isEmpty()){
            log.error("部门信息为空......");
            return;
        }

        //2.生成本地临时文件,写入数据
        File file=writeDate2File(deptList);
        if(file == null || file.length()==0) {
            log.error("临时文件创建失败或者为空......");
            return;
        }

        //3.推送文件到FTP服务器，完成后删除临时文件。
        boolean isSuccess=ftpUtils.uploadFile(file);
        if(isSuccess){
            log.info("推送成功===>"+file.getName());
        }else{
            log.error("推送失败===>"+file.getName());
        }
    }

    //1.获取数据
    private List<DeptBean> getData(String code){
        List<DeptBean> deptList=null;
        try {
            deptList = deptDao.getAllSubDeptByCode(code);
        }
        catch (Exception e) {
            log.error("查询部门"+code+"失败："+e.getMessage());
        }
        return deptList;
    }

    //2.写入本地文件
    private File writeDate2File(List<DeptBean> deptList){
        BufferedWriter writer=null;
        File file=null;
        try {
            //创建本地临时目录
            File fileDirecotry=new File(ftp_temp_dir);
            if(!fileDirecotry.exists()){
                fileDirecotry.mkdir();
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
            String nowTime=sdf.format(new Date());
            String fileName="departmentInfos_"+nowTime+".json";
            file=new File(ftp_temp_dir+"/"+fileName);
            file.createNewFile();

            Map<String,List<DeptBean>> map =new HashMap();
            map.put("departmentInfos",deptList);
            writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            String context=JsonUtil.obj2String(map);
            context = context.substring(1,context.length()-1);//去掉前面{和后面的};
            writer.write(context);
        }
        catch (Exception e) {
            log.error("创建本地临时文件，写入数据失败..."+e.getMessage());
        }
        finally {
            try {
                if(writer !=null) writer.close();
            }catch (Exception e){
                log.error("dataOutputStream close failed..........");
            }
        }
        return file;
    }
}
