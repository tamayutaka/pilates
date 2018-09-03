package bo;
import java.util.Calendar;

import dao.CustomerDAO;
import dao.LessonDAO;
import model.Schedule;
public class LessonLogic {


	public Schedule showSchedule(Calendar calendar){
		calendar=Calendar.getInstance();
		int LY = calendar.get(Calendar.YEAR);
		int LM = calendar.get(Calendar.MONTH);
		int LD = calendar.get(Calendar.DATE);
		String sLM=String.valueOf(LM);
		String sLD=String.valueOf(LD);
		Calendar today=Calendar.getInstance();
		int Y=today.get(Calendar.YEAR);
		int M=today.get(Calendar.MONTH);
		int D=today.get(Calendar.DATE);
		today.set(Y,M,D);
		today.add(D,2);
		boolean N=calendar.after(today);
		String[] le= new String[6];
		le[1] = sLM;
		le[2]= sLD;
		if(N==false){

			le[3]="締切";
			le[4]="";
			le[5]="";
			le[6]="";


		}else{
			String DATE=sLM+"/"+sLD;
			LessonDAO LDAO=new LessonDAO();
			String[] FDATE=LDAO.findDay(DATE);
			le[3]=FDATE[0];
			le[5]=FDATE[1];
			CustomerDAO CCDAO=new CustomerDAO();
			int CC1 =CCDAO.countCustomer(DATE,le[3]);
			switch(CC1){
			case 0:le[4]="残り2人";break;
			case 1:le[4]="残り１人";break;
			case 2:le[4]="満員";break;


			}
			int CC2 =CCDAO.countCustomer(DATE,le[5]);
			switch(CC2){
			case 0:le[6]="残り2人";break;
			case 1:le[6]="残り１人";break;
			case 2:le[6]="満員";break;


			}


		}
		Schedule sle=new Schedule(le);
return sle;
	}

}
