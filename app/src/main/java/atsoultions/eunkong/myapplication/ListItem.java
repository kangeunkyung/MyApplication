package atsoultions.eunkong.myapplication;

/**
 * Created by eunkong on 2017. 10. 18..
 */

public class ListItem {

    private int no;

    private String bank;

    private int bankIndex;

    private String user;

    private String account;

    private String nickname;

    public ListItem() {

    }

    public ListItem(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    public ListItem(String bank, String account, String user) {
        this.bank = bank;
        this.account = account;
        this.user = user;
    }

    public ListItem(int no, String bank, int bankIndex, String account) {
        this.no = no;
        this.bank = bank;
        this.bankIndex = bankIndex;
        this.account = account;
    }

   public ListItem(String bank, int bankIndex, String account, String user) {
        this.bank = bank;
        this.bankIndex = bankIndex;
        this.user = user;
        this.account = account;
    }
    public ListItem(int no, String bank, int bankIndex, String account, String user) {
        this.no = no;
        this.bank = bank;
        this.bankIndex = bankIndex;
        this.user = user;
        this.account = account;
    }

    public ListItem(String bank, int bankIndex, String account, String user, String nickname) {
        this.bank = bank;
        this.bankIndex = bankIndex;
        this.user = user;
        this.account = account;
        this.nickname = nickname;
    }

    public ListItem(int no, String bank, int bankIndex, String account, String user, String nickname) {
        this.no = no;
        this.bank = bank;
        this.bankIndex = bankIndex;
        this.user = user;
        this.account = account;
        this.nickname = nickname;
    }


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getBankIndex() {
        return bankIndex;
    }

    public void setBankIndex(int bankIndex) {
        this.bankIndex = bankIndex;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "no=" + no +
                ", bankIndex=" + bankIndex  +
                ", bank='" + bank + '\'' +
                ", account='" + account + '\'' +
                ", user='" + user + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
