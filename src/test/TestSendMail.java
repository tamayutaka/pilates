package test;

import bo.SendMail;
import model.MailData;

public class TestSendMail {
	public static void main(String[] args) {
		MailData mailData = new MailData();
		testFindNum(mailData);
	}

	public static void testFindNum(MailData mailData){
		SendMail sendMail=new SendMail();
		boolean result = sendMail.send(mailData);
		if(result){
			System.out.println("成功");
		}else{
			System.out.println("失敗");
		}
	}

}
