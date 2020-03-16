package proj;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.print.attribute.standard.PrinterLocation;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import proj.UserInterface;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class Database extends JFrame {
    static int mode;//为0表示是用户模式，为1表示是管理员模式

    static int maneger_code;//给管理员用的。分别表明是什么管理员指令
    static int maneger_minus_mark;//表示要扣多少credit（信誉度）
    static int maneger_stu_code,maneger_stu_name;//管理员要处理的学生的学号和名字
    static long maneger_stu_id;
    static Date maneger_delete_date;////删除maneger_delete_date以及之前的预约
    static long maneger_unusable_id;//坏了的器材的ID
    static long maneger_to_be_deleted_id;//将要删除的器材的ID
    static String maneger_course_name;//管理员要处理的体育课的名字，比如：羽毛球四班
    static int now_res_id = 2;
    static String  date;
    static String sql;
    static Connection con;
    static ResultSet ret;
    static Statement statement;
    

	private JPanel contentPane;
	private JPasswordField passwordField_1;
	private JTextField txtFadfad;
	private JTextField txtIfYouHave;
	private JFormattedTextField frmtdtxtfldSdf;
	
	protected static boolean isID = false;
	protected static boolean isPassword = false;
	protected static int s,t,k=1;//t = 1查  t=2定 t=3取消 s=1234场 器材 教练 账号
	
	/**
	 * Launch the application.
	 */
	static Database frame = new Database();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	
	
	
@SuppressWarnings("deprecation")
static void backend(){
    //jdbc驱动
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/" + "mydb" + "?useSSL=false&serverTimezone=UTC&useSSL=false";
    String user = "root";
    String DBpassword = "Xv105031298";
    date = SportsBooking.da;
    try {
        //注册JDBC驱动程序
        Class.forName(driver);
        //建立连接
        con = DriverManager.getConnection(url, user, DBpassword);
        if (!con.isClosed()) {
            System.out.println("数据库连接成功");
        }
        statement = con.createStatement();

        if(mode==0) {
            if (s == 4) {//验证ID和密码
                SportsBooking.info.clear();
                sql = "select count(*) cnt from mydb.User where epy_stu_ID=" + SportsBooking.id + ";";
                long tmp=0;
                ret = statement.executeQuery(sql);
                 while (ret.next()){
                     tmp=ret.getLong("cnt");
                 }
                if (tmp==0){
                    isID = false;
                    SportsBooking.info.add("Your ID or password is wrong!");
                }
                else {
                    isID = true;
                	sql = "select count(*) cnt from mydb.User where epy_stu_ID=" + SportsBooking.id + " and password='"+SportsBooking.password+"';";
                	ret = statement.executeQuery(sql);
                    ret.next();
                    long tmm=ret.getLong("cnt");
                    if(tmm==0){
                    	isPassword=false;
                    }else{
                    	isPassword=true;
                    sql = "select * from mydb.User where epy_stu_ID=" + SportsBooking.id +
                            " and Password='" + SportsBooking.password + "';";
                    ret = statement.executeQuery(sql);
                    while(ret.next()){
                    int idd=ret.getInt("epy_stu_ID");
                    String namee=ret.getString("UserName");
                     int creditt=ret.getInt("credit");
                     SportsBooking.info.add("Name is "+namee+" ;ID is "+idd+"; credit is "+creditt+".");
                     SportsBooking.Credit = ""+creditt;
                     SportsBooking.Name = namee;
                    //System.out.println(1111);
                    }}
                }
                //System.out.println(creditt);
            }


            //说明：所有前端需求中，starttime,endtime,date还有类型都是必填项

            if (s == 1 && t == 1) {//查场地    t=123 分别是查定取消   s=123分别是场器材老师
                //要求： 给出场地类型，必须返回所有可用时间。分为两种，即k=1和k=2。
                //举个栗子：
                // k=2时，需求信息中同时提供了日期（date）、场地（location，比如润杨体育场）和场馆类型（field_type，比如羽毛球馆）
                //然后我们返回润杨体育场中所有的羽毛球馆可以占用的时间。
                // 比如返回润杨羽毛球馆1号场10：00-11：30，12：00-17：00，17：30-18：00都可以预约
                //k=1时，需求中只说明日期、场馆类型，我们的返回信息与k=2时相似，只是要遍历所有场馆
                // 然后我们返回润杨羽毛球场和湖畔羽毛球场等所有羽毛球场可预约时间

                //注意：每个场馆的开馆时间和闭馆时间可能不同

            	SportsBooking.info.clear();
                //if(k==2){
            	System.out.println(SportsBooking.field_type+" "+SportsBooking.location+" "+k);
            	
                sql = "select * from (select * from mydb.Reservation_Table " +
                        "where date='" + date + "') date_res" +
                        "  join (select * from mydb.gym_item where gym_type='"
                        + SportsBooking.field_type + "' and location='" + SportsBooking.location + "') bad on " +
                        "    date_res.Gymid=bad.ID order by Gymid,Begintime;";

                if (k == 1) {   //只提供了所需场馆类型，比如羽毛球馆	
                    sql = "select * from (select * from mydb.Reservation_Table where date='" + date + "') date_res" +
                            "    join     (select * from mydb.gym_item where gym_type='" + SportsBooking.field_type + "') bad on " +
                            "    date_res.Gymid=bad.ID order by Gymid,Begintime;";
                }
                ret = statement.executeQuery(sql);
               while(ret.next()){
                //ret.next();
            	   System.out.println("here");
                String ss=ret.getString("Name");
                	Time t1=ret.getTime("Begintime");
                	t1.setHours(t1.getHours()+16);
                	Time t2=ret.getTime("Overtime");
                	t2.setHours(t2.getHours()+16);
                	ss=ss+" "+"Begin: "+t1.toString()+" End: "+t2.toString()+"        ";
                	SportsBooking.info.add(ss);
                	System.out.println(ss);
                	MyInformation.rst += ss;
                	MyInformation.rst += "\r\n";
                	
                }
                
            }


            if (t == 1 && s == 2) {//查器材
            	SportsBooking.info.clear();
                if (k == 2) {//belong_gym可以是风雨操场
                    sql = "select count(*) cnt from" +
                            "   (select equipment_item.ID ID from equipment_item " +
                            "join equipment_type et on" +
                            "  equipment_item.type_id = et.ID and kind='" + SportsBooking.item_kind +
                            "' and belong_gym ='" + SportsBooking.location + "' and useable=1 ) typ " +
                            "where not exists " +
                            " (select Equipmentid from Reservation_Table where date=" + date +
                            " and (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.endtime + "')" +
                            " or (Overtime>'" + SportsBooking.starttime + "' and Overtime<='" + SportsBooking.endtime + "')   ) );";
                    ret = statement.executeQuery(sql);
                    while (ret.next()){
                    int cnt = ret.getInt("cnt");
                    String str = Integer.toString(cnt);
                    SportsBooking.info.add(SportsBooking.location + " has " + str + " unit.");
                } }else if (k == 1) {
                    sql = "select distinct belong_gym from equipment_item;";
                    ResultSet ret2 = statement.executeQuery(sql);
                    while (ret2.next()) {
                    	SportsBooking.location = ret2.getString("belong_gym");
                        sql = "select count(*) cnt from" +
                                "   (select equipment_item.ID ID from equipment_item join equipment_type et on" +
                                "  equipment_item.type_id = et.ID and kind='" + SportsBooking.item_kind + "' and belong_gym ='" + SportsBooking.location + "' and useable=1 ) typ where not exists " +
                                " (select Equipmentid from Reservation_Table where date=" + date + " and (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.endtime + "')" +
                                " or (Overtime>'" + SportsBooking.starttime + "' and Overtime<='" +SportsBooking.endtime + "')   ) );";
                        ret = statement.executeQuery(sql);
                        ret.next();
                        int cnt = ret.getInt("cnt");
                        String str = Integer.toString(cnt);
                        SportsBooking.info.add(SportsBooking.location + " has " + str + " unit.");
                    }
                }
            }

            if (t == 1 && s == 3) {//查教练
            	SportsBooking.info.clear();
                if (k == 1) {//查老师名字
                    sql = "select count(*) cnt from Reservation_Table r join (" +
                            "   select i.ID ID from instructor i join user u on u.ID=i.ID and UserName='" + SportsBooking.instructor + "' ) Chen on r.Tutorialid=Chen.ID " +
                            "        and (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.endtime + "')" +
                            "        or (Overtime>'" + statement + "' and Overtime<='" + SportsBooking.endtime + "')   ) ;";
                    ret = statement.executeQuery(sql);
                    while (ret.next()){
                    int cnt = ret.getInt("cnt");
                    if (cnt != 0)
                    	SportsBooking.info.add(SportsBooking.instructor + " is not free in all the time.\n You may try a shorter time slice or another time slice. ");
                    else
                    	SportsBooking.info.add(SportsBooking.instructor + " is free in the time.");
                }}
                else if (k == 2) {//查有某技能且空闲的老师
                    sql = "select Name from " +
                            "(select * from Skills join good_at on Skillid  " +
                            "where Name='" + SportsBooking.instrctor_kind + "')sk join instructor i on Instructorid=i.ID;";
                    ResultSet ret2 = statement.executeQuery(sql);

                    while (ret2.next()) {
                    	SportsBooking.instructor = ret2.getString("Name");
                        sql = "select count(*) cnt from Reservation_Table r join (" +
                                "   select i.ID ID from instructor i join user u on u.ID=i.ID and UserName='" + SportsBooking.instructor + "' ) Chen on r.Tutorialid=Chen.ID " +
                                "        and (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.endtime + "')" +
                                "        or (Overtime>'" + statement + "' and Overtime<='" + SportsBooking.endtime + "')   ) ;";
                        ret = statement.executeQuery(sql);
                        while (ret.next()){
                        int cnt = ret.getInt("cnt");
                        if (cnt != 0)
                        	SportsBooking.info.add(SportsBooking.instructor + " is not free in all the time." +
                                    " You may try a shorter time slice or another time slice. ");
                        else
                        	SportsBooking.info.add(SportsBooking.instructor + " is free in the time.");
                    }
                    }
                }
            }

            System.out.println("t:"+t+" s:"+s);
            if (t == 2 && s == 1) {// t=123 分别是查定取消   s=123分别是场器材老师
                //还有用户权限
            	SportsBooking.info.clear();
            	System.out.println(SportsBooking.id);
                sql = "select credit from User where epy_stu_ID=" + SportsBooking.id + ";";
                ret = statement.executeQuery(sql);
                System.out.println("t:"+t+" s:"+s);
                ret.next();
                int credit = Integer.parseInt(SportsBooking.Credit);
               
                if (credit == 0) {
                	SportsBooking.info.add("Your credit is " + credit + ", and this is not enough.");
                } else {
                	System.out.println(SportsBooking.specific_gym);
                    sql = "select ID from gym_item where Name='" + SportsBooking.specific_gym + "';";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    long gym_id = ret.getLong("ID");
                    date = SportsBooking.da;
                   
                    sql = "select count(*)cnt from Reservation_Table where Gymid=" + gym_id + " and date='" + date + "' and (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.endtime + "' ) " +
                            " or (Overtime>'" + SportsBooking.starttime + "' and Overtime<='" + SportsBooking.endtime + "')   );";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    int cnt = ret.getInt("cnt");
                    //System.out.println(now_res_id+SportsBooking.id+SportsBooking.starttime.toString()+SportsBooking.endtime.toString()+gym_id+date);
                    
                    if (cnt > 0) {SportsBooking.valid=false;
                    	SportsBooking.info.add("This time slice of the gym is not available, try another one please.");
                    } else {SportsBooking.valid=true;
                    //   System.out.println(now_res_id+SportsBooking.id+SportsBooking.starttime.toString()+SportsBooking.endtime.toString()+gym_id+date);
                    	SportsBooking.info.add("The operation is successful!");
                    	
                    	 sql="select ID idd from user where epy_stu_ID="+SportsBooking.id+";";
                    	 ret = statement.executeQuery(sql);
                    	 ret.next();
                    	 long idd=ret.getLong("idd");
                    	 
                    	 sql="select max(ID) iddd from reservation_table;";
                    	 ret = statement.executeQuery(sql);
                    	 ret.next();
                    	 long iddd=ret.getLong("iddd")+1;
                    	  System.out.println("id: "+iddd+" Addrid:"+SportsBooking.id+" "+SportsBooking.starttime+" "+SportsBooking.endtime.toString()+" "+gym_id+" ");
                    	 
                    	sql = "insert into mydb.Reservation_Table(ID, Adderid, Begintime, Overtime, Gymid, date) " +
                                "values (" + iddd + "," + idd + " ,'" + SportsBooking.starttime + "','" + SportsBooking.endtime + "'," + gym_id + ",'" + date + "');";
                        statement.executeUpdate(sql);
                        
                        System.out.println(iddd);
                    }
                  }
                
            }
            
            if (s == 2 && t == 2) {// t=123 分别是查定取消   s=123分别是场器材老师
                //还有用户权限
            	SportsBooking.info.clear();
                sql = "select credit from User where ID=" + SportsBooking.id + ";";
                ret = statement.executeQuery(sql);
                ret.next();
                int credit = ret.getInt("credit");
                System.out.println(credit);
                if (credit == 0) {
                	SportsBooking.info.add("Your credit is " + credit + ", and this is not enough.");
                } else {
                    sql = "select count(*)cnt from equipment_item ei join equipment_type et " +
                            "  on ei.type_id = et.ID " +
                            "where belong_gym='" + SportsBooking.location + "' and useable=1 and kind='" + SportsBooking.item_kind + "' " +
                            " and not exists " +
                            "         (select Equipmentid from Reservation_Table where  (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.starttime +
                            "                           ) or (Overtime>'" + SportsBooking.endtime + "' and Overtime<='" + SportsBooking.endtime + "')   ) and date='" + date + "') ;";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    int cnt = ret.getInt("cnt");
                    if (cnt >= SportsBooking.quantity) {
                        sql = "select ei.ID IID from equipment_item ei join equipment_type et " +
                                "  on ei.type_id = et.ID " +
                                "where belong_gym='" + SportsBooking.location + "' and useable=1 and kind='" + SportsBooking.item_kind + "' " +
                                " and not exists " +
                                "         (select Equipmentid from Reservation_Table where  (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.starttime +
                                "   ) or (Overtime>'" + SportsBooking.endtime + "' and Overtime<='" + SportsBooking.endtime + "')   ) and date='" + date + "') ;";
                        ret = statement.executeQuery(sql);
                        int i = 0;
                        while (ret.next() && i < SportsBooking.quantity) {
                        	sql="select ID idd from user where epy_stu_ID="+SportsBooking.id+";";
                       	 ret = statement.executeQuery(sql);
                       	 ret.next();
                       	 long idd=ret.getLong("idd");
                       	 
                       	 sql="select max(ID) iddd from reservation_table;";
                       	 ret = statement.executeQuery(sql);
                       	 ret.next();
                       	 long iddd=ret.getLong("iddd")+1;
                       	 
                            long equipmentid = ret.getLong("IID");
                            sql = "insert into mydb.Reservation_Table(ID, Adderid, Begintime, Overtime, equipmentid, date) " +
                                    "values (" + iddd + "," + idd + " ,'" + SportsBooking.starttime + "','" + SportsBooking.endtime + "'," + equipmentid + ",'" + date + "');";
                            statement.executeQuery(sql);
                            
                        }
                        SportsBooking.info.add("Successful operation!");
                    } else {
                    	SportsBooking.info.add("There are only " + cnt + " unit this kind of equipment, so they are not enough.");
                    }
                }
            }

            if (t == 2 && s == 3) {// t=123 分别是查定取消   s=123分别是场器材老师
                //还有用户权限
            	SportsBooking.info.clear();
                sql = "select credit from User where ID=" + SportsBooking.id + ";";
                ret = statement.executeQuery(sql);
                ret.next();
                int credit = ret.getInt("credit");
                if (credit <= 50) {//50不够权限
                	SportsBooking.info.add("Your credit is " + credit + ", and this is not enough.");
                } else {
                    sql = "select count(*) cnt from Reservation_Table r join (" +
                            "   select i.ID ID from instructor i join user u on u.ID=i.ID and UserName='" + SportsBooking.instructor + "' ) Chen on r.Tutorialid=Chen.ID " +
                            "        and (    ( Begintime>='" + SportsBooking.starttime + "' and Begintime<'" + SportsBooking.endtime + "')" +
                            "        or (Overtime>'" + SportsBooking.starttime + "' and Overtime<='" + SportsBooking.endtime + "')   ) ;";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    int cnt = ret.getInt("cnt");
                    if (cnt != 0) SportsBooking.info.add(SportsBooking.instructor + " is not free in all the time.You may try another time slice. ");
                    else {
                        sql = "select instructor.ID IDD " +
                                "from instructor join user u " +
                                "on instructor.Userid = u.ID and UserName='" + SportsBooking.instructor + "';";
                        ret = statement.executeQuery(sql);
                        ret.next();
                        long IDD = ret.getInt("IDD");
                        
                        sql="select ID idd from user where epy_stu_ID="+SportsBooking.id+";";
                   	 ret = statement.executeQuery(sql);
                   	 ret.next();
                   	 long idddd=ret.getLong("idd");
                   	 
                   	 sql="select max(ID) iddd from reservation_table;";
                   	 ret = statement.executeQuery(sql);
                   	 ret.next();
                   	 long iddd=ret.getLong("iddd")+1;
                   	 
                   	 
                        sql = "insert into mydb.Reservation_Table(ID, Adderid, Begintime, Overtime, Tutorialid, date) " +
                                "values (" + iddd + "," + idddd + " ,'" + SportsBooking.starttime + "','" + SportsBooking.endtime + "'," + IDD + ",'" + date + "');";
                        statement.executeQuery(sql);
                        now_res_id++;
                    }
                }
            }


            if (t == 3 && s == 1) {// t=123 分别是查定取消   s=123分别是场器材老师
                //还有用户权限
            	SportsBooking.info.clear();
                sql = "select count(*)cnt from Reservation_Table rt " +
                        "join gym_item gi on  rt.Gymid=gi.ID" +
                        "where Begintime='" + SportsBooking.starttime + "' and Overtime='"
                        + SportsBooking.endtime + "' and Adderid=" + SportsBooking.id + " " +
                        "and date='" + date + "' and gi.Name='" + SportsBooking.specific_gym + "';";
                ret = statement.executeQuery(sql);
                ret.next();
                long rt_IDD = ret.getLong("cnt");
                if (rt_IDD == 0) SportsBooking.info.add("The reservation is not made.");
                else {
                    sql = "select rt.id idd from Reservation_Table rt " +
                            "join gym_item gi on  rt.Gymid=gi.ID" +
                            "where Begintime='" + SportsBooking.starttime + "' and Overtime='"
                            + SportsBooking.endtime + "' and Adderid=" + SportsBooking.id + " " +
                            "and date='" + date + "' and gi.Name='" + SportsBooking.specific_gym + "';";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    rt_IDD = ret.getLong("idd");
                    sql = "delete from Reservation_Table where ID=" + rt_IDD + ";";
                    statement.executeQuery(sql);
                    SportsBooking.info.add("Delete successfully!");
                }
            }


            if (t == 3 && s == 2) {// t=123 分别是查定取消   s=123分别是场器材老师
                //还有用户权限
            	SportsBooking.info.clear();
                sql = " select count(*) cnt from Reservation_Table rt " +
                        "join equipment_item ei on rt.Equipmentid = ei.ID " +
                        "   join equipment_type et on ei.type_id = et.ID " +
                        "where Adderid=" + SportsBooking.id + " and kind='" + SportsBooking.item_kind + "'" +
                        "and belong_gym='" + SportsBooking.location + "' and date='" + date + "' " +
                        "and Begintime='" + SportsBooking.starttime + "' and Overtime='" + SportsBooking.endtime + "';";
                ret = statement.executeQuery(sql);
                ret.next();
                long rt_IDD = ret.getLong("cnt");
                if (rt_IDD == 0) SportsBooking.info.add("The reservation is not made.");
                else {
                    sql = " select rt.id idd from Reservation_Table rt " +
                            "join equipment_item ei on rt.Equipmentid = ei.ID " +
                            "   join equipment_type et on ei.type_id = et.ID " +
                            "where Adderid=" + SportsBooking.id + " and kind='" + SportsBooking.item_kind + "'" +
                            "and belong_gym='" + SportsBooking.location + "' and date='" + date + "' " +
                            "and Begintime='" + SportsBooking.starttime + "' and Overtime='" + SportsBooking.endtime + "';";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    int i = 0;
                    while (i < SportsBooking.quantity && ret.next()) {
                        rt_IDD = ret.getLong("idd");
                        sql = "delete from Reservation_Table where ID=" + rt_IDD + ";";
                        statement.executeQuery(sql);
                        i++;
                    }
                    SportsBooking.info.add("Delete successfully!");
                }
            }

            if (s == 3 && t == 3) {// t=123 分别是查定取消   s=123分别是场器材老师
                //还有用户权限
            	SportsBooking.info.clear();
                sql = "select count(*)cnt from Reservation_Table rt " +
                        "join instructor ins on  rt.Tutorialid=ins.ID" +
                        "join user u on ins.Userid = u.ID " +
                        "where u.UserName='" + SportsBooking.instructor + "'" +
                        "and Begintime='" + SportsBooking.starttime + "' and Overtime='"
                        + SportsBooking.endtime + "' and Adderid=" + SportsBooking.id + " " +
                        "and date='" + date + "';";
                ret = statement.executeQuery(sql);
                ret.next();
                long rt_IDD = ret.getLong("cnt");
                if (rt_IDD == 0) SportsBooking.info.add("The reservation is not made.");
                else {
                    sql = "select rt.id idd from Reservation_Table rt " +
                            "join instructor ins on  rt.Tutorialid=ins.ID" +
                            "join user u on ins.Userid = u.ID " +
                            "where u.UserName='" + SportsBooking.instructor + "'" +
                            "and Begintime='" + SportsBooking.starttime + "' and Overtime='"
                            + SportsBooking.endtime + "' and Adderid=" + SportsBooking.id + " " +
                            "and date='" + date + "';";
                    ret = statement.executeQuery(sql);
                    ret.next();
                    rt_IDD = ret.getLong("idd");
                    sql = "delete from Reservation_Table where ID=" + rt_IDD + ";";
                    statement.executeQuery(sql);
                    SportsBooking.info.add("Delete successfully!");
                }
            }
        }


/////////////////////////////////////////////////////////////////////////////////////////

        else {//进入管理员模式

            if (maneger_code == 1) {//扣除学号为maneger_stu_code的学生 数量为maneger_minus_mark 的credit（信誉度）
                sql = "update User set credit =credit-" + maneger_minus_mark +
                        " where epy_stu_ID=" + maneger_stu_code + ";";
                statement.executeUpdate(sql);
                
            }

            if(maneger_code==2){//增加新生账号，密码初始化为学号
                sql = "insert User(id, username, password, epy_stu_id, credit) " +
                        "values ("+maneger_stu_id+",'"+maneger_stu_name+"','"+maneger_stu_id+"',"+maneger_stu_id+",100);";
                statement.executeUpdate(sql);
                maneger_stu_id++;
            }

            if (maneger_code == 3) {//删除过时的预约
                sql = "delete from Reservation_Table where date<'"+maneger_delete_date+"';";
                statement.executeUpdate(sql);
            }

            if (maneger_code == 4) {//将坏了的器材设置为坏了
                sql = "update equipment_item set useable=0 where ID="+maneger_unusable_id+";";
                statement.executeUpdate(sql);
            }

            if (maneger_code == 5) {//删除所有坏了的器材
                sql = "delete from equipment_item where useable=0;";
                statement.executeUpdate(sql);
            }
            System.out.println("Operation is finished");

        }

        con.close();
    } catch (ClassNotFoundException e) {
        System.out.println("数据库驱动没有安装");

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("数据库连接失败");
    }
}



	/////////////////////////////////////////////////////////////////////////////////////////

	public Database() {
		setTitle("SUSTech Sports Booking System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnLogIn = new JButton("Log in");

		btnLogIn.setBounds(184, 208, 93, 23);
		contentPane.add(btnLogIn);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setForeground(Color.WHITE);
		lblId.setToolTipText("Your only ID in SUSTech");
		lblId.setBounds(125, 155, 18, 15);
		contentPane.add(lblId);
		
		JLabel lblPassward = new JLabel("Password:");
		lblPassward.setForeground(Color.WHITE);
		lblPassward.setToolTipText("Your Sakai password");
		lblPassward.setBounds(79, 183, 65, 15);
		contentPane.add(lblPassward);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(144, 177, 181, 21);
		contentPane.add(passwordField_1);
		
		frmtdtxtfldSdf = new JFormattedTextField();
		frmtdtxtfldSdf.setBounds(144, 152, 181, 21);
		contentPane.add(frmtdtxtfldSdf);
		
		txtFadfad = new JTextField();
		txtFadfad.setEnabled(false);
		txtFadfad.setEditable(false);
		txtFadfad.setForeground(Color.CYAN);
		txtFadfad.setBackground(Color.WHITE);
		txtFadfad.setOpaque(false);
		txtFadfad.setBorder(null);
		txtFadfad.setFont(new Font("Segoe Script", Font.BOLD, 20));
		txtFadfad.setHorizontalAlignment(SwingConstants.CENTER);
		txtFadfad.setText("SUSTech Sports Booking System");
		txtFadfad.setBounds(10, 99, 434, 59);
		contentPane.add(txtFadfad);
		txtFadfad.setColumns(10);
		
		txtIfYouHave = new JTextField();
		txtIfYouHave.setForeground(Color.BLACK);
		txtIfYouHave.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		txtIfYouHave.setText("If you have any problem, please contact administritor.Tel:15602992435 ");
		txtIfYouHave.setBounds(0, 241, 434, 20);
		contentPane.add(txtIfYouHave);
		txtIfYouHave.setColumns(10);
		txtIfYouHave.setOpaque(false);
		txtIfYouHave.setBorder(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\labman028\\Documents\\Tencent Files\\1030839349\\FileRecv2.jpg"));
		lblNewLabel.setBounds(0, -1, 434, 261);
		contentPane.add(lblNewLabel);

		
		btnLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 消息对话框无返回, 仅做通知作用          	
            	t= 1;
                s = 4;
                SportsBooking.id= frmtdtxtfldSdf.getText();
                SportsBooking.password = String.valueOf(passwordField_1.getPassword());
                backend();
                System.out.println(SportsBooking.id+" "+ SportsBooking.password);
            if(!isID)
            	JOptionPane.showMessageDialog(
                        Database.getFrames()[0],
                        "No such ID, please check your ID",
                        "Error Message",
                        JOptionPane.WARNING_MESSAGE
                );
           	
            else if(!isPassword) 
            	JOptionPane.showMessageDialog(          
                    Database.getFrames()[0],
                    "Wrong password, please check your password",                  
                    "Error Message",
                    JOptionPane.ERROR_MESSAGE
            );       
           	
            else {
            	frame.setVisible(false);
            	 try {
              	   UserInterface.frame.setVisible(true);
      			} catch (Exception e1) {
      				e1.printStackTrace();
      			}
            }
           }
        });

	}
}
