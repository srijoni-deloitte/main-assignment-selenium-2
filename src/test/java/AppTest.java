import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class AppTest extends Method_container
    {
        @Test(priority = 1)
        public void A_openTheWebsite() throws Exception {
            initialSetup();
        }
        @Test(priority = 2)
        public void B_add_cus() throws Exception
        {
            add_new_cus();
        }
        @Test(priority = 3)
        public void C_open_acc() throws Exception
        {
            open_acc_new_cus();
        }
        @Test(priority = 4)
        public void D_logginin() throws Exception
        {
            login();
        }
        @Test(priority = 5)
        public void E_depositting()throws Exception
        {
            deposit_test();
        }
        @Test(priority = 6)
        public void F_Withdrawing()throws Exception
        {
            withdrawl_test();
        }
        @Test(priority = 7)
        public void G_withdraw_exception()throws Exception
        {
            withdraw_exception();
        }
        @AfterClass
        public void close()
        {
            //closing();
        }


    }

