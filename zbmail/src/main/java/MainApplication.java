import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;

/**
 * Created by guiqi on 2017/12/29.
 */
public class MainApplication {

    private final static String MAIL_SERVER_HOST = "smtp.qiye.163.com";
    private final static String USER = "huguiqi@shanlinjinrong.com";
    private final static String PASSWORD = "MMHuguiqi_198838";
    private final static String MAIL_FROM = "huguiqi@shanlinjinrong.com"; //发送人
    private final static String MAIL_TO = "guiqi.hu@163.com"; //收件人
    private final static String MAIL_CC = "417980991@qq.com"; //抄送人
    private final static String MAIL_BCC = ""; //密送人

    public static void sendMail() throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.debug", "true");
        prop.setProperty("mail.host", MAIL_SERVER_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        // 1、创建session
        Session session = Session.getInstance(prop);
        Transport ts = null;
        // 2、通过session得到transport对象
        ts = session.getTransport();
        // 3、连上邮件服务器
        ts.connect(MAIL_SERVER_HOST, USER, PASSWORD);
        // 4、创建邮件
        MimeMessage message = new MimeMessage(session);
        // 邮件消息头
        message.setFrom(new InternetAddress(MAIL_FROM)); // 邮件的发件人
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(MAIL_TO)); // 邮件的收件人
        message.addRecipient(Message.RecipientType.CC, new InternetAddress(MAIL_CC)); // 邮件的抄送人
//        message.setRecipient(Message.RecipientType.BCC, new InternetAddress(MAIL_BCC)); // 邮件的密送人

        HashMap<String,String> map = givenMailContent();
        message.setSubject(map.get("title")); // 邮件的标题
        // 邮件消息体
        message.setText(map.get("text"));
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();

    }


    private static HashMap<String,String> givenMailContent(){
        StringBuilder sb = new StringBuilder("你好，\n");
        Date todayDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        sb.append("     ")
          .append(format.format(todayDate))
          .append("的周工作总结请见附件。\n")
          .append("\n")
          .append("谢谢。。。");

        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("text",sb.toString());
        format.applyPattern("yyyy-MM-dd");
        hashMap.put("title","技术部-胡桂祁周报("+format.format(todayDate)+")");
        return hashMap;
    }

    /**
     * 获取项目所在路径(包括jar)
     *
     * @return
     */
    public static String getProjectPath() {

        java.net.URL url = MainApplication.class.getProtectionDomain().getCodeSource()
                .getLocation();
        String filePath = null;
        try {
            filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        java.io.File file = new java.io.File(filePath);
        filePath = file.getAbsolutePath();
        return filePath;
    }

    /**
     * 获取项目所在路径
     *
     * @return
     */
    public static String getRealPath() {
        String realPath = MainApplication.class.getClassLoader().getResource("")
                .getFile();
        java.io.File file = new java.io.File(realPath);
        realPath = file.getAbsolutePath();
        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realPath;
    }

    public static String getAppPath(Class<?> cls) {
        // 检查用户传入的参数是否为空
        if (cls == null)
            throw new java.lang.IllegalArgumentException("参数不能为空！");

        ClassLoader loader = cls.getClassLoader();
        // 获得类的全名，包括包名
        String clsName = cls.getName();
        // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
        if (clsName.startsWith("java.") || clsName.startsWith("javax.")) {
            throw new java.lang.IllegalArgumentException("不要传送系统类！");
        }
        // 将类的class文件全名改为路径形式
        String clsPath = clsName.replace(".", "/") + ".class";

        // 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(clsPath);
        // 从URL对象中获取路径信息
        String realPath = url.getPath();
        // 去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if (pos > -1) {
            realPath = realPath.substring(pos + 5);
        }
        // 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(clsPath);
        realPath = realPath.substring(0, pos - 1);
        // 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        if (realPath.endsWith("!")) {
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        }
        java.io.File file = new java.io.File(realPath);
        realPath = file.getAbsolutePath();

        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return realPath;
    }// getAppPath定义结束


    public static void main(String[] args) throws Exception {
//        File source = new File(givenLastZBPath());
//        File dest = new File(givenTodayZBPath());
//        copyNewZBExcel(source,dest);
        modifyZBExcel(args[0]);
        sendMail();
    }



    private static void copyNewZBExcel(File source, File dest)throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    private static void modifyZBExcel(String nextWeekPlan){
        try {
            File source = new File(givenLastZBPath());
            //poi包下的类读取excel文件
            Workbook workbook = new XSSFWorkbook(source);
            Sheet sheet = workbook.getSheetAt(0);
            String headCellValue = sheet.getRow(0).getCell(0).getStringCellValue();
            int weekDayIndex = headCellValue.indexOf("部") + 2;
            int lastWeekDayIndex = headCellValue.indexOf("及") + 2;
            Integer currentWeekDay = new Integer(headCellValue.substring(weekDayIndex, weekDayIndex + 3).trim());
            Integer lastWeekDay = new Integer(headCellValue.substring(lastWeekDayIndex, lastWeekDayIndex + 3).trim());
           String replaceStr = headCellValue.replaceFirst(""+lastWeekDay,(lastWeekDay +1) + "").replaceFirst(""+currentWeekDay,(currentWeekDay +1)+"");

           //取值
            String planWork = sheet.getRow(3).getCell(1).getStringCellValue();
            String actWork = sheet.getRow(3).getCell(2).getStringCellValue();
            String lastWeekPlanWork = sheet.getRow(11).getCell(1).getStringCellValue();
            String zeRengReng = sheet.getRow(11).getCell(1).getStringCellValue();

            //写入数据
            sheet.getRow(0).getCell(0).setCellValue(replaceStr);
            sheet.getRow(3).getCell(1).setCellValue(lastWeekPlanWork);
            sheet.getRow(3).getCell(2).setCellValue(lastWeekPlanWork);
            sheet.getRow(11).getCell(1).setCellValue(nextWeekPlan);
            sheet.getRow(11).getCell(2).setCellValue(zeRengReng);

            File dest = new File(givenTodayZBPath());
            //将修改后的文件写出到D:\\excel目录下
            FileOutputStream os = new FileOutputStream(dest);
            os.flush();
            //将Excel写出
            workbook.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String givenLastZBPath(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String path = getProjectPath();
//        return path+"\\周工作总结及计划表_善林宝_技术部_胡桂祁_"+format.format(calendar.getTime()) +".xlsx";
        return  path+"\\周工作总结及计划表_善林宝_技术部_胡桂祁_20171222.xlsx";
    }

    private static String givenTodayZBPath(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String path = getProjectPath();
        return path+"\\周工作总结及计划表_善林宝_技术部_胡桂祁_"+format.format(calendar.getTime())+".xlsx";
    }
}
