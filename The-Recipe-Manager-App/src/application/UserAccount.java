package application;



import java.util.ArrayList;


public class UserAccount {


 public static int idUserAccount;

 public static ArrayList<Integer> userIdList = new ArrayList<>();

 public static int getUserId() {
	 return idUserAccount;
}


 public static void setUserId(int idUserAccount) {
	 	UserAccount.idUserAccount = idUserAccount;
 }







//
//    // Constructor to initialize the recipe data
//    public UserAccount(int idUserAccount) {
//        this.idUserAccount = idUserAccount;
//    }

    public static void addUserId(int idUserAccount) {
    	userIdList.add(idUserAccount);
    	}
    public static ArrayList<Integer> getUserIdList() {
    		return userIdList;
//    	return idUserAccount;
    	}






      }



