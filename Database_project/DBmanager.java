package proj;

public class DBmanager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
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

        }*/
		Database.mode=1;
		//Database.maneger_code=3;
		Database.maneger_code=1;
		Database.maneger_stu_code=30001702;
		Database.maneger_minus_mark=40;
		Database.backend();
		
		Database.mode=0;
	}

}
