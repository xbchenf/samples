package com.lolaage.externalp100.ftp;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author xbchen
 * @date 2018-6-6
 * @description FTP上传工具类
 */
public class FtpUtils {

    public static Logger log = Logger.getLogger(FtpUtils.class);

    private String hostname = "210.21.222.22";//ftp服务器地址
    private int port = 24251 ;//ftp服务器端口号默认为21
    private String username = "aa";//ftp登录账号
    private String password = "sss!@#wsx";//ftp登录密码
    private String path="/lolaage";//上传目录

    public FtpUtils(String hostname,int port,String username,String password,String path){
        this.hostname=hostname;
        this.port=port;
        this.username=username;
        this.password=password;
        this.path=path;
    }

    /**
     * 初始化ftp服务器
     */
    private FTPClient initFtpClient() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            log.info("connecting...ftp服务器:"+this.hostname+":"+this.port);
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                log.info("connect failed...ftp服务器:"+this.hostname+":"+this.port);
            }
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.makeDirectory(path);//创建成功返回true，如果已经存在则会返回false;
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(path);
            log.info("connect success...ftp服务器:"+this.hostname+":"+this.port);
        }catch (Exception e) {
            log.error("ftp connect initializer failed:"+e.getMessage());
        }
        return ftpClient;
    }

    /**
     * 上传文件到FTP服务器
     * @param file
     * @return
     */
    public boolean uploadFile(File file){
        log.info("开始准备推送文件到FTP服务器:"+file.getName());
        boolean flag = false;
        InputStream in=null;
        FTPClient ftpClient =null;
        try{
            ftpClient= initFtpClient();
            in=new FileInputStream(file);
            log.info("开始上传文件到FTP服务器：name:"+file.getName());
            ftpClient.storeFile(file.getName(), in);
            in.close();
            ftpClient.logout();
            flag = true;
            log.info("上传文件成功; name:"+file.getName());
        }catch (Exception e){
            log.error("文件："+file.getName()+";上传失败："+e.getMessage());
        }finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(Exception e){
                    log.error("ftp disconnect failed："+e.getMessage());
                }
            }
            if(null != in){
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("inputstream close failed："+e.getMessage());
                }
            }
            if(null != file)file.delete();
        }
        return flag;
    }

    public static void main(String[] args) throws Exception{

       /* File file=new File("E:\\project\\code\\NanShanFenJu\\GenericProfessionServer\\trunk\\GenericProfessionServer_NanShanFenJu\\target\\classes\\ftp_temp_file\\test1.json");
        FtpUtils ftpUtils=new FtpUtils("210.21.204.53",24251,"sdjw","sdjw!@#wsx","/lolaage");
        ftpUtils.initFtpClient();
        ftpUtils.uploadFile(file);*/

        /*File localFile = new File("E:\\project\\code\\NanShanFenJu\\GenericProfessionServer\\trunk\\GenericProfessionServer_NanShanFenJu\\target\\classes\\ftp_temp_file\\test2.json");
        OutputStream is = new FileOutputStream(localFile);
        ftpClient.retrieveFile(file.getName(), is);*/

        /*File file=new File("E:\\project\\code\\NanShanFenJu\\GenericProfessionServer\\trunk\\GenericProfessionServer_NanShanFenJu\\target\\classes\\ftp_temp_file\\test1.json");
        BufferedReader reader =new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        System.out.println(reader.readLine());
        reader.close();*/
    }
}
