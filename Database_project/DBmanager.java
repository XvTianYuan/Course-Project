package proj;

public class DBmanager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		else {//�������Աģʽ

            if (maneger_code == 1) {//�۳�ѧ��Ϊmaneger_stu_code��ѧ�� ����Ϊmaneger_minus_mark ��credit�������ȣ�
                sql = "update User set credit =credit-" + maneger_minus_mark +
                        " where epy_stu_ID=" + maneger_stu_code + ";";
                statement.executeUpdate(sql);
            }

            if(maneger_code==2){//���������˺ţ������ʼ��Ϊѧ��
                sql = "insert User(id, username, password, epy_stu_id, credit) " +
                        "values ("+maneger_stu_id+",'"+maneger_stu_name+"','"+maneger_stu_id+"',"+maneger_stu_id+",100);";
                statement.executeUpdate(sql);
                maneger_stu_id++;
            }

            if (maneger_code == 3) {//ɾ����ʱ��ԤԼ
                sql = "delete from Reservation_Table where date<'"+maneger_delete_date+"';";
                statement.executeUpdate(sql);
            }

            if (maneger_code == 4) {//�����˵���������Ϊ����
                sql = "update equipment_item set useable=0 where ID="+maneger_unusable_id+";";
                statement.executeUpdate(sql);
            }

            if (maneger_code == 5) {//ɾ�����л��˵�����
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
